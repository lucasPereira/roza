package br.ufsc.ine.leb.roza.expt.i;

import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

import br.ufsc.ine.leb.roza.core.legacy.utils.CommaSeparatedValues;
import br.ufsc.ine.leb.roza.core.legacy.utils.FolderUtils;
import br.ufsc.ine.leb.roza.core.modern.analytics.TestClassMetrics;
import br.ufsc.ine.leb.roza.core.modern.analytics.TestClassMetricsCalculator;
import br.ufsc.ine.leb.roza.core.modern.clustering.AgglomerativeHierarchicalTestCaseClusterer;
import br.ufsc.ine.leb.roza.core.modern.clustering.ClusteringLevel;
import br.ufsc.ine.leb.roza.core.modern.clustering.CompositeMergeTieBreaker;
import br.ufsc.ine.leb.roza.core.modern.clustering.CompositeStopCriterion;
import br.ufsc.ine.leb.roza.core.modern.clustering.SingleLinkage;
import br.ufsc.ine.leb.roza.core.modern.clustering.StableTestCaseOrderMergeTieBreaker;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseClusters;
import br.ufsc.ine.leb.roza.core.modern.decomposition.DefaultTestCaseDecomposer;
import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.loading.FileSystemCodeFileLoader;
import br.ufsc.ine.leb.roza.core.modern.loading.LoadedCodeFiles;
import br.ufsc.ine.leb.roza.core.modern.measurement.LccssTestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.LcsTestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMatrix;
import br.ufsc.ine.leb.roza.core.modern.parsing.JunitTestClassParser;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.refactoring.ImplicitSetupTestClassRefactorer;

public final class Experiment {

	private static final Path SOURCE_ROOT = Path.of("src/test/java");
	private static final FolderUtils RESULTS = new FolderUtils("experiment-results/i");

	public static void main(String[] args) {
		RESULTS.createEmptyFolder();
		Subject subject = loadSubject();
		writeEligibleMetrics(subject);
		writeClusteringLevelMetrics(subject, "lccss", new LccssTestCaseSimilarityMeasurer());
		writeClusteringLevelMetrics(subject, "lcs", new LcsTestCaseSimilarityMeasurer());
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

	private static List<ClusteringLevel> clusteringLevels(DecomposedTestCases accepted, TestCaseSimilarityMeasurer similarityMeasurer) {
		TestCaseSimilarityMatrix matrix = similarityMeasurer.measure(accepted);
		AgglomerativeHierarchicalTestCaseClusterer clusterer = new AgglomerativeHierarchicalTestCaseClusterer(
				new SingleLinkage(),
				new CompositeStopCriterion(List.of()),
				new CompositeMergeTieBreaker(List.of(new StableTestCaseOrderMergeTieBreaker())));
		return clusterer.generateLevels(matrix);
	}

	private static void writeEligibleMetrics(Subject subject) {
		CommaSeparatedValues writer = new CommaSeparatedValues();
		writer.addLine(
				"test_classes",
				"test_methods",
				"setup_methods",
				"attributes",
				"total_statements",
				"duplicated_statements",
				"duplication_rate");
		TestClassMetrics metrics = TestClassMetricsCalculator.forEligibleCode(subject.parsedTestClasses(), subject.acceptedTestCases());
		writer.addLine(
				metrics.testClasses(),
				metrics.testMethods(),
				metrics.setupMethods(),
				metrics.attributes(),
				metrics.totalStatements(),
				metrics.duplicatedStatements(),
				formatDuplicationRate(metrics.duplicationRate()));
		RESULTS.writeContetAsString("eligible.csv", writer.getContent());
	}

	private static void writeClusteringLevelMetrics(Subject subject, String similarityMetric, TestCaseSimilarityMeasurer similarityMeasurer) {
		ImplicitSetupTestClassRefactorer refactorer = new ImplicitSetupTestClassRefactorer();
		CommaSeparatedValues writer = new CommaSeparatedValues();
		writer.addLine(
				"level",
				"test_classes",
				"test_methods",
				"setup_methods",
				"attributes",
				"total_statements",
				"duplicated_statements",
				"duplication_rate");
		for (ClusteringLevel level : clusteringLevels(subject.acceptedTestCases(), similarityMeasurer)) {
			TestClassMetrics metrics = TestClassMetricsCalculator.forTestClasses(
					refactorer.refactor(new TestCaseClusters(level.clusters())).testClasses());
			writer.addLine(
					displayLevel(level),
					metrics.testClasses(),
					metrics.testMethods(),
					metrics.setupMethods(),
					metrics.attributes(),
					metrics.totalStatements(),
					metrics.duplicatedStatements(),
					formatDuplicationRate(metrics.duplicationRate()));
		}
		RESULTS.writeContetAsString("refactored-by-level-" + similarityMetric + ".csv", writer.getContent());
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
}
