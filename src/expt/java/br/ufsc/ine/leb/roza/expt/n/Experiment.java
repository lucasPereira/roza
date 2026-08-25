package br.ufsc.ine.leb.roza.expt.n;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.legacy.utils.CommaSeparatedValues;
import br.ufsc.ine.leb.roza.core.legacy.utils.FolderUtils;
import br.ufsc.ine.leb.roza.core.modern.analytics.RefactoringLevelRanker;
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
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCaseDecomposer;
import br.ufsc.ine.leb.roza.core.modern.decomposition.WithoutImplicitSetupTestCaseDecomposer;
import br.ufsc.ine.leb.roza.core.modern.loading.FileSystemCodeFileLoader;
import br.ufsc.ine.leb.roza.core.modern.loading.LoadedCodeFiles;
import br.ufsc.ine.leb.roza.core.modern.measurement.ContiguousCommonStatementsSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.LccssTestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseCluster;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.JunitTestClassParser;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.refactoring.DelegatedSetupTestClassRefactorer;
import br.ufsc.ine.leb.roza.core.modern.refactoring.ImplicitSetupTestClassRefactorer;
import br.ufsc.ine.leb.roza.core.modern.refactoring.RankingSetupContributor;
import br.ufsc.ine.leb.roza.core.modern.refactoring.RefactoredTestClasses;
import br.ufsc.ine.leb.roza.core.modern.refactoring.ResidualImplicitSetupTestClassRefactorer;
import br.ufsc.ine.leb.roza.core.modern.refactoring.TestClassRefactorer;
import br.ufsc.ine.leb.roza.expt.n.ThesisTables.ResultRow;

public final class Experiment {

	private static final FolderUtils RESULTS = new FolderUtils("experiment-results/n");
	private static final int VARIANT_COUNT = ThesisTables.VARIANTS.size();

	public static void main(String[] args) {
		long startedAt = System.currentTimeMillis();
		RESULTS.createEmptyFolder();
		List<Subjects.Subject> subjects = Subjects.all();
		ExperimentProgress progress = new ExperimentProgress(subjects.size(), VARIANT_COUNT);
		System.out.printf(Locale.ROOT, "Experiment n: %d subjects × %d variants. Gradle's bar is this JavaExec task, not the experiment.%n", subjects.size(), VARIANT_COUNT);
		List<ResultRow> rows = new ArrayList<>();
		CommaSeparatedValues skipped = new CommaSeparatedValues();
		skipped.addLine("project", "reason");
		for (Subjects.Subject subject : subjects) {
			List<Path> folders = subject.existingFolders();
			if (folders.isEmpty()) {
				skipped.addLine(subject.name(), "no test folders");
				progress.beginSubject(subject.name(), 0);
				progress.abandonSubject();
				continue;
			}
			ParsedTestClasses original;
			try {
				original = parse(folders);
			} catch (RuntimeException exception) {
				progress.beginSubject(subject.name(), 0);
				skip(subject.name(), exception, skipped, progress, rows);
				continue;
			}
			int tests = original.testClasses().stream().mapToInt(testClass -> testClass.testMethods().size()).sum();
			if (tests == 0) {
				skipped.addLine(subject.name(), "no parseable tests");
				progress.beginSubject(subject.name(), 0);
				progress.abandonSubject();
				continue;
			}
			RefactoredTestClasses originalSuite = new RefactoredTestClasses(original.testClasses());
			rows.add(row(subject.name(), "original", originalSuite, originalSuite));
			progress.beginSubject(subject.name(), tests);
			try {
				runSubject(subject, original, rows, progress);
			} catch (Throwable throwable) {
				skip(subject.name(), throwable, skipped, progress, rows);
				continue;
			}
			persist(rows, skipped);
		}
		writeComparison(rows);
		try {
			writeCharts(rows);
			writeThesisTables(rows);
		} catch (RuntimeException exception) {
			System.err.println("Could not write charts or thesis tables: " + exception);
		}
		RESULTS.writeContetAsString("skipped.csv", skipped.getContent());
		System.out.printf("Experiment n finished. Results: %s. Total time: %.1fs%n", RESULTS.getBaseFolder(), (System.currentTimeMillis() - startedAt) / 1000.0);
	}

