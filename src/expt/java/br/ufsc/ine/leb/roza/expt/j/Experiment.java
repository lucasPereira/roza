package br.ufsc.ine.leb.roza.expt.j;

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
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMatrix;
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.parsing.JunitTestClassParser;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.refactoring.ImplicitSetupTestClassRefactorer;
import br.ufsc.ine.leb.roza.core.modern.refactoring.TestClassRefactorer;

public final class Experiment {

	private static final Path SOURCE_ROOT = Path.of("external-projects/saas-unificado/saas+teste");
	private static final FolderUtils RESULTS = new FolderUtils("experiment-results/j");
	private static final int PROGRESS_INTERVAL = 100;

	public static void main(String[] args) {
		long startedAt = System.currentTimeMillis();
		RESULTS.createEmptyFolder();
		System.out.printf("Loading tests from %s%n", SOURCE_ROOT.toAbsolutePath().normalize());
		Subject subject = loadSubject();
		System.out.printf(
				"Loaded %d test classes, %d eligible tests, %d violations in %.1fs%n",
				subject.parsedTestClasses().testClasses().size(),
				subject.acceptedTestCases().testCases().size(),
				subject.parsedTestClasses().violations().size(),
				elapsedSeconds(startedAt));
		writeEligibleMetrics(subject);
		writeClusteringLevelMetrics(subject, "refactored-by-level.csv", "LCCSS", new LccssTestCaseSimilarityMeasurer(), new ImplicitSetupTestClassRefactorer());
		System.out.printf(
				"Experiment j finished. Eligible tests: %d. Results: %s. Total time: %.1fs%n",
				subject.acceptedTestCases().testCases().size(),
				RESULTS.getBaseFolder(),
				elapsedSeconds(startedAt));
	}

	private static Subject loadSubject() {
		Path sourceRoot = SOURCE_ROOT.toAbsolutePath().normalize();
		LoadedCodeFiles loaded = new FileSystemCodeFileLoader(sourceRoot, true, List.of(".java")).load();
		ParsedTestClasses parsed = new JunitTestClassParser().parse(loaded);
		DecomposedTestCases accepted = new DefaultTestCaseDecomposer().decompose(parsed);
		return new Subject(parsed, accepted);
	}

	private static List<ClusteringLevel> clusteringLevels(DecomposedTestCases accepted, String metricLabel, TestCaseSimilarityMeasurer measurer) {
		long startedAt = System.currentTimeMillis();
		System.out.printf("Measuring %s similarity for %d tests...%n", metricLabel, accepted.testCases().size());
		TestCaseSimilarityMatrix matrix = measurer.measure(accepted);
		System.out.printf("%s similarity measured in %.1fs%n", metricLabel, elapsedSeconds(startedAt));
		long clusteringStartedAt = System.currentTimeMillis();
		System.out.printf("Generating clustering levels for %s...%n", metricLabel);
		AgglomerativeHierarchicalTestCaseClusterer clusterer = new AgglomerativeHierarchicalTestCaseClusterer(
				new SingleLinkage(),
				new CompositeStopCriterion(List.of()),
				new CompositeMergeTieBreaker(List.of(new StableTestCaseOrderMergeTieBreaker())));
		List<ClusteringLevel> levels = clusterer.generateLevels(matrix);
		System.out.printf("%s clustering levels generated in %.1fs%n", metricLabel, elapsedSeconds(clusteringStartedAt));
		return levels;
	}

	private static void writeEligibleMetrics(Subject subject) {
		CommaSeparatedValues writer = new CommaSeparatedValues();
		writer.addLine(
				"test_classes",
				"test_methods",
				"setup_methods",
				"attributes",
				"setup_statements",
				"duplicated_setup_statements",
				"setup_duplication_rate");
		TestClassMetrics metrics = TestClassMetricsCalculator.forEligibleSetupCode(
				subject.parsedTestClasses(),
				subject.acceptedTestCases());
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

	private static void writeClusteringLevelMetrics(
			Subject subject,
			String outputFile,
			String metricLabel,
			TestCaseSimilarityMeasurer measurer,
			TestClassRefactorer refactorer) {
		long startedAt = System.currentTimeMillis();
		List<ClusteringLevel> levels = clusteringLevels(subject.acceptedTestCases(), metricLabel, measurer);
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
		int levelCount = levels.size();
		for (ClusteringLevel level : levels) {
			int displayLevel = displayLevel(level);
			if (displayLevel == 1 || displayLevel % PROGRESS_INTERVAL == 0 || displayLevel == levelCount) {
				System.out.printf(
						"Refactoring %s level %d/%d (%.1fs elapsed)%n",
						metricLabel,
						displayLevel,
						levelCount,
						elapsedSeconds(startedAt));
			}
			TestClassMetrics metrics = TestClassMetricsCalculator.forSetupCode(
					refactorer.refactor(new TestCaseClusters(level.clusters())).testClasses());
			writer.addLine(
					displayLevel,
					metrics.testClasses(),
					metrics.testMethods(),
					metrics.setupMethods(),
					metrics.attributes(),
					metrics.totalStatements(),
					metrics.duplicatedStatements(),
					formatDuplicationRate(metrics.duplicationRate()));
		}
		RESULTS.writeContetAsString(outputFile, writer.getContent());
		System.out.printf("Wrote %s metrics for %d levels in %.1fs%n", metricLabel, levelCount, elapsedSeconds(startedAt));
	}

	private static int displayLevel(ClusteringLevel level) {
		return level.number() + 1;
	}

	private static String formatDuplicationRate(double rate) {
		return String.format(Locale.ROOT, "%.1f", rate * 100.0);
	}

	private static double elapsedSeconds(long startedAt) {
		return (System.currentTimeMillis() - startedAt) / 1000.0;
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
