package br.ufsc.ine.leb.roza.expt.i;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.ufsc.ine.leb.roza.core.legacy.utils.CommaSeparatedValues;
import br.ufsc.ine.leb.roza.core.legacy.utils.FolderUtils;
import br.ufsc.ine.leb.roza.core.modern.analytics.TestClassMetrics;
import br.ufsc.ine.leb.roza.core.modern.analytics.TestClassMetricsCalculator;
import br.ufsc.ine.leb.roza.core.modern.clustering.AgglomerativeHierarchicalTestCaseClusterer;
import br.ufsc.ine.leb.roza.core.modern.clustering.ClusteringLevel;
import br.ufsc.ine.leb.roza.core.modern.clustering.CompositeMergeTieBreaker;
import br.ufsc.ine.leb.roza.core.modern.clustering.CompositeStopCriterion;
import br.ufsc.ine.leb.roza.core.modern.clustering.SingleLinkage;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseCluster;
import br.ufsc.ine.leb.roza.core.modern.clustering.StableTestCaseOrderMergeTieBreaker;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseClusters;
import br.ufsc.ine.leb.roza.core.modern.decomposition.DefaultTestCaseDecomposer;
import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.loading.FileSystemCodeFileLoader;
import br.ufsc.ine.leb.roza.core.modern.loading.LoadedCodeFiles;
import br.ufsc.ine.leb.roza.core.modern.measurement.LccssTestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMatrix;
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.parsing.JunitTestClassParser;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.refactoring.ImplicitSetupTestClassRefactorer;
import br.ufsc.ine.leb.roza.core.modern.refactoring.TestClassRefactorer;

public final class Experiment {

	private static final Path SOURCE_ROOT = Path.of("src/test/java");
	private static final FolderUtils RESULTS = new FolderUtils("experiment-results/i");

	public static void main(String[] args) {
		RESULTS.createEmptyFolder();
		Subject subject = loadSubject();
		TestClassMetrics original = writeEligibleMetrics(subject);
		writeControls(subject, original);
		BestLevel bestLevel = writeClusteringLevelMetrics(
				subject,
				"refactored-by-level.csv",
				"LCCSS",
				new LccssTestCaseSimilarityMeasurer(),
				new ImplicitSetupTestClassRefactorer());
		writeBestLevelSummary(original, List.of(bestLevel));
		System.out.printf(
				"Experiment i finished. Eligible tests: %d. Results: %s%n",
				subject.acceptedTestCases().testCases().size(),
				RESULTS.getBaseFolder());
	}

	private static Subject loadSubject() {
		Path sourceRoot = SOURCE_ROOT.toAbsolutePath().normalize();
		LoadedCodeFiles loaded = new FileSystemCodeFileLoader(sourceRoot, true, List.of(".java")).load();
		ParsedTestClasses parsed = new JunitTestClassParser().parse(loaded);
		DecomposedTestCases accepted = new DefaultTestCaseDecomposer().decompose(parsed);
		return new Subject(parsed, accepted);
	}

	private static List<ClusteringLevel> clusteringLevels(DecomposedTestCases accepted, TestCaseSimilarityMeasurer measurer) {
		TestCaseSimilarityMatrix matrix = measurer.measure(accepted);
		AgglomerativeHierarchicalTestCaseClusterer clusterer = new AgglomerativeHierarchicalTestCaseClusterer(
				new SingleLinkage(),
				new CompositeStopCriterion(List.of()),
				new CompositeMergeTieBreaker(List.of(new StableTestCaseOrderMergeTieBreaker())));
		return clusterer.generateLevels(matrix);
	}

	private static TestClassMetrics writeEligibleMetrics(Subject subject) {
		CommaSeparatedValues writer = new CommaSeparatedValues();
		addMetricsHeader(writer);
		TestClassMetrics metrics = TestClassMetricsCalculator.forEligibleSetupCode(
				subject.parsedTestClasses(),
				subject.acceptedTestCases());
		addMetricsLine(writer, metrics);
		RESULTS.writeContetAsString("eligible.csv", writer.getContent());
		return metrics;
	}

	private static void writeControls(Subject subject, TestClassMetrics original) {
		CommaSeparatedValues writer = new CommaSeparatedValues();
		writer.addLine(
				"control",
				"test_classes",
				"test_methods",
				"setup_methods",
				"attributes",
				"setup_statements",
				"duplicated_setup_statements",
				"setup_duplication_rate");
		addControlLine(writer, "original-filtered", original, original.testMethods());
		TestCaseClusters singletonClusters = singletonClusters(subject.acceptedTestCases());
		addControlLine(
				writer,
				"decomposed-unrecomposed",
				setupMetrics(new ImplicitSetupTestClassRefactorer(), singletonClusters),
				original.testMethods());
		TestCaseClusters sourcePartition = sourceClassPartition(subject.acceptedTestCases());
		addControlLine(
				writer,
				"source-partition",
				setupMetrics(new ImplicitSetupTestClassRefactorer(), sourcePartition),
				original.testMethods());
		RESULTS.writeContetAsString("controls.csv", writer.getContent());
	}

	private static TestClassMetrics setupMetrics(TestClassRefactorer refactorer, TestCaseClusters clusters) {
		return TestClassMetricsCalculator.forSetupCode(refactorer.refactor(clusters).testClasses());
	}

	private static TestCaseClusters singletonClusters(DecomposedTestCases accepted) {
		List<TestCaseCluster> clusters = new ArrayList<>();
		for (int index = 0; index < accepted.testCases().size(); index++) {
			clusters.add(new TestCaseCluster(index, accepted.testCases().get(index)));
		}
		return new TestCaseClusters(clusters);
	}

