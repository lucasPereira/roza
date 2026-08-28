package br.ufsc.ine.leb.roza.expt.m;

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
import br.ufsc.ine.leb.roza.core.modern.clustering.AverageLinkage;
import br.ufsc.ine.leb.roza.core.modern.clustering.ClusteringLevel;
import br.ufsc.ine.leb.roza.core.modern.clustering.CompositeStopCriterion;
import br.ufsc.ine.leb.roza.core.modern.clustering.StableTestCaseOrderMergeTieBreaker;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseClusters;
import br.ufsc.ine.leb.roza.core.modern.decomposition.DefaultTestCaseDecomposer;
import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCaseDecomposer;
import br.ufsc.ine.leb.roza.core.modern.decomposition.WithoutImplicitSetupTestCaseDecomposer;
import br.ufsc.ine.leb.roza.core.modern.loading.FileSystemCodeFileLoader;
import br.ufsc.ine.leb.roza.core.modern.measurement.ContiguousCommonStatementsSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.LccssTestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.parsing.JunitTestClassParser;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestCodeViolation;
import br.ufsc.ine.leb.roza.core.modern.refactoring.DelegatedSetupTestClassRefactorer;
import br.ufsc.ine.leb.roza.core.modern.refactoring.ImplicitSetupTestClassRefactorer;
import br.ufsc.ine.leb.roza.core.modern.refactoring.RefactoredTestClasses;
import br.ufsc.ine.leb.roza.core.modern.refactoring.TestClassRefactorer;
import br.ufsc.ine.leb.roza.core.modern.writing.FileSystemTestClassWriter;

public final class Experiment {

	private static final Path SOURCE = Path.of("src/expt/resources/m");
	private static final FolderUtils RESULTS = new FolderUtils("experiment-results/m");
	private static final List<String> STRATEGIES = List.of(
			"Original",
			"Implicit",
			"Delegated",
			"Implicit + Delegated",
			"Delegated + Implicit");
	private static final List<String> COLORS = List.of("#6b7280", "#2563eb", "#d97706", "#059669", "#7c3aed");

	public static void main(String[] args) {
		RESULTS.createEmptyFolder();
		ParsedTestClasses original = parse(SOURCE);
		TestClassMetrics originalMetrics = TestClassMetricsCalculator.forSetupCode(original.testClasses());
		writeSuite("original", new RefactoredTestClasses(original.testClasses()));

		StrategyResult implicit = bestLevel(
				"implicit-setup",
				original,
				new DefaultTestCaseDecomposer(),
				new LccssTestCaseSimilarityMeasurer(),
				new ImplicitSetupTestClassRefactorer());
		StrategyResult delegated = bestLevel(
				"delegated-setup",
				original,
				new WithoutImplicitSetupTestCaseDecomposer(),
				new ContiguousCommonStatementsSimilarityMeasurer(1),
				new DelegatedSetupTestClassRefactorer());
		StrategyResult implicitThenDelegated = bestLevel(
				"implicit-then-delegated",
				asParsed(implicit.refactored()),
				new WithoutImplicitSetupTestCaseDecomposer(),
				new ContiguousCommonStatementsSimilarityMeasurer(1),
				new DelegatedSetupTestClassRefactorer());
		StrategyResult delegatedThenImplicit = bestLevel(
				"delegated-then-implicit",
				asParsed(delegated.refactored()),
				new DefaultTestCaseDecomposer(),
				new LccssTestCaseSimilarityMeasurer(),
				new ImplicitSetupTestClassRefactorer());

		List<StrategyResult> results = List.of(
				new StrategyResult("Original", "-", originalMetrics, new RefactoredTestClasses(original.testClasses())),
				implicit.named("Implicit"),
				delegated.named("Delegated"),
				implicitThenDelegated.named("Implicit + Delegated"),
				delegatedThenImplicit.named("Delegated + Implicit"));
		writeComparison(results);
		writeBarChart("total-statements.svg", "Total statements", "Statements", results.stream().map(result -> (double) result.metrics().totalStatements()).collect(Collectors.toList()), false);
		writeBarChart("duplicated-statements.svg", "Duplicated statements", "Statements", results.stream().map(result -> (double) result.metrics().duplicatedStatements()).collect(Collectors.toList()), false);
		writeBarChart("duplication-rate.svg", "Duplication rate", "Duplication rate (%)", results.stream().map(result -> result.metrics().duplicationRate() * 100.0).collect(Collectors.toList()), true);
		System.out.printf("Experiment m finished. Results: %s%n", RESULTS.getBaseFolder());
		for (StrategyResult result : results) {
			System.out.printf(
					"  %s: level %s, %d statements, %d duplicated (%.1f%%)%n",
					result.name(),
					result.level(),
					result.metrics().totalStatements(),
					result.metrics().duplicatedStatements(),
					result.metrics().duplicationRate() * 100.0);
		}
	}

