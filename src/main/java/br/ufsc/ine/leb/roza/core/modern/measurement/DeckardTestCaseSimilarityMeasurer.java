package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

public final class DeckardTestCaseSimilarityMeasurer implements TestCaseSimilarityMeasurer {

	private static final int FIRST_PROJECTED_BODY_LINE = 3;

	private final DeckardMeasurementConfiguration configuration;
	private final DeckardCloneReportParser parser;
	private final DeckardSimilarityScorer scorer;

	public DeckardTestCaseSimilarityMeasurer() {
		this(new DeckardMeasurementConfiguration());
	}

	public DeckardTestCaseSimilarityMeasurer(DeckardMeasurementConfiguration configuration) {
		this.configuration = Objects.requireNonNull(configuration);
		parser = new DeckardCloneReportParser();
		scorer = new DeckardSimilarityScorer();
	}

	@Override
	public TestCaseSimilarityMatrix measure(DecomposedTestCases decomposedTestCases) {
		Objects.requireNonNull(decomposedTestCases);
		Path rozaRoot = validateRozaRoot(Path.of("."));
		String runId = "modern-deckard-" + UUID.randomUUID();
		Path materializerDirectory = rozaRoot.resolve("output/materializer").resolve(runId);
		Path measurerDirectory = rozaRoot.resolve("output/measurer").resolve(runId);
		Path deckardDirectory = rozaRoot.resolve("external-tools/deckard");
		Path config = deckardDirectory.resolve("config");
		try {
			createCleanDirectory(materializerDirectory);
			createCleanDirectory(measurerDirectory.resolve("vectors"));
			createCleanDirectory(measurerDirectory.resolve("cluster"));
			createCleanDirectory(measurerDirectory.resolve("times"));
			List<DeckardMaterializedTestCase> materializedTestCases = materialize(decomposedTestCases.testCases(), materializerDirectory);
			Files.writeString(config, configContent(deckardDirectory, materializerDirectory, measurerDirectory), StandardCharsets.UTF_8);
			runDeckard(deckardDirectory);
			List<DeckardCloneFragment> fragments = parseCloneReports(measurerDirectory.resolve("cluster"));
			return scorer.score(materializedTestCases, fragments);
		} catch (IOException exception) {
			throw new IllegalStateException("Deckard measurement failed.", exception);
		} finally {
			deleteIfExists(config);
			deleteRecursively(materializerDirectory);
			deleteRecursively(measurerDirectory);
		}
	}

	private List<DeckardMaterializedTestCase> materialize(List<TestCase> testCases, Path materializerDirectory) throws IOException {
		List<DeckardMaterializedTestCase> materializedTestCases = new ArrayList<>();
		for (int index = 0; index < testCases.size(); index++) {
			TestCase testCase = testCases.get(index);
			String className = String.format("DeckardTestCase%05d", index);
			String fileName = className + ".java";
			List<String> projection = projection(testCase);
			Files.writeString(materializerDirectory.resolve(fileName), javaSource(className, projection), StandardCharsets.UTF_8);
			materializedTestCases.add(new DeckardMaterializedTestCase(testCase, fileName, FIRST_PROJECTED_BODY_LINE, projection.size()));
		}
		return materializedTestCases;
	}

	private List<String> projection(TestCase testCase) {
		return testCase.body()
				.statements()
				.stream()
				.takeWhile(statement -> !statement.isAssertion())
				.map(CodeStatement::originalText)
				.collect(Collectors.toList());
	}

	private String javaSource(String className, List<String> statements) {
		StringBuilder builder = new StringBuilder();
		builder.append("public class ").append(className).append(" {\n");
		builder.append("\tpublic void test() {\n");
		for (String statement : statements) {
			builder.append("\t\t").append(statement).append("\n");
		}
		builder.append("\t}\n");
		builder.append("}\n");
		return builder.toString();
	}