	private static TestCaseClusters sourceClassPartition(DecomposedTestCases accepted) {
		Map<String, TestCaseCluster> clustersBySourceClass = new LinkedHashMap<>();
		for (int index = 0; index < accepted.testCases().size(); index++) {
			TestCase testCase = accepted.testCases().get(index);
			String sourceClass = testCase.sourceTestClass().orElseThrow().qualifiedName();
			TestCaseCluster singleton = new TestCaseCluster(index, testCase);
			clustersBySourceClass.merge(sourceClass, singleton, TestCaseCluster::merge);
		}
		return new TestCaseClusters(new ArrayList<>(clustersBySourceClass.values()));
	}

	private static BestLevel writeClusteringLevelMetrics(
			Subject subject,
			String outputFile,
			String metric,
			TestCaseSimilarityMeasurer measurer,
			TestClassRefactorer refactorer) {
		CommaSeparatedValues writer = new CommaSeparatedValues();
		writer.addLine(
				"level",
				"test_classes",
				"test_methods",
				"setup_methods",
				"attributes",
				"setup_statements",
				"duplicated_setup_statements",
				"setup_duplication_rate");
		BestLevel best = null;
		for (ClusteringLevel level : clusteringLevels(subject.acceptedTestCases(), measurer)) {
			TestClassMetrics metrics = TestClassMetricsCalculator.forSetupCode(
					refactorer.refactor(new TestCaseClusters(level.clusters())).testClasses());
			if (metrics.testMethods() != subject.acceptedTestCases().testCases().size()) {
				throw new IllegalStateException("Every clustering level must conserve the accepted test corpus.");
			}
			writer.addLine(
					displayLevel(level),
					metrics.testClasses(),
					metrics.testMethods(),
					metrics.setupMethods(),
					metrics.attributes(),
					metrics.totalStatements(),
					metrics.duplicatedStatements(),
					formatDuplicationRate(metrics.duplicationRate()));
			if (best == null || metrics.duplicatedStatements() < best.metrics().duplicatedStatements()) {
				best = new BestLevel(metric, displayLevel(level), metrics);
			}
		}
		RESULTS.writeContetAsString(outputFile, writer.getContent());
		return best;
	}

	private static void writeBestLevelSummary(TestClassMetrics original, List<BestLevel> bestLevels) {
		CommaSeparatedValues writer = new CommaSeparatedValues();
		writer.addLine(
				"metric",
				"best_level",
				"setup_statements",
				"duplicated_setup_statements",
				"setup_duplication_rate",
				"duplicated_change_from_original");
		for (BestLevel best : bestLevels) {
			writer.addLine(
					best.metric(),
					best.level(),
					best.metrics().totalStatements(),
					best.metrics().duplicatedStatements(),
					formatDuplicationRate(best.metrics().duplicationRate()),
					best.metrics().duplicatedStatements() - original.duplicatedStatements());
		}
		RESULTS.writeContetAsString("best-level-summary.csv", writer.getContent());
	}

	private static void addMetricsHeader(CommaSeparatedValues writer) {
		writer.addLine(
				"test_classes",
				"test_methods",
				"setup_methods",
				"attributes",
				"setup_statements",
				"duplicated_setup_statements",
				"setup_duplication_rate");
	}

	private static void addMetricsLine(CommaSeparatedValues writer, TestClassMetrics metrics) {
		writer.addLine(
				metrics.testClasses(),
				metrics.testMethods(),
				metrics.setupMethods(),
				metrics.attributes(),
				metrics.totalStatements(),
				metrics.duplicatedStatements(),
				formatDuplicationRate(metrics.duplicationRate()));
	}

	private static void addControlLine(
			CommaSeparatedValues writer,
			String control,
			TestClassMetrics metrics,
			int expectedTestMethods) {
		if (metrics.testMethods() != expectedTestMethods) {
			throw new IllegalStateException("Experiment controls must contain the same accepted test corpus.");
		}
		writer.addLine(
				control,
				metrics.testClasses(),
				metrics.testMethods(),
				metrics.setupMethods(),
				metrics.attributes(),
				metrics.totalStatements(),
				metrics.duplicatedStatements(),
				formatDuplicationRate(metrics.duplicationRate()));
	}

	private static int displayLevel(ClusteringLevel level) {
		return level.number() + 1;
	}

	private static String formatDuplicationRate(double rate) {
		return String.format(Locale.ROOT, "%.1f", rate * 100.0);
	}

	private static final class Subject {

		private final ParsedTestClasses parsedTestClasses;
		private final DecomposedTestCases acceptedTestCases;

		private Subject(ParsedTestClasses parsedTestClasses, DecomposedTestCases acceptedTestCases) {
			this.parsedTestClasses = parsedTestClasses;
			this.acceptedTestCases = acceptedTestCases;
		}

		private ParsedTestClasses parsedTestClasses() {
			return parsedTestClasses;
		}

		private DecomposedTestCases acceptedTestCases() {
			return acceptedTestCases;
		}
	}

	private static final class BestLevel {

		private final String metric;
		private final int level;
		private final TestClassMetrics metrics;

		private BestLevel(String metric, int level, TestClassMetrics metrics) {
			this.metric = metric;
			this.level = level;
			this.metrics = metrics;
		}

		private String metric() {
			return metric;
		}

		private int level() {
			return level;
		}

		private TestClassMetrics metrics() {
			return metrics;
		}
	}
}