	private static void runSubject(
			Subjects.Subject subject,
			ParsedTestClasses original,
			List<ResultRow> rows,
			ExperimentProgress progress) {
		StrategyResult implicit = best("implicit", original, new DefaultTestCaseDecomposer(true), new LccssTestCaseSimilarityMeasurer(), new ImplicitSetupTestClassRefactorer(), progress);
		rows.add(row(subject.name(), "implicit", originalSuite(original), implicit.refactored));
		StrategyResult residual = best("residual-implicit", original, new DefaultTestCaseDecomposer(true), new LccssTestCaseSimilarityMeasurer(), new ResidualImplicitSetupTestClassRefactorer(), progress);
		rows.add(row(subject.name(), "residual-implicit", originalSuite(original), residual.refactored));
		StrategyResult delegated = best("delegated", original, new WithoutImplicitSetupTestCaseDecomposer(true), new ContiguousCommonStatementsSimilarityMeasurer(), new DelegatedSetupTestClassRefactorer(), progress);
		rows.add(row(subject.name(), "delegated", originalSuite(original), delegated.refactored));
		StrategyResult implicitDelegated = best("implicit+delegated", asParsed(implicit.refactored), new WithoutImplicitSetupTestCaseDecomposer(true), new ContiguousCommonStatementsSimilarityMeasurer(), new DelegatedSetupTestClassRefactorer(), progress);
		rows.add(row(subject.name(), "implicit+delegated", originalSuite(original), implicitDelegated.refactored));
		StrategyResult delegatedImplicit = best("delegated+implicit", asParsed(delegated.refactored), new DefaultTestCaseDecomposer(true), new LccssTestCaseSimilarityMeasurer(), new ImplicitSetupTestClassRefactorer(), progress);
		rows.add(row(subject.name(), "delegated+implicit", originalSuite(original), delegatedImplicit.refactored));
		StrategyResult residualDelegated = best("residual+delegated", asParsed(residual.refactored), new WithoutImplicitSetupTestCaseDecomposer(true), new ContiguousCommonStatementsSimilarityMeasurer(), new DelegatedSetupTestClassRefactorer(), progress);
		rows.add(row(subject.name(), "residual+delegated", originalSuite(original), residualDelegated.refactored));
		StrategyResult delegatedResidual = best("delegated+residual-implicit", asParsed(delegated.refactored), new DefaultTestCaseDecomposer(true), new LccssTestCaseSimilarityMeasurer(), new ResidualImplicitSetupTestClassRefactorer(), progress);
		rows.add(row(subject.name(), "delegated+residual-implicit", originalSuite(original), delegatedResidual.refactored));
	}

	private static StrategyResult best(
			String variant,
			ParsedTestClasses parsed,
			TestCaseDecomposer decomposer,
			TestCaseSimilarityMeasurer measurer,
			TestClassRefactorer refactorer,
			ExperimentProgress progress) {
		progress.beginVariant(variant);
		TestClassRefactorer preservingHelpers = withExistingHelpers(refactorer, parsed);
		DecomposedTestCases testCases = decomposer.decompose(parsed);
		if (testCases.testCases().isEmpty()) {
			progress.finishVariant();
			return new StrategyResult(new RefactoredTestClasses(parsed.testClasses()));
		}
		List<ClusteringLevel> levels = new AgglomerativeHierarchicalTestCaseClusterer(
				new SingleLinkage(),
				new CompositeStopCriterion(List.of()),
				new CompositeMergeTieBreaker(List.of(new StableTestCaseOrderMergeTieBreaker())))
						.generateLevels(measurer.measure(testCases, progress.stage("measure")), progress.stage("cluster"));
		int bestIndex = RefactoringLevelRanker.topLevelIndices(levels, preservingHelpers, 1, progress.stage("rank")).get(0);
		ClusteringLevel best = levels.get(bestIndex);
		StrategyResult result = new StrategyResult(preservingHelpers.refactor(new TestCaseClusters(best.clusters())));
		progress.finishVariant();
		return result;
	}

	static TestClassRefactorer withExistingHelpers(TestClassRefactorer refactorer, ParsedTestClasses parsed) {
		if (refactorer instanceof RankingSetupContributor) {
			return new HelperPreservingRankingRefactorer(refactorer, (RankingSetupContributor) refactorer, parsed);
		}
		return clusters -> refactorer.refactor(clusters).plusExistingHelpers(parsed);
	}