	private List<DeckardCloneFragment> parseCloneReports(Path clusterDirectory) throws IOException {
		List<DeckardCloneFragment> fragments = new ArrayList<>();
		if (!Files.isDirectory(clusterDirectory)) {
			return fragments;
		}
		try (Stream<Path> paths = Files.list(clusterDirectory)) {
			List<Path> reports = paths.filter(path -> path.getFileName().toString().startsWith("post_cluster_vdb_"))
					.collect(Collectors.toList());
			for (Path report : reports) {
				fragments.addAll(parser.parse(Files.readString(report, StandardCharsets.UTF_8)));
			}
		}
		return fragments;
	}

	private void runDeckard(Path deckardDirectory) {
		ProcessBuilder processBuilder = new ProcessBuilder("./execute-using-docker.sh");
		processBuilder.directory(deckardDirectory.toFile());
		processBuilder.redirectErrorStream(true);
		try {
			Process process = processBuilder.start();
			String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
			int exitCode = process.waitFor();
			if (exitCode != 0) {
				throw new IllegalStateException("Deckard exited with code " + exitCode + ":\n" + output);
			}
		} catch (IOException exception) {
			throw new IllegalStateException("Could not execute Deckard.", exception);
		} catch (InterruptedException exception) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException("Deckard execution was interrupted.", exception);
		}
	}

	private String configContent(Path deckardDirectory, Path materializerDirectory, Path measurerDirectory) {
		return "#!/bin/sh\n"
				+ "export MIN_TOKENS=" + configuration.minTokens() + "\n"
				+ "export STRIDE=" + configuration.stride() + "\n"
				+ "export SIMILARITY=" + configuration.similarity() + "\n"
				+ "export SRC_DIR=" + configPath(deckardDirectory, materializerDirectory) + "\n"
				+ "export FILE_PATTERN=*.java\n"
				+ "export VECTOR_DIR=" + configPath(deckardDirectory, measurerDirectory.resolve("vectors")) + "\n"
				+ "export CLUSTER_DIR=" + configPath(deckardDirectory, measurerDirectory.resolve("cluster")) + "\n"
				+ "export TIME_DIR=" + configPath(deckardDirectory, measurerDirectory.resolve("times")) + "\n"
				+ "export DECKARD_DIR=tool\n"
				+ "export VGEN_EXEC=tool/src/main/jvecgen\n"
				+ "export GROUPING_EXEC=tool/src/vgen/vgrouping/runvectorsort\n"
				+ "export CLUSTER_EXEC=tool/src/lsh/bin/enumBuckets\n"
				+ "export QUERY_EXEC=tool/src/lsh/bin/queryBuckets\n"
				+ "export POSTPRO_EXEC=tool/scripts/clonedetect/post_process_groupfile\n"
				+ "export GROUPING_S=1\n"
				+ "export MAX_PROC=1\n"
				+ "export MAX_PROCS=1\n";
	}

	private String configPath(Path deckardDirectory, Path path) {
		return deckardDirectory.relativize(path).toString().replace('\\', '/');
	}

	private static Path validateRozaRoot(Path rozaRoot) {
		Path normalized = Objects.requireNonNull(rozaRoot).toAbsolutePath().normalize();
		if (!Files.isDirectory(normalized.resolve("external-tools/deckard"))) {
			throw new IllegalArgumentException("Deckard measurement must run from the Roza project root.");
		}
		return normalized;
	}

	private void createCleanDirectory(Path directory) throws IOException {
		deleteRecursively(directory);
		Files.createDirectories(directory);
	}

	private void deleteIfExists(Path file) {
		try {
			Files.deleteIfExists(file);
		} catch (IOException ignored) {
		}
	}

	private void deleteRecursively(Path path) {
		if (!Files.exists(path)) {
			return;
		}
		try (Stream<Path> paths = Files.walk(path)) {
			paths.sorted(Comparator.reverseOrder())
					.forEach(this::deleteIfExists);
		} catch (IOException ignored) {
		}
	}
}
