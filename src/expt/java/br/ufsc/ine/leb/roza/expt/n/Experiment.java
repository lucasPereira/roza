package br.ufsc.ine.leb.roza.expt.n;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.legacy.utils.CommaSeparatedValues;
import br.ufsc.ine.leb.roza.core.legacy.utils.FileUtils;
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
import br.ufsc.ine.leb.roza.expt.n.StatisticalTables.ResultRow;

public final class Experiment {

	private static final FolderUtils RESULTS = new FolderUtils("experiment-results/n");
	private static final int VARIANT_COUNT = StatisticalTables.VARIANTS.size();
	private static final int CCS_MINIMUM_LENGTH = 2;

	public static void main(String[] args) {
		ExperimentOptions options = ExperimentOptions.parse(args);
		if (options.fromComparison()) {
			if (!comparisonFile().isFile()) {
				throw new IllegalStateException("Cannot use --from-comparison without " + comparisonFile().getPath());
			}
			List<ResultRow> rows = ExperimentResume.parseComparison(new FileUtils().readContetAsString(comparisonFile()));
			writeCharts(rows);
			writeTables(rows);
			System.out.printf("Rewrote experiment n derived files from %s%n", comparisonFile().getPath());
			return;
		}
		long startedAt = System.currentTimeMillis();
		List<ResultRow> rows;
		if (options.missingOnly()) {
			if (!comparisonFile().isFile()) {
				throw new IllegalStateException("Cannot use --missing-only without " + comparisonFile().getPath());
			}
			rows = ExperimentResume.parseComparison(new FileUtils().readContetAsString(comparisonFile()));
		} else {
			RESULTS.createEmptyFolder();
			rows = new ArrayList<>();
		}
		List<Subjects.Subject> subjects = options.subjects(Subjects.all(), rows);
		ExperimentProgress progress = new ExperimentProgress(subjects.size(), VARIANT_COUNT);
		if (options.missingOnly()) {
			System.out.printf(
					Locale.ROOT,
					"Experiment n missing-only: %s%n",
					subjects.isEmpty()
							? "nothing missing"
							: subjects.stream().map(Subjects.Subject::name).collect(Collectors.joining(", ")));
		}
		System.out.printf(
				Locale.ROOT,
				"Experiment n: %d subjects × %d variants. Gradle's bar is this JavaExec task, not the experiment.%n",
				subjects.size(),
				VARIANT_COUNT);
		CommaSeparatedValues skipped = newSkipped();
		for (Subjects.Subject subject : subjects) {
			if (ExperimentResume.subjectComplete(rows, subject.name())) {
				progress.beginSubject(subject.name(), 0);
				progress.abandonSubject();
				System.out.printf(Locale.ROOT, "[%s] already complete, skipping%n", subject.name());
				continue;
			}
			List<Path> folders = subject.existingFolders();
			if (folders.isEmpty()) {
				skipped.addLine(subject.name(), "*", "no test folders");
				progress.beginSubject(subject.name(), 0);
				progress.abandonSubject();
				persist(rows, skipped);
				continue;
			}
			ParsedTestClasses original;
			try {
				original = parse(folders);
			} catch (RuntimeException exception) {
				progress.beginSubject(subject.name(), 0);
				skipSubject(subject.name(), exception, skipped, progress, rows);
				continue;
			}
			int tests = original.testClasses().stream().mapToInt(testClass -> testClass.testMethods().size()).sum();
			if (tests == 0) {
				skipped.addLine(subject.name(), "*", "no parseable tests");
				progress.beginSubject(subject.name(), 0);
				progress.abandonSubject();
				persist(rows, skipped);
				continue;
			}
			RefactoredTestClasses originalSuite = new RefactoredTestClasses(original.testClasses());
			ExperimentResume.upsert(rows, row(subject.name(), "original", originalSuite));
			progress.beginSubject(subject.name(), tests);
			persist(rows, skipped);
			runSubject(subject, original, rows, skipped, progress);
			persist(rows, skipped);
		}
		writeComparison(rows);
		try {
			writeCharts(rows);
			writeTables(rows);
		} catch (RuntimeException exception) {
			System.err.println("Could not write charts or tables: " + exception);
		}
		RESULTS.writeContetAsString("skipped.csv", skipped.getContent());
		System.out.printf("Experiment n finished. Results: %s. Total time: %.1fs%n", RESULTS.getBaseFolder(), (System.currentTimeMillis() - startedAt) / 1000.0);
	}