	private static void skip(
			String project,
			Throwable throwable,
			CommaSeparatedValues skipped,
			ExperimentProgress progress,
			List<ResultRow> rows) {
		String reason = throwable.toString();
		skipped.addLine(project, reason);
		System.err.printf(Locale.ROOT, "[%s] skipped: %s%n", project, reason);
		progress.abandonSubject();
		try {
			persist(rows, skipped);
		} catch (Throwable ignored) {
		}
		if (throwable instanceof OutOfMemoryError) {
			System.gc();
		}
	}

	private static void persist(List<ResultRow> rows, CommaSeparatedValues skipped) {
		writeComparison(rows);
		RESULTS.writeContetAsString("skipped.csv", skipped.getContent());
	}

	private static ParsedTestClasses parse(List<Path> folders) {
		LoadedCodeFiles loaded = new FileSystemCodeFileLoader(folders, true, List.of(".java")).load();
		return new JunitTestClassParser().parse(loaded);
	}

	private static ParsedTestClasses asParsed(RefactoredTestClasses refactored) {
		return new ParsedTestClasses(allClasses(refactored));
	}

	private static RefactoredTestClasses originalSuite(ParsedTestClasses parsed) {
		return new RefactoredTestClasses(parsed.testClasses());
	}

	private static ResultRow row(String project, String variant, RefactoredTestClasses original, RefactoredTestClasses current) {
		TestClassMetrics metrics = metricsOf(current);
		int helpers = allClasses(current).stream().mapToInt(testClass -> testClass.helperMethods().size()).sum();
		int originalDuplicated = metricsOf(original).duplicatedStatements();
		Double percent = originalDuplicated == 0 || "original".equals(variant)
				? ("original".equals(variant) ? 0.0 : null)
				: (metrics.duplicatedStatements() - originalDuplicated) * 100.0 / originalDuplicated;
		if ("original".equals(variant)) {
			percent = 0.0;
		}
		return new ResultRow(
				project,
				variant,
				metrics.testClasses(),
				metrics.setupMethods(),
				metrics.attributes(),
				helpers,
				metrics.totalStatements(),
				metrics.duplicatedStatements(),
				metrics.duplicationRate() * 100.0,
				percent);
	}

	private static void writeComparison(List<ResultRow> rows) {
		CommaSeparatedValues csv = new CommaSeparatedValues();
		csv.addLine(
				"project",
				"variant",
				"test_classes",
				"setups",
				"attributes",
				"helper_methods",
				"total_statements",
				"duplicated_statements",
				"duplication_rate",
				"duplication_difference_percentage");
		for (ResultRow row : rows) {
			csv.addLine(
					row.project,
					row.variant,
					row.testClasses,
					row.setups,
					row.attributes,
					row.helperMethods,
					row.totalStatements,
					row.duplicatedStatements,
					formatRate(row.duplicationRate),
					row.duplicationDifferencePercentage == null ? "" : formatRate(row.duplicationDifferencePercentage));
		}
		RESULTS.writeContetAsString("comparison.csv", csv.getContent());
	}

	private static void writeCharts(List<ResultRow> rows) {
		List<String> projects = rows.stream().map(row -> row.project).distinct().collect(Collectors.toList());
		RESULTS.writeContetAsString(
				"duplicated-statements.svg",
				GroupedBarChart.svg("Sentenças duplicadas", "Sentenças duplicadas", projects, ThesisTables.TREATMENTS, series(rows, projects, row -> (double) row.duplicatedStatements), false));
		RESULTS.writeContetAsString(
				"duplication-rate.svg",
				GroupedBarChart.svg("Taxa de duplicação", "Taxa de duplicação (%)", projects, ThesisTables.TREATMENTS, series(rows, projects, row -> row.duplicationRate), true));
		RESULTS.writeContetAsString(
				"duplication-difference-percentage.svg",
				GroupedBarChart.svg("Porcentagem de diferença na duplicação", "Porcentagem de diferença (%)", projects, ThesisTables.TREATMENTS, series(rows, projects, row -> row.duplicationDifferencePercentage == null ? Double.NaN : row.duplicationDifferencePercentage), false));
	}