	private static ParsedTestClasses parse(Path folder) {
		ParsedTestClasses parsed = new JunitTestClassParser().parse(new FileSystemCodeFileLoader(folder, false, List.of(".java")).load());
		if (!parsed.violations().isEmpty()) {
			String violations = parsed.violations().stream().map(TestCodeViolation::description).collect(Collectors.joining("; "));
			throw new IllegalStateException("Experiment m expected a fully eligible suite, but parsing reported: " + violations);
		}
		return parsed;
	}

	private static ParsedTestClasses asParsed(RefactoredTestClasses refactored) {
		return new ParsedTestClasses(allClasses(refactored));
	}

	private static StrategyResult bestLevel(
			String outputFolder,
			ParsedTestClasses parsed,
			TestCaseDecomposer decomposer,
			TestCaseSimilarityMeasurer measurer,
			TestClassRefactorer refactorer) {
		TestClassRefactorer preservingHelpers = clusters -> refactorer.refactor(clusters).plusExistingHelpers(parsed);
		DecomposedTestCases testCases = decomposer.decompose(parsed);
		List<ClusteringLevel> levels = new AgglomerativeHierarchicalTestCaseClusterer(
				new AverageLinkage(),
				new CompositeStopCriterion(List.of()),
				new StableTestCaseOrderMergeTieBreaker()).generateLevels(measurer.measure(testCases));
		writeLevelMetrics(outputFolder + "-by-level.csv", testCases, levels, preservingHelpers);
		int bestIndex = RefactoringLevelRanker.topLevelIndices(levels, preservingHelpers, 1).get(0);
		ClusteringLevel best = levels.get(bestIndex);
		RefactoredTestClasses refactored = preservingHelpers.refactor(new TestCaseClusters(best.clusters()));
		writeSuite(outputFolder, refactored);
		return new StrategyResult(outputFolder, Integer.toString(displayLevel(best)), metricsOf(refactored), refactored);
	}

	private static void writeSuite(String folder, RefactoredTestClasses refactored) {
		new FileSystemTestClassWriter(Path.of(RESULTS.getBaseFolder(), folder)).write(refactored);
	}

	private static void writeLevelMetrics(
			String fileName,
			DecomposedTestCases testCases,
			List<ClusteringLevel> levels,
			TestClassRefactorer refactorer) {
		CommaSeparatedValues csv = new CommaSeparatedValues();
		csv.addLine(
				"level",
				"test_classes",
				"test_methods",
				"setup_methods",
				"attributes",
				"total_statements",
				"duplicated_statements",
				"duplication_rate");
		for (ClusteringLevel level : levels) {
			TestClassMetrics metrics = metricsOf(refactorer.refactor(new TestCaseClusters(level.clusters())));
			if (metrics.testMethods() != testCases.testCases().size()) {
				throw new IllegalStateException("Every clustering level must conserve the accepted test corpus.");
			}
			csv.addLine(
					displayLevel(level),
					metrics.testClasses(),
					metrics.testMethods(),
					metrics.setupMethods(),
					metrics.attributes(),
					metrics.totalStatements(),
					metrics.duplicatedStatements(),
					formatRate(metrics.duplicationRate()));
		}
		RESULTS.writeContetAsString(fileName, csv.getContent());
	}

	private static void writeComparison(List<StrategyResult> results) {
		CommaSeparatedValues csv = new CommaSeparatedValues();
		csv.addLine(
				"strategy",
				"best_level",
				"test_classes",
				"test_methods",
				"setup_methods",
				"attributes",
				"total_statements",
				"duplicated_statements",
				"duplication_rate");
		for (StrategyResult result : results) {
			csv.addLine(
					result.name(),
					result.level(),
					result.metrics().testClasses(),
					result.metrics().testMethods(),
					result.metrics().setupMethods(),
					result.metrics().attributes(),
					result.metrics().totalStatements(),
					result.metrics().duplicatedStatements(),
					formatRate(result.metrics().duplicationRate()));
		}
		RESULTS.writeContetAsString("comparison.csv", csv.getContent());
	}