	private static void runSubject(
			Subjects.Subject subject,
			ParsedTestClasses original,
			List<ResultRow> rows,
			CommaSeparatedValues skipped,
			ExperimentProgress progress) {
		Set<String> needed = ExperimentResume.neededVariants(ExperimentResume.variants(rows, subject.name()));
		StrategyResult implicit = runVariant(
				"implicit",
				needed,
				() -> original,
				new DefaultTestCaseDecomposer(true),
				new LccssTestCaseSimilarityMeasurer(),
				new ImplicitSetupTestClassRefactorer(),
				subject,
				rows,
				skipped,
				progress);
		StrategyResult residual = runVariant(
				"residual-implicit",
				needed,
				() -> original,
				new DefaultTestCaseDecomposer(true),
				new LccssTestCaseSimilarityMeasurer(),
				new ResidualImplicitSetupTestClassRefactorer(),
				subject,
				rows,
				skipped,
				progress);
		StrategyResult delegated = runVariant(
				"delegated",
				needed,
				() -> original,
				new WithoutImplicitSetupTestCaseDecomposer(true),
				new ContiguousCommonStatementsSimilarityMeasurer(CCS_MINIMUM_LENGTH),
				new DelegatedSetupTestClassRefactorer(CCS_MINIMUM_LENGTH),
				subject,
				rows,
				skipped,
				progress);
		runVariant(
				"implicit+delegated",
				needed,
				() -> parsedOrNull(implicit),
				new WithoutImplicitSetupTestCaseDecomposer(true),
				new ContiguousCommonStatementsSimilarityMeasurer(CCS_MINIMUM_LENGTH),
				new DelegatedSetupTestClassRefactorer(CCS_MINIMUM_LENGTH),
				subject,
				rows,
				skipped,
				progress);
		runVariant(
				"delegated+implicit",
				needed,
				() -> parsedOrNull(delegated),
				new DefaultTestCaseDecomposer(true),
				new LccssTestCaseSimilarityMeasurer(),
				new ImplicitSetupTestClassRefactorer(),
				subject,
				rows,
				skipped,
				progress);
		runVariant(
				"residual-implicit+delegated",
				needed,
				() -> parsedOrNull(residual),
				new WithoutImplicitSetupTestCaseDecomposer(true),
				new ContiguousCommonStatementsSimilarityMeasurer(CCS_MINIMUM_LENGTH),
				new DelegatedSetupTestClassRefactorer(CCS_MINIMUM_LENGTH),
				subject,
				rows,
				skipped,
				progress);
		runVariant(
				"delegated+residual-implicit",
				needed,
				() -> parsedOrNull(delegated),
				new DefaultTestCaseDecomposer(true),
				new LccssTestCaseSimilarityMeasurer(),
				new ResidualImplicitSetupTestClassRefactorer(),
				subject,
				rows,
				skipped,
				progress);
	}

	private static StrategyResult runVariant(
			String variant,
			Set<String> needed,
			Supplier<ParsedTestClasses> input,
			TestCaseDecomposer decomposer,
			TestCaseSimilarityMeasurer measurer,
			TestClassRefactorer refactorer,
			Subjects.Subject subject,
			List<ResultRow> rows,
			CommaSeparatedValues skipped,
			ExperimentProgress progress) {
		if (!needed.contains(variant)) {
			progress.alreadyDone(variant);
			return null;
		}
		ParsedTestClasses parsed = input.get();
		if (parsed == null) {
			String reason = "prerequisite missing";
			skipped.addLine(subject.name(), variant, reason);
			progress.skipUnrun(variant, reason);
			persist(rows, skipped);
			return null;
		}
		StrategyResult result;
		try {
			result = best(variant, parsed, decomposer, measurer, refactorer, progress);
		} catch (Throwable throwable) {
			if (throwable instanceof Error && !(throwable instanceof OutOfMemoryError)) {
				throw (Error) throwable;
			}
			String reason = throwable.toString();
			skipped.addLine(subject.name(), variant, reason);
			progress.failVariant(reason);
			System.err.printf(Locale.ROOT, "[%s] %s skipped: %s%n", subject.name(), variant, reason);
			try {
				persist(rows, skipped);
			} catch (Throwable ignored) {
			}
			if (throwable instanceof OutOfMemoryError) {
				System.gc();
			}
			return null;
		}
		ExperimentResume.upsert(rows, row(subject.name(), variant, result.refactored));
		persist(rows, skipped);
		return result;
	}