	private static List<List<Double>> series(List<ResultRow> rows, List<String> projects, java.util.function.Function<ResultRow, Double> metric) {
		List<List<Double>> values = new ArrayList<>();
		for (String treatment : ThesisTables.TREATMENTS) {
			List<Double> series = new ArrayList<>();
			for (String project : projects) {
				ResultRow match = rows.stream().filter(row -> row.project.equals(project) && row.variant.equals(treatment)).findFirst().orElse(null);
				series.add(match == null ? Double.NaN : metric.apply(match));
			}
			values.add(series);
		}
		return values;
	}

	private static void writeThesisTables(List<ResultRow> rows) {
		if (rows.isEmpty()) {
			return;
		}
		RESULTS.writeContetAsString("vs-original-duplicated-statements.csv", ThesisTables.vsOriginal(rows, "duplicated_statements", row -> row.duplicatedStatements, true));
		RESULTS.writeContetAsString("vs-original-test-classes.csv", ThesisTables.vsOriginal(rows, "test_classes", row -> row.testClasses, true));
		RESULTS.writeContetAsString("vs-original-setups.csv", ThesisTables.vsOriginal(rows, "setups", row -> row.setups, true));
		RESULTS.writeContetAsString("vs-original-attributes.csv", ThesisTables.vsOriginal(rows, "attributes", row -> row.attributes, true));
		RESULTS.writeContetAsString("vs-original-helper-methods.csv", ThesisTables.vsOriginal(rows, "helper_methods", row -> row.helperMethods, true));
		RESULTS.writeContetAsString("vs-original-total-statements.csv", ThesisTables.vsOriginal(rows, "total_statements", row -> row.totalStatements, true));
		RESULTS.writeContetAsString("friedman-duplicated-statements.csv", ThesisTables.friedman(rows));
		RESULTS.writeContetAsString("pairwise-duplicated-statements.csv", ThesisTables.pairwise(rows));
		RESULTS.writeContetAsString("medians-duplicated-statements.csv", ThesisTables.medians(rows));
		RESULTS.writeContetAsString("composition-duplicated-statements.csv", ThesisTables.composition(rows));
	}

	private static TestClassMetrics metricsOf(RefactoredTestClasses refactored) {
		return TestClassMetricsCalculator.forSetupCode(allClasses(refactored));
	}

	private static List<TestClass> allClasses(RefactoredTestClasses refactored) {
		List<TestClass> classes = new ArrayList<>(refactored.testClasses());
		classes.addAll(refactored.helperClasses());
		return classes;
	}

	private static String formatRate(double value) {
		return String.format(Locale.ROOT, "%.1f", value);
	}

	private static final class HelperPreservingRankingRefactorer implements TestClassRefactorer, RankingSetupContributor {

		private final TestClassRefactorer refactorer;
		private final RankingSetupContributor contributor;
		private final ParsedTestClasses parsed;

		private HelperPreservingRankingRefactorer(
				TestClassRefactorer refactorer,
				RankingSetupContributor contributor,
				ParsedTestClasses parsed) {
			this.refactorer = refactorer;
			this.contributor = contributor;
			this.parsed = parsed;
		}

		@Override
		public RefactoredTestClasses refactor(TestCaseClusters clusters) {
			return refactorer.refactor(clusters).plusExistingHelpers(parsed);
		}

		@Override
		public List<TestClass> sharedRankingClasses(List<TestCase> tests) {
			List<TestClass> classes = new ArrayList<>(contributor.sharedRankingClasses(tests));
			List<String> names = classes.stream().map(TestClass::qualifiedName).collect(Collectors.toList());
			for (TestClass helper : parsed.testClasses()) {
				if (helper.isHelperClass() && !names.contains(helper.qualifiedName())) {
					classes.add(helper);
					names.add(helper.qualifiedName());
				}
			}
			return classes;
		}

		@Override
		public List<TestClass> clusterRankingClasses(TestCaseCluster cluster) {
			return contributor.clusterRankingClasses(cluster);
		}

		@Override
		public boolean countsResidualSourceSetupWhileSingletonsRemain() {
			return contributor.countsResidualSourceSetupWhileSingletonsRemain();
		}

		@Override
		public TestClass residualSourceSetupClass(TestClass source) {
			return contributor.residualSourceSetupClass(source);
		}
	}

	private static final class StrategyResult {

		private final RefactoredTestClasses refactored;

		private StrategyResult(RefactoredTestClasses refactored) {
			this.refactored = refactored;
		}
	}
}
