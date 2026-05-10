package br.ufsc.ine.leb.roza.expt.h;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SetupMethod;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestMethod;
import br.ufsc.ine.leb.roza.TextFile;
import br.ufsc.ine.leb.roza.clustering.AgglomerativeHierarchicalClusteringTestCaseClusterer;
import br.ufsc.ine.leb.roza.clustering.AnyClusterReferee;
import br.ufsc.ine.leb.roza.clustering.BiggestClusterReferee;
import br.ufsc.ine.leb.roza.clustering.CompleteLinkage;
import br.ufsc.ine.leb.roza.clustering.ComposedReferee;
import br.ufsc.ine.leb.roza.clustering.Linkage;
import br.ufsc.ine.leb.roza.clustering.Referee;
import br.ufsc.ine.leb.roza.clustering.SimilarityBasedCriterion;
import br.ufsc.ine.leb.roza.clustering.TestCaseClusterer;
import br.ufsc.ine.leb.roza.clustering.ThresholdCriterion;
import br.ufsc.ine.leb.roza.extraction.Junit5TestCaseExtractor;
import br.ufsc.ine.leb.roza.extraction.TestCaseExtractor;
import br.ufsc.ine.leb.roza.loading.RecursiveTextFileLoader;
import br.ufsc.ine.leb.roza.loading.TextFileLoader;
import br.ufsc.ine.leb.roza.materialization.Junit4WithoutAssertionsTestCaseMaterializer;
import br.ufsc.ine.leb.roza.materialization.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.measurement.LccssSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.SimilarityMeasurer;
import br.ufsc.ine.leb.roza.parsing.Junit5TestClassParser;
import br.ufsc.ine.leb.roza.parsing.TestClassParser;
import br.ufsc.ine.leb.roza.refactoring.ClusterRefactor;
import br.ufsc.ine.leb.roza.refactoring.IncrementalTestClassNamingStrategy;
import br.ufsc.ine.leb.roza.refactoring.SimpleClusterRefactor;
import br.ufsc.ine.leb.roza.refactoring.TestClassNamingStrategy;
import br.ufsc.ine.leb.roza.selection.JavaExtensionTextFileSelector;
import br.ufsc.ine.leb.roza.selection.TextFileSelector;
import br.ufsc.ine.leb.roza.utils.CommaSeparatedValues;
import br.ufsc.ine.leb.roza.utils.FolderUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Experiment {

	private static final String SUBJECT = "Apache Commons Lang";
	private static final String SOURCE_COMMIT = "19e2d568a4ee4910ff470eba40ec040673195d7d";
	private static final String SUBJECT_TESTS = "src/expt/resources/h";
	private static final String RESULTS = "experiment-results/h";
	private static final String MATERIALIZER_OUTPUT = "output/materializer/h";
	private static final String FILE_LIMIT_ENVIRONMENT_VARIABLE = "ROZA_EXPERIMENT_H_MAX_FILES";
	private static final BigDecimal SIMILARITY_THRESHOLD = BigDecimal.valueOf(0.4);

	public static void main(String[] args) {
		new FolderUtils(RESULTS).createEmptyFolder();
		new FolderUtils(MATERIALIZER_OUTPUT).createEmptyFolder();

		SubjectData subject = loadSubject();
		writeSkippedFiles(subject);
		writeConfiguration();

		List<ExperimentRow> rows = new ArrayList<>();
		rows.add(measure("Original", subject.classes, subject, 0));
		long localStarted = System.nanoTime();
		List<TestClass> localOnlyClasses = refactorLocally(subject.classes);
		rows.add(measure("Local-only refactoring", localOnlyClasses, subject, elapsedMillis(localStarted)));
		long globalStarted = System.nanoTime();
		List<TestClass> rozaClasses = refactorGlobally(subject.tests);
		rows.add(measure("Roza global clustering", rozaClasses, subject, elapsedMillis(globalStarted)));
		writeSummary(rows);
	}

	private static SubjectData loadSubject() {
		TextFileLoader loader = new RecursiveTextFileLoader(SUBJECT_TESTS);
		TextFileSelector selector = new JavaExtensionTextFileSelector();
		TestClassParser parser = new Junit5TestClassParser();
		TestCaseExtractor extractor = new Junit5TestCaseExtractor();
		List<TextFile> selected = selector.select(loader.load());
		selected = limitFiles(selected);
		List<TestClass> classes = new ArrayList<>();
		List<SkippedFile> skippedFiles = new ArrayList<>();
		for (TextFile file : selected) {
			try {
				classes.addAll(parser.parse(List.of(file)));
			} catch (RuntimeException exception) {
				skippedFiles.add(new SkippedFile(file.getName(), exception.getClass().getSimpleName(), sanitize(exception.getMessage())));
			}
		}
		List<TestCase> tests = extractor.extract(classes);
		System.out.printf("Loaded %d Java files, parsed %d test classes, extracted %d tests, skipped %d files.%n", selected.size(), classes.size(), tests.size(), skippedFiles.size());
		return new SubjectData(selected.size(), classes, tests, skippedFiles);
	}

	private static List<TextFile> limitFiles(List<TextFile> files) {
		int limit = getFileLimit();
		if (limit <= 0 || files.size() <= limit) {
			return files;
		}
		System.out.printf("Limiting experiment h to %d Java files because %s is set.%n", limit, FILE_LIMIT_ENVIRONMENT_VARIABLE);
		return new ArrayList<>(files.subList(0, limit));
	}

	private static List<TestClass> refactorLocally(List<TestClass> classes) {
		TestCaseExtractor extractor = new Junit5TestCaseExtractor();
		Set<Cluster> localClusters = new LinkedHashSet<>();
		for (TestClass testClass : classes) {
			List<TestCase> tests = extractor.extract(List.of(testClass));
			if (!tests.isEmpty()) {
				localClusters.add(toCluster(tests));
			}
		}
		TestClassNamingStrategy namingStrategy = new IncrementalTestClassNamingStrategy();
		ClusterRefactor refactor = new SimpleClusterRefactor(namingStrategy);
		return refactor.refactor(localClusters);
	}

	private static List<TestClass> refactorGlobally(List<TestCase> tests) {
		TestCaseMaterializer materializer = new Junit4WithoutAssertionsTestCaseMaterializer(MATERIALIZER_OUTPUT);
		MaterializationReport materialization = materializer.materialize(tests);
		SimilarityMeasurer measurer = new LccssSimilarityMeasurer();
		SimilarityReport report = measurer.measure(materialization);
		Linkage linkage = new CompleteLinkage(report);
		Referee referee = new ComposedReferee(new BiggestClusterReferee(), new AnyClusterReferee());
		ThresholdCriterion criterion = new SimilarityBasedCriterion(SIMILARITY_THRESHOLD);
		TestCaseClusterer clusterer = new AgglomerativeHierarchicalClusteringTestCaseClusterer(linkage, referee, criterion);
		Set<Cluster> clusters = clusterer.cluster(report);
		TestClassNamingStrategy namingStrategy = new IncrementalTestClassNamingStrategy();
		ClusterRefactor refactor = new SimpleClusterRefactor(namingStrategy);
		return refactor.refactor(clusters);
	}

	private static Cluster toCluster(List<TestCase> tests) {
		Cluster cluster = new Cluster(tests.get(0));
		for (int index = 1; index < tests.size(); index++) {
			cluster = cluster.merge(new Cluster(tests.get(index)));
		}
		return cluster;
	}

	private static ExperimentRow measure(String strategy, List<TestClass> classes, SubjectData subject, long runtimeMillis) {
		StatementCount statementCount = countStatements(classes);
		long attributes = classes.stream().mapToLong(testClass -> testClass.getFields().size()).sum();
		long setupMethods = classes.stream().mapToLong(testClass -> testClass.getSetupMethods().size()).sum();
		long testMethods = classes.stream().mapToLong(testClass -> testClass.getTestMethods().size()).sum();
		return new ExperimentRow(strategy, subject.javaFiles, subject.skippedFiles.size(), classes.size(), attributes, setupMethods, testMethods, statementCount.statementCount, statementCount.uniqueDuplicateCount, statementCount.totalDuplicateCount, runtimeMillis);
	}

	private static StatementCount countStatements(List<TestClass> testClasses) {
		Junit5TestCaseExtractor extractor = new Junit5TestCaseExtractor();
		Map<String, Integer> statementCounts = new TreeMap<>();
		int statementCount = 0;
		for (TestClass testClass : testClasses) {
			for (SetupMethod setupMethod : testClass.getSetupMethods()) {
				for (Statement statement : setupMethod.getStatements()) {
					if (!isAssertion(extractor, statement)) {
						statementCount++;
						statementCounts.put(statement.getText(), statementCounts.getOrDefault(statement.getText(), 0) + 1);
					}
				}
			}
			for (TestMethod testMethod : testClass.getTestMethods()) {
				for (Statement statement : testMethod.getStatements()) {
					if (!isAssertion(extractor, statement)) {
						statementCount++;
						statementCounts.put(statement.getText(), statementCounts.getOrDefault(statement.getText(), 0) + 1);
					}
				}
			}
		}
		int uniqueDuplicates = 0;
		int totalDuplicates = 0;
		for (Integer occurrences : statementCounts.values()) {
			if (occurrences > 1) {
				uniqueDuplicates++;
				totalDuplicates += occurrences - 1;
			}
		}
		return new StatementCount(statementCount, uniqueDuplicates, totalDuplicates);
	}

	private static boolean isAssertion(Junit5TestCaseExtractor extractor, Statement statement) {
		try {
			return extractor.statementIsAssertion(statement);
		} catch (RuntimeException exception) {
			return false;
		}
	}

	private static void writeSummary(List<ExperimentRow> rows) {
		int originalTotalDuplicates = rows.get(0).totalDuplicates;
		CommaSeparatedValues csv = new CommaSeparatedValues();
		csv.addLine("Strategy", "Subject", "Java files", "Skipped files", "Classes", "Attributes", "Setup methods", "Test methods", "Statements", "Unique duplicates", "Total duplicates", "Total duplicate reduction", "Total duplicate reduction percent", "Runtime millis");
		for (ExperimentRow row : rows) {
			int reduction = originalTotalDuplicates - row.totalDuplicates;
			BigDecimal reductionPercent = percentage(reduction, originalTotalDuplicates);
			csv.addLine(row.strategy, SUBJECT, row.javaFiles, row.skippedFiles, row.classes, row.attributes, row.setupMethods, row.testMethods, row.statements, row.uniqueDuplicates, row.totalDuplicates, reduction, reductionPercent, row.runtimeMillis);
		}
		new FolderUtils(RESULTS).writeContetAsString("summary.csv", csv.getContent());
	}

	private static void writeSkippedFiles(SubjectData subject) {
		CommaSeparatedValues csv = new CommaSeparatedValues();
		csv.addLine("File", "Exception", "Message");
		for (SkippedFile skippedFile : subject.skippedFiles) {
			csv.addLine(skippedFile.fileName, skippedFile.exception, skippedFile.message);
		}
		new FolderUtils(RESULTS).writeContetAsString("skipped-files.csv", csv.getContent());
	}

	private static void writeConfiguration() {
		CommaSeparatedValues csv = new CommaSeparatedValues();
		csv.addLine("Property", "Value");
		csv.addLine("Subject", SUBJECT);
		csv.addLine("Source repository", "https://github.com/apache/commons-lang");
		csv.addLine("Source commit", SOURCE_COMMIT);
		csv.addLine("Source folder", "src/test/java/org/apache/commons/lang3");
		csv.addLine("Roza resource folder", SUBJECT_TESTS);
		csv.addLine("File limit environment variable", FILE_LIMIT_ENVIRONMENT_VARIABLE);
		csv.addLine("File limit value", getFileLimit() <= 0 ? "all" : getFileLimit());
		csv.addLine("Similarity metric", "LCCSS");
		csv.addLine("Global linkage", "Complete linkage");
		csv.addLine("Global referee", "ComposedReferee(BiggestClusterReferee, AnyClusterReferee)");
		csv.addLine("Global threshold", SIMILARITY_THRESHOLD);
		csv.addLine("Local-only baseline", "Refactor each original test class independently without redistributing tests across classes");
		new FolderUtils(RESULTS).writeContetAsString("configuration.csv", csv.getContent());
	}

	private static int getFileLimit() {
		String value = System.getenv(FILE_LIMIT_ENVIRONMENT_VARIABLE);
		if (value == null || value.isBlank()) {
			return 0;
		}
		return Integer.parseInt(value);
	}

	private static BigDecimal percentage(int numerator, int denominator) {
		if (denominator == 0) {
			return BigDecimal.ZERO;
		}
		return BigDecimal.valueOf(numerator).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(denominator), 2, RoundingMode.HALF_UP);
	}

	private static long elapsedMillis(long started) {
		return (System.nanoTime() - started) / 1_000_000;
	}

	private static String sanitize(String message) {
		if (message == null) {
			return "";
		}
		return message.replace('\n', ' ').replace('\r', ' ').replace(';', ',');
	}

	private static class SubjectData {

		private final int javaFiles;
		private final List<TestClass> classes;
		private final List<TestCase> tests;
		private final List<SkippedFile> skippedFiles;

		private SubjectData(int javaFiles, List<TestClass> classes, List<TestCase> tests, List<SkippedFile> skippedFiles) {
			this.javaFiles = javaFiles;
			this.classes = classes;
			this.tests = tests;
			this.skippedFiles = skippedFiles;
		}
	}

	private static class SkippedFile {

		private final String fileName;
		private final String exception;
		private final String message;

		private SkippedFile(String fileName, String exception, String message) {
			this.fileName = fileName;
			this.exception = exception;
			this.message = message;
		}
	}

	private static class StatementCount {

		private final int statementCount;
		private final int uniqueDuplicateCount;
		private final int totalDuplicateCount;

		private StatementCount(int statementCount, int uniqueDuplicateCount, int totalDuplicateCount) {
			this.statementCount = statementCount;
			this.uniqueDuplicateCount = uniqueDuplicateCount;
			this.totalDuplicateCount = totalDuplicateCount;
		}
	}

	private static class ExperimentRow {

		private final String strategy;
		private final int javaFiles;
		private final int skippedFiles;
		private final int classes;
		private final long attributes;
		private final long setupMethods;
		private final long testMethods;
		private final int statements;
		private final int uniqueDuplicates;
		private final int totalDuplicates;
		private final long runtimeMillis;

		private ExperimentRow(String strategy, int javaFiles, int skippedFiles, int classes, long attributes, long setupMethods, long testMethods, int statements, int uniqueDuplicates, int totalDuplicates, long runtimeMillis) {
			this.strategy = strategy;
			this.javaFiles = javaFiles;
			this.skippedFiles = skippedFiles;
			this.classes = classes;
			this.attributes = attributes;
			this.setupMethods = setupMethods;
			this.testMethods = testMethods;
			this.statements = statements;
			this.uniqueDuplicates = uniqueDuplicates;
			this.totalDuplicates = totalDuplicates;
			this.runtimeMillis = runtimeMillis;
		}
	}
}
