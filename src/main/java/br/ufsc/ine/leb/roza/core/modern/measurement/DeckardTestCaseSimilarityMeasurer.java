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

public final class DeckardTestCaseSimilarityMeasurer implements TestCaseSimilarityMeasurer {

	private final DeckardMeasurementConfiguration configuration;
	private final DeckardCloneReportParser parser;
	private final CloneFragmentSimilarityScorer scorer;

	public DeckardTestCaseSimilarityMeasurer(DeckardMeasurementConfiguration configuration) {
		this.configuration = Objects.requireNonNull(configuration);
		parser = new DeckardCloneReportParser();
		scorer = new CloneFragmentSimilarityScorer();
	}

	@Override
	public TestCaseSimilarityMatrix measure(DecomposedTestCases decomposedTestCases) {
		Objects.requireNonNull(decomposedTestCases);
		RozaProjectPaths paths = RozaProjectPaths.forDeckard(Path.of("."));
		String runId = "modern-deckard-" + UUID.randomUUID();
		Path materializerDirectory = paths.materializerRun(runId);
		Path measurerDirectory = paths.measurerRun(runId);
		Path deckardDirectory = paths.deckardToolDirectory();
		Path config = deckardDirectory.resolve("config");
		try {
			createCleanDirectory(materializerDirectory);
			createCleanDirectory(measurerDirectory.resolve("vectors"));
			createCleanDirectory(measurerDirectory.resolve("cluster"));
			createCleanDirectory(measurerDirectory.resolve("times"));
			List<MaterializedTestCase> materializedTestCases = TestCaseProjectionMaterializer.materialize(
					decomposedTestCases.testCases(), materializerDirectory, "DeckardTestCase");
			Files.writeString(config, configContent(deckardDirectory, materializerDirectory, measurerDirectory), StandardCharsets.UTF_8);
			runDeckard(deckardDirectory);
			List<CloneFragment> fragments = parseCloneReports(measurerDirectory.resolve("cluster"));
			return scorer.score(materializedTestCases, fragments);
		} catch (IOException exception) {
			throw new IllegalStateException("Deckard measurement failed.", exception);
		} finally {
			deleteIfExists(config);
			deleteRecursively(materializerDirectory);
			deleteRecursively(measurerDirectory);
		}
	}

	private List<CloneFragment> parseCloneReports(Path clusterDirectory) throws IOException {
		List<CloneFragment> fragments = new ArrayList<>();
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
