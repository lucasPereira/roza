package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;

public final class JplagTestCaseSimilarityMeasurer implements TestCaseSimilarityMeasurer {

	private static final Pattern MATCH_REPORT = Pattern.compile("match\\d+-top\\.html");

	private final JplagMeasurementConfiguration configuration;
	private final JplagReportParser parser;

	public JplagTestCaseSimilarityMeasurer(JplagMeasurementConfiguration configuration) {
		this.configuration = Objects.requireNonNull(configuration);
		parser = new JplagReportParser();
	}

	@Override
	public TestCaseSimilarityMatrix measure(DecomposedTestCases decomposedTestCases) {
		Objects.requireNonNull(decomposedTestCases);
		List<TestCase> testCases = decomposedTestCases.testCases();
		TestCaseSimilarityMatrix matrix = new TestCaseSimilarityMatrix(testCases);
		if (testCases.size() < 2) {
			return matrix;
		}

		RozaProjectPaths paths = RozaProjectPaths.forJplag(Path.of("."));
		String runId = "modern-jplag-" + UUID.randomUUID();
		Path materializerDirectory = paths.materializerRun(runId);
		Path measurerDirectory = paths.measurerRun(runId);
		Path jplagDirectory = paths.jplagToolDirectory();
		try {
			createCleanDirectory(materializerDirectory);
			createCleanDirectory(measurerDirectory);
			List<String> fileNames = TestCaseProjectionMaterializer.materialize(testCases, materializerDirectory, "JplagTestCase")
					.stream()
					.map(MaterializedTestCase::fileName)
					.collect(Collectors.toList());
			runJplag(jplagDirectory, materializerDirectory, measurerDirectory);
			fillMatrix(matrix, fileNames, parseReports(measurerDirectory));
			return matrix;
		} catch (IOException exception) {
			throw new IllegalStateException("JPlag measurement failed.", exception);
		} finally {
			deleteRecursively(materializerDirectory);
			deleteRecursively(measurerDirectory);
		}
	}

	private void runJplag(Path jplagDirectory, Path materializerDirectory, Path measurerDirectory) {
		List<String> command = new ArrayList<>();
		command.add("java");
		command.add("-jar");
		command.add("jplag-2.11.9.jar");
		command.add("-t");
		command.add(String.valueOf(configuration.sensitivity()));
		command.add("-l");
		command.add("java17");
		command.add("-m");
		command.add("0%");
		command.add("-vl");
		command.add("-o");
		command.add(measurerDirectory.resolve("log.txt").toString());
		command.add("-r");
		command.add(measurerDirectory.toString());
		command.add("-s");
		command.add(materializerDirectory.toString());

		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(jplagDirectory.toFile());
		processBuilder.redirectErrorStream(true);
		try {
			Process process = processBuilder.start();
			String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
			int exitCode = process.waitFor();
			if (exitCode != 0) {
				throw new IllegalStateException("JPlag exited with code " + exitCode + ":\n" + output);
			}
		} catch (IOException exception) {
			throw new IllegalStateException("Could not execute JPlag.", exception);
		} catch (InterruptedException exception) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException("JPlag execution was interrupted.", exception);
		}
	}

	private List<JplagSimilarityReport> parseReports(Path measurerDirectory) throws IOException {
		List<JplagSimilarityReport> reports = new ArrayList<>();
		try (Stream<Path> paths = Files.list(measurerDirectory)) {
			List<Path> reportPaths = paths.filter(path -> MATCH_REPORT.matcher(path.getFileName().toString()).matches())
					.sorted()
					.collect(Collectors.toList());
			for (Path reportPath : reportPaths) {
				reports.add(parser.parse(Files.readString(reportPath, StandardCharsets.ISO_8859_1)));
			}
		}
		return reports;
	}

	private void fillMatrix(TestCaseSimilarityMatrix matrix, List<String> fileNames, List<JplagSimilarityReport> reports) {
		Map<String, Integer> indexes = new HashMap<>();
		for (int index = 0; index < fileNames.size(); index++) {
			indexes.put(fileNames.get(index), index);
		}
		for (JplagSimilarityReport report : reports) {
			Integer sourceIndex = indexes.get(fileName(report.sourceFile()));
			Integer targetIndex = indexes.get(fileName(report.targetFile()));
			if (sourceIndex != null && targetIndex != null) {
				matrix.setSimilarity(sourceIndex, targetIndex, report.sourceSimilarity());
				matrix.setSimilarity(targetIndex, sourceIndex, report.targetSimilarity());
			}
		}
	}

	private String fileName(String path) {
		return Path.of(path).getFileName().toString();
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