	private static ParsedTestClasses parsedOrNull(StrategyResult result) {
		return result == null ? null : asParsed(result.refactored);
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

	private static void skipSubject(
			String project,
			Throwable throwable,
			CommaSeparatedValues skipped,
			ExperimentProgress progress,
			List<ResultRow> rows) {
		String reason = throwable.toString();
		skipped.addLine(project, "*", reason);
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

	private static File comparisonFile() {
		return new File(RESULTS.getBaseFolder(), "comparison.csv");
	}

	private static CommaSeparatedValues newSkipped() {
		CommaSeparatedValues skipped = new CommaSeparatedValues();
		skipped.addLine("project", "variant", "reason");
		return skipped;
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

	private static ResultRow row(String project, String variant, RefactoredTestClasses current) {
		TestClassMetrics metrics = metricsOf(current);
		int helpers = allClasses(current).stream().mapToInt(testClass -> testClass.helperMethods().size()).sum();
		return new ResultRow(
				project,
				variant,
				metrics.testClasses(),
				metrics.setupMethods(),
				metrics.attributes(),
				helpers,
				metrics.totalStatements(),
				metrics.duplicatedStatements());
	}

	private static void writeComparison(List<ResultRow> rows) {
		CommaSeparatedValues csv = new CommaSeparatedValues();
		csv.addLine(
				"project",
				"variant",
				"test_classes",
				"setup_methods",
				"attributes",
				"helper_methods",
				"total_statements",
				"duplicated_statements");
		for (ResultRow row : ExperimentResume.ordered(rows, Subjects.all().stream().map(Subjects.Subject::name).collect(Collectors.toList()))) {
			csv.addLine(
					row.project,
					row.variant,
					row.testClasses,
					row.setupMethods,
					row.attributes,
					row.helperMethods,
					row.totalStatements,
					row.duplicatedStatements);
		}
		RESULTS.writeContetAsString("comparison.csv", csv.getContent());
	}

	private static void writeCharts(List<ResultRow> rows) {
		List<String> projects = rows.stream().map(row -> row.project).distinct().collect(Collectors.toList());
		Map<String, Integer> originalDuplicated = rows.stream()
				.filter(row -> "original".equals(row.variant))
				.collect(Collectors.toMap(row -> row.project, row -> row.duplicatedStatements, (left, right) -> left));
		List<List<Double>> variations = series(rows, projects, StatisticalTables.VARIANTS, row -> {
			Integer baseline = originalDuplicated.get(row.project);
			if (baseline == null || baseline == 0) {
				return Double.NaN;
			}
			return (row.duplicatedStatements - baseline) * 100.0 / baseline;
		});
		RESULTS.writeContetAsString(
				"duplication-variation-distribution.svg",
				DuplicationVariationDistributionChart.svg(projects, StatisticalTables.VARIANTS, variations));
		RESULTS.writeContetAsString(
				"duplication-variation-heatmap.svg",
				DuplicationVariationHeatmap.svg(projects, StatisticalTables.VARIANTS, variations));
	}

	private static List<List<Double>> series(
			List<ResultRow> rows,
			List<String> projects,
			List<String> treatments,
			java.util.function.Function<ResultRow, Double> metric) {
		List<List<Double>> values = new ArrayList<>();
		for (String treatment : treatments) {
			List<Double> series = new ArrayList<>();
			for (String project : projects) {
				ResultRow match = rows.stream().filter(row -> row.project.equals(project) && row.variant.equals(treatment)).findFirst().orElse(null);
				series.add(match == null ? Double.NaN : metric.apply(match));
			}
			values.add(series);
		}
		return values;
	}

	private static void writeTables(List<ResultRow> rows) {
		if (rows.isEmpty()) {
			return;
		}
		RESULTS.writeContetAsString("shapiro-vs-original.csv", StatisticalTables.shapiro(rows));
		RESULTS.writeContetAsString("vs-original-duplicated-statements.csv", StatisticalTables.vsOriginal(rows, row -> row.duplicatedStatements));
		RESULTS.writeContetAsString("vs-original-test-classes.csv", StatisticalTables.vsOriginal(rows, row -> row.testClasses));
		RESULTS.writeContetAsString("vs-original-setup-methods.csv", StatisticalTables.vsOriginal(rows, row -> row.setupMethods));
		RESULTS.writeContetAsString("vs-original-attributes.csv", StatisticalTables.vsOriginal(rows, row -> row.attributes));
		RESULTS.writeContetAsString("vs-original-helper-methods.csv", StatisticalTables.vsOriginal(rows, row -> row.helperMethods));
		RESULTS.writeContetAsString("vs-original-total-statements.csv", StatisticalTables.vsOriginal(rows, row -> row.totalStatements));
		RESULTS.writeContetAsString("friedman-duplicated-statements.csv", StatisticalTables.friedman(rows));
		RESULTS.writeContetAsString("pairwise-duplicated-statements.csv", StatisticalTables.pairwise(rows));
		RESULTS.writeContetAsString("medians-duplicated-statements.csv", StatisticalTables.medians(rows));
		RESULTS.writeContetAsString("composition-duplicated-statements.csv", StatisticalTables.composition(rows));
	}

	private static TestClassMetrics metricsOf(RefactoredTestClasses refactored) {
		return TestClassMetricsCalculator.forSetupCode(allClasses(refactored));
	}

	private static List<TestClass> allClasses(RefactoredTestClasses refactored) {
		List<TestClass> classes = new ArrayList<>(refactored.testClasses());
		classes.addAll(refactored.helperClasses());
		return classes;
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