	private static void writeBarChart(String fileName, String title, String yLabel, List<Double> values, boolean percent) {
		int width = 920;
		int height = 520;
		int left = 80;
		int right = 40;
		int top = 56;
		int bottom = 120;
		int plotWidth = width - left - right;
		int plotHeight = height - top - bottom;
		double maxValue = Math.max(percent ? 100.0 : 1.0, values.stream().mapToDouble(Double::doubleValue).max().orElse(1.0));
		if (!percent) {
			maxValue = Math.ceil(maxValue * 1.1);
		}
		int barGap = 28;
		int barWidth = (plotWidth - barGap * (values.size() + 1)) / values.size();
		StringBuilder svg = new StringBuilder();
		svg.append(String.format(Locale.ROOT, "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"%d\" height=\"%d\" viewBox=\"0 0 %d %d\">%n", width, height, width, height));
		svg.append("\t<rect width=\"100%\" height=\"100%\" fill=\"#ffffff\"/>\n");
		svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"32\" text-anchor=\"middle\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"18\" font-weight=\"700\" fill=\"#111827\">%s</text>%n", width / 2, title));
		svg.append(String.format(Locale.ROOT, "\t<text x=\"24\" y=\"%d\" text-anchor=\"middle\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"12\" fill=\"#374151\" transform=\"rotate(-90 24 %d)\">%s</text>%n", top + plotHeight / 2, top + plotHeight / 2, yLabel));
		int ticks = 5;
		for (int tick = 0; tick <= ticks; tick++) {
			double value = maxValue * tick / ticks;
			int y = top + plotHeight - (int) Math.round(plotHeight * tick / (double) ticks);
			svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#e5e7eb\" stroke-width=\"1\"/>%n", left, y, left + plotWidth, y));
			String label = percent ? String.format(Locale.ROOT, "%.0f", value) : String.format(Locale.ROOT, "%.0f", value);
			svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" text-anchor=\"end\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"11\" fill=\"#6b7280\">%s</text>%n", left - 8, y + 4, label));
		}
		svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#111827\" stroke-width=\"1.5\"/>%n", left, top, left, top + plotHeight));
		svg.append(String.format(Locale.ROOT, "\t<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"#111827\" stroke-width=\"1.5\"/>%n", left, top + plotHeight, left + plotWidth, top + plotHeight));
		for (int index = 0; index < values.size(); index++) {
			double value = values.get(index);
			int barHeight = (int) Math.round(plotHeight * (value / maxValue));
			int x = left + barGap + index * (barWidth + barGap);
			int y = top + plotHeight - barHeight;
			svg.append(String.format(Locale.ROOT, "\t<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"%s\"/>%n", x, y, barWidth, barHeight, COLORS.get(index)));
			String valueLabel = percent ? String.format(Locale.ROOT, "%.1f%%", value) : String.format(Locale.ROOT, "%.0f", value);
			svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" text-anchor=\"middle\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"12\" font-weight=\"600\" fill=\"#111827\">%s</text>%n", x + barWidth / 2, y - 8, valueLabel));
			svg.append(String.format(Locale.ROOT, "\t<text x=\"%d\" y=\"%d\" text-anchor=\"end\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"12\" fill=\"#111827\" transform=\"rotate(-32 %d %d)\">%s</text>%n", x + barWidth / 2 + 8, top + plotHeight + 18, x + barWidth / 2 + 8, top + plotHeight + 18, STRATEGIES.get(index)));
		}
		svg.append("</svg>\n");
		RESULTS.writeContetAsString(fileName, svg.toString());
	}

	private static TestClassMetrics metricsOf(RefactoredTestClasses refactored) {
		return TestClassMetricsCalculator.forSetupCode(allClasses(refactored));
	}

	private static List<TestClass> allClasses(RefactoredTestClasses refactored) {
		List<TestClass> classes = new ArrayList<>(refactored.testClasses());
		classes.addAll(refactored.helperClasses());
		return classes;
	}

	private static int displayLevel(ClusteringLevel level) {
		return level.number() + 1;
	}

	private static String formatRate(double rate) {
		return String.format(Locale.ROOT, "%.1f", rate * 100.0);
	}

	private static final class StrategyResult {

		private final String name;
		private final String level;
		private final TestClassMetrics metrics;
		private final RefactoredTestClasses refactored;

		private StrategyResult(String name, String level, TestClassMetrics metrics, RefactoredTestClasses refactored) {
			this.name = name;
			this.level = level;
			this.metrics = metrics;
			this.refactored = refactored;
		}

		private StrategyResult named(String name) {
			return new StrategyResult(name, level, metrics, refactored);
		}

		private String name() {
			return name;
		}

		private String level() {
			return level;
		}

		private TestClassMetrics metrics() {
			return metrics;
		}

		private RefactoredTestClasses refactored() {
			return refactored;
		}
	}
}
