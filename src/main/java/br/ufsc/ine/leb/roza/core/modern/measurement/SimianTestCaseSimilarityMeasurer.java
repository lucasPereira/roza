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

public final class SimianTestCaseSimilarityMeasurer implements TestCaseSimilarityMeasurer {

	private final SimianMeasurementConfiguration configuration;
	private final SimianCloneReportParser parser;
	private final CloneFragmentSimilarityScorer scorer;

	public SimianTestCaseSimilarityMeasurer(SimianMeasurementConfiguration configuration) {
		this.configuration = Objects.requireNonNull(configuration);
		parser = new SimianCloneReportParser();
		scorer = new CloneFragmentSimilarityScorer();
	}

	@Override
	public TestCaseSimilarityMatrix measure(DecomposedTestCases decomposedTestCases) {
		Objects.requireNonNull(decomposedTestCases);
		List<TestCase> testCases = decomposedTestCases.testCases();
		if (testCases.size() < 2) {
			return new TestCaseSimilarityMatrix(testCases);
		}

		RozaProjectPaths paths = RozaProjectPaths.forSimian(Path.of("."));
		String runId = "modern-simian-" + UUID.randomUUID();
		Path materializerDirectory = paths.materializerRun(runId);
		Path measurerDirectory = paths.measurerRun(runId);
		Path report = measurerDirectory.resolve("report.xml");
		try {
			createCleanDirectory(materializerDirectory);
			createCleanDirectory(measurerDirectory);
			List<MaterializedTestCase> materializedTestCases = TestCaseProjectionMaterializer.materialize(testCases, materializerDirectory, "SimianTestCase");
			runSimian(paths.simianToolDirectory(), materializedTestCases, materializerDirectory, report);
			List<CloneFragment> fragments = parser.parse(Files.readString(report, StandardCharsets.UTF_8));
			return scorer.score(materializedTestCases, fragments);
		} catch (IOException exception) {
			throw new IllegalStateException("Simian measurement failed.", exception);
		} finally {
			deleteRecursively(materializerDirectory);
			deleteRecursively(measurerDirectory);
		}
	}

	private void runSimian(
			Path simianDirectory,
			List<MaterializedTestCase> materializedTestCases,
			Path materializerDirectory,
			Path report) {
		List<String> command = new ArrayList<>();
		command.add("java");
		command.add("-jar");
		command.add("simian-2.5.10.jar");
		command.addAll(configurationArguments());
		command.addAll(materializedTestCases.stream()
				.map(materializedTestCase -> materializerDirectory.resolve(materializedTestCase.fileName()).toString())
				.collect(Collectors.toList()));

		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(simianDirectory.toFile());
		processBuilder.redirectErrorStream(true);
		processBuilder.redirectOutput(report.toFile());
		try {
			Process process = processBuilder.start();
			int exitCode = process.waitFor();
			if (exitCode != 0) {
				throw new IllegalStateException("Simian exited with code " + exitCode + ":\n" + Files.readString(report, StandardCharsets.UTF_8));
			}
		} catch (IOException exception) {
			throw new IllegalStateException("Could not execute Simian.", exception);
		} catch (InterruptedException exception) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException("Simian execution was interrupted.", exception);
		}
	}

	private List<String> configurationArguments() {
		return List.of(
				"-threshold=" + configuration.threshold(),
				"-ignoreCurlyBraces+",
				"-ignoreIdentifiers-",
				"-ignoreStrings-",
				"-ignoreNumbers-",
				"-ignoreCharacters-",
				"-ignoreLiterals-",
				"-ignoreVariableNames-",
				"-formatter=xml",
				"-language=java",
				"-failOnDuplication-",
				"-reportDuplicateText+",
				"-ignoreBlocks=0:0",
				"-ignoreIdentifierCase-",
				"-ignoreStringCase-",
				"-ignoreCharacterCase-",
				"-ignoreSubtypeNames-",
				"-ignoreModifiers+",
				"-balanceParentheses+",
				"-balanceSquareBrackets+");
	}

	private void createCleanDirectory(Path directory) throws IOException {
		deleteRecursively(directory);
		Files.createDirectories(directory);
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

	private void deleteIfExists(Path file) {
		try {
			Files.deleteIfExists(file);
		} catch (IOException ignored) {
		}
	}
}
