package br.ufsc.ine.leb.roza.expt.g;

import br.ufsc.ine.leb.roza.core.Cluster;
import br.ufsc.ine.leb.roza.core.SimilarityReport;
import br.ufsc.ine.leb.roza.core.SimilarityReportBuilder;
import br.ufsc.ine.leb.roza.core.TestCase;
import br.ufsc.ine.leb.roza.core.TestClass;
import br.ufsc.ine.leb.roza.core.TextFile;
import br.ufsc.ine.leb.roza.core.clustering.AnyClusterReferee;
import br.ufsc.ine.leb.roza.core.clustering.AgglomerativeHierarchicalClusteringTestCaseClusterer;
import br.ufsc.ine.leb.roza.core.clustering.AverageLinkage;
import br.ufsc.ine.leb.roza.core.clustering.BiggestClusterReferee;
import br.ufsc.ine.leb.roza.core.clustering.ClusteringStatistics;
import br.ufsc.ine.leb.roza.core.clustering.CompleteLinkage;
import br.ufsc.ine.leb.roza.core.clustering.Combination;
import br.ufsc.ine.leb.roza.core.clustering.ComposedReferee;
import br.ufsc.ine.leb.roza.core.clustering.Level;
import br.ufsc.ine.leb.roza.core.clustering.Linkage;
import br.ufsc.ine.leb.roza.core.clustering.Referee;
import br.ufsc.ine.leb.roza.core.clustering.SingleLinkage;
import br.ufsc.ine.leb.roza.core.clustering.ThresholdCriterion;
import br.ufsc.ine.leb.roza.core.clustering.WinnerCombination;
import br.ufsc.ine.leb.roza.core.extraction.Junit5TestCaseExtractor;
import br.ufsc.ine.leb.roza.core.extraction.TestCaseExtractor;
import br.ufsc.ine.leb.roza.core.loading.RecursiveTextFileLoader;
import br.ufsc.ine.leb.roza.core.loading.TextFileLoader;
import br.ufsc.ine.leb.roza.core.parsing.Junit5TestClassParser;
import br.ufsc.ine.leb.roza.core.parsing.TestClassParser;
import br.ufsc.ine.leb.roza.core.selection.JavaExtensionTextFileSelector;
import br.ufsc.ine.leb.roza.core.selection.TextFileSelector;
import br.ufsc.ine.leb.roza.core.utils.CommaSeparatedValues;
import br.ufsc.ine.leb.roza.core.utils.FolderUtils;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Experiment {

	private static final String SUBJECT = "Apache Commons Lang";
	private static final String SUBJECT_TESTS = "src/expt/resources/g";
	private static final String RESULTS = "experiment-results/g";
	private static final int[] SIZES = {50, 100, 150, 200};

	public static void main(String[] args) {
		new FolderUtils(RESULTS).createEmptyFolder();
		List<TestCase> tests = loadSubjectTests();
		List<BenchmarkRow> baseline = run(Mode.BASELINE, tests);
		List<BenchmarkRow> optimized = run(Mode.OPTIMIZED, tests);
		write("baseline.csv", toCsv(baseline));
		write("optimized.csv", toCsv(optimized));
		write("comparison.csv", toComparisonCsv(baseline, optimized));
	}

	private static List<BenchmarkRow> run(Mode mode, List<TestCase> tests) {
		List<BenchmarkRow> rows = new ArrayList<>();
		for (int size : SIZES) {
			if (tests.size() < size) {
				System.out.printf("Skipping %d tests because only %d were extracted.%n", size, tests.size());
				continue;
			}
			List<TestCase> subset = tests.subList(0, size);
			SimilarityReport report = createSyntheticReport(subset);
			rows.add(run(mode, report, subset, "Single linkage", new SingleLinkage(report)));
			rows.add(run(mode, report, subset, "Complete linkage", new CompleteLinkage(report)));
			rows.add(run(mode, report, subset, "Average linkage", new AverageLinkage(report)));
		}
		return rows;
	}

	private static String toCsv(List<BenchmarkRow> rows) {
		CommaSeparatedValues csv = new CommaSeparatedValues();
		csv.addLine("Algorithm", "Subject", "Tests", "Linkage", "Status", "Levels", "Final Clusters", "Runtime Millis", "Generated Candidates", "Linkage Evaluations", "Peak Used Memory Bytes");
		for (BenchmarkRow row : rows) {
			csv.addLine(row.algorithm, SUBJECT, row.tests, row.linkage, row.status, row.levels, row.finalClusters, row.runtimeMillis, row.generatedCandidates, row.linkageEvaluations, row.peakUsedMemoryBytes);
		}
		return csv.getContent();
	}

	private static String toComparisonCsv(List<BenchmarkRow> baseline, List<BenchmarkRow> optimized) {
		CommaSeparatedValues csv = new CommaSeparatedValues();
		csv.addLine("Tests", "Linkage", "Baseline Runtime Millis", "Optimized Runtime Millis", "Runtime Speedup", "Baseline Candidates", "Optimized Candidates", "Candidate Reduction");
		for (BenchmarkRow optimizedRow : optimized) {
			if (!optimizedRow.isCompleted()) {
				continue;
			}
			for (BenchmarkRow baselineRow : baseline) {
				if (baselineRow.matches(optimizedRow) && baselineRow.isCompleted()) {
					BigDecimal runtimeSpeedup = divide(baselineRow.runtimeMillis, optimizedRow.runtimeMillis);
					BigDecimal candidateReduction = divide(baselineRow.generatedCandidates, optimizedRow.generatedCandidates);
					csv.addLine(optimizedRow.tests, optimizedRow.linkage, baselineRow.runtimeMillis, optimizedRow.runtimeMillis, runtimeSpeedup, baselineRow.generatedCandidates, optimizedRow.generatedCandidates, candidateReduction);
				}
			}
		}
		return csv.getContent();
	}

	private static BigDecimal divide(long numerator, long denominator) {
		return BigDecimal.valueOf(numerator).divide(BigDecimal.valueOf(denominator), 2, RoundingMode.HALF_UP);
	}

	private static void write(String fileName, String content) {
		File file = new File(RESULTS, fileName);
		if (file.exists() && !file.delete()) {
			throw new IllegalStateException(String.format("Could not replace %s", file));
		}
		new FolderUtils(RESULTS).writeContetAsString(fileName, content);
	}

	private static List<TestCase> loadSubjectTests() {
		TextFileLoader loader = new RecursiveTextFileLoader(SUBJECT_TESTS);
		TextFileSelector selector = new JavaExtensionTextFileSelector();
		TestClassParser parser = new Junit5TestClassParser();
		TestCaseExtractor extractor = new Junit5TestCaseExtractor();
		List<TextFile> selected = selector.select(loader.load());
		List<TestClass> classes = new ArrayList<>();
		int skipped = 0;
		for (TextFile file : selected) {
			try {
				classes.addAll(parser.parse(List.of(file)));
			} catch (RuntimeException exception) {
				skipped++;
			}
		}
		List<TestCase> tests = extractor.extract(classes);
		System.out.printf("Extracted %d tests from %d classes (%d files skipped).%n", tests.size(), classes.size(), skipped);
		return tests;
	}

	private static SimilarityReport createSyntheticReport(List<TestCase> tests) {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(true);
		int size = tests.size();
		BigDecimal denominator = BigDecimal.valueOf((long) size * size * size + (long) size * size);
		for (int sourceIndex = 0; sourceIndex < size; sourceIndex++) {
			for (int targetIndex = sourceIndex + 1; targetIndex < size; targetIndex++) {
				int distance = targetIndex - sourceIndex;
				long weightedDistance = (long) (size - distance) * size * size;
				long disambiguation = (long) (size - sourceIndex) * size + (size - targetIndex);
				BigDecimal score = BigDecimal.valueOf(weightedDistance + disambiguation).divide(denominator, 12, RoundingMode.HALF_UP);
				builder.add(tests.get(sourceIndex), tests.get(targetIndex), score);
			}
		}
		return builder.build();
	}

	private static BenchmarkRow run(Mode mode, SimilarityReport report, List<TestCase> tests, String linkageName, Linkage linkage) {
		int size = tests.size();
		if (mode == Mode.BASELINE && shouldSkipBaseline(size, linkageName)) {
			return BenchmarkRow.skipped(mode.label, size, linkageName, "skipped: baseline runtime exceeds practical limit");
		}
		Referee referee = new ComposedReferee(new BiggestClusterReferee(), new AnyClusterReferee());
		ThresholdCriterion criterion = (level, combination, evaluation) -> false;
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		long beforeMemory = usedMemory(runtime);
		long started = System.nanoTime();
		BenchmarkResult result = mode == Mode.BASELINE ? runBaseline(tests, linkage, referee, criterion) : runOptimized(report, linkage, referee, criterion);
		long elapsed = System.nanoTime() - started;
		long afterMemory = usedMemory(runtime);
		long peakMemory = Math.max(beforeMemory, afterMemory);
		System.out.printf("%s %s %d tests with %s: %d ms, %d generated candidates.%n", mode.label, SUBJECT, size, linkageName, elapsed / 1_000_000, result.generatedCandidates);
		return BenchmarkRow.completed(mode.label, size, linkageName, result.levels.size(), result.levels.get(result.levels.size() - 1).getClusters().size(), elapsed / 1_000_000, result.generatedCandidates, result.linkageEvaluations, peakMemory);
	}

	private static boolean shouldSkipBaseline(int size, String linkageName) {
		return size > 150 || (size > 100 && linkageName.equals("Average linkage"));
	}

	private static BenchmarkResult runBaseline(List<TestCase> tests, Linkage linkage, Referee referee, ThresholdCriterion criterion) {
		LegacyInstrumentedClusterer clusterer = new LegacyInstrumentedClusterer(linkage, referee, criterion);
		List<Level> levels = clusterer.generateLevels(createInitialClusters(tests));
		ClusterStats stats = clusterer.getStats();
		return new BenchmarkResult(levels, stats.generatedCandidates, stats.linkageEvaluations);
	}

	private static BenchmarkResult runOptimized(SimilarityReport report, Linkage linkage, Referee referee, ThresholdCriterion criterion) {
		ClusteringStatistics statistics = new ClusteringStatistics();
		AgglomerativeHierarchicalClusteringTestCaseClusterer clusterer = new AgglomerativeHierarchicalClusteringTestCaseClusterer(linkage, referee, criterion, statistics);
		List<Level> levels = clusterer.generateLevels(report);
		return new BenchmarkResult(levels, statistics.getGeneratedCandidates(), statistics.getLinkageEvaluations());
	}

	private static Set<Cluster> createInitialClusters(List<TestCase> tests) {
		Set<Cluster> clusters = new LinkedHashSet<>();
		for (TestCase test : tests) {
			clusters.add(new Cluster(test));
		}
		return clusters;
	}

	private static long usedMemory(Runtime runtime) {
		return runtime.totalMemory() - runtime.freeMemory();
	}

	private static class LegacyInstrumentedClusterer {

		private final Linkage linkage;
		private final Referee referee;
		private final ThresholdCriterion criterion;
		private final ClusterStats stats;

		private LegacyInstrumentedClusterer(Linkage linkage, Referee referee, ThresholdCriterion criterion) {
			this.linkage = linkage;
			this.referee = referee;
			this.criterion = criterion;
			this.stats = new ClusterStats();
		}

		private List<Level> generateLevels(Set<Cluster> initialClusters) {
			Set<Cluster> currentClusters = initialClusters;
			List<Level> levels = new ArrayList<>();
			Level currentLevel = new Level(currentClusters);
			levels.add(currentLevel);
			boolean shouldContinue = currentClusters.size() > 1;
			while (shouldContinue) {
				WinnerCombination winner = join(currentClusters);
				Combination combination = winner.getCombination();
				BigDecimal evaluation = winner.getEvaluation();
				Cluster first = combination.getFirst();
				Cluster second = combination.getSecond();
				Cluster merged = first.merge(second);
				Set<Cluster> nextClusters = currentClusters.stream().filter(cluster -> !cluster.equals(first) && !cluster.equals(second)).collect(Collectors.toCollection(LinkedHashSet::new));
				nextClusters.add(merged);
				Level nextLevel = new Level(currentLevel, nextClusters, evaluation);
				Boolean thresholdReached = criterion.shouldStop(nextLevel.getStep(), combination, evaluation);
				boolean hasClustersToMerge = nextClusters.size() > 1;
				shouldContinue = !thresholdReached && hasClustersToMerge;
				if (!thresholdReached) {
					levels.add(nextLevel);
				}
				if (shouldContinue) {
					currentLevel = nextLevel;
					currentClusters = nextClusters;
				}
			}
			return levels;
		}

		private WinnerCombination join(Set<Cluster> clusters) {
			Set<Combination> chosen = new HashSet<>();
			BigDecimal nearestDistance = BigDecimal.ZERO;
			Set<Combination> combinations = getCombinations(clusters);
			stats.generatedCandidates += combinations.size();
			for (Combination combination : combinations) {
				Cluster first = combination.getFirst();
				Cluster second = combination.getSecond();
				BigDecimal distance = linkage.evaluate(first, second);
				stats.linkageEvaluations++;
				if (distance.compareTo(nearestDistance) == 0) {
					chosen.add(combination);
				} else if (distance.compareTo(nearestDistance) > 0) {
					chosen.clear();
					chosen.add(combination);
					nearestDistance = distance;
				}
			}
			Combination winner = referee.untie(chosen);
			return new WinnerCombination(winner, nearestDistance);
		}

		private Set<Combination> getCombinations(Set<Cluster> clusters) {
			Map<Combination, Combination> combinations = new LinkedHashMap<>();
			for (Cluster first : clusters) {
				for (Cluster second : clusters) {
					if (first != second) {
						Combination combination = new Combination(first, second);
						combinations.putIfAbsent(combination, combination);
					}
				}
			}
			return new LinkedHashSet<>(combinations.values());
		}

		private ClusterStats getStats() {
			return stats;
		}
	}

	private static class ClusterStats {

		private long generatedCandidates;
		private long linkageEvaluations;
	}

	private static class BenchmarkResult {

		private final List<Level> levels;
		private final long generatedCandidates;
		private final long linkageEvaluations;

		private BenchmarkResult(List<Level> levels, long generatedCandidates, long linkageEvaluations) {
			this.levels = levels;
			this.generatedCandidates = generatedCandidates;
			this.linkageEvaluations = linkageEvaluations;
		}
	}

	private static class BenchmarkRow {

		private final String algorithm;
		private final int tests;
		private final String linkage;
		private final String status;
		private final String levels;
		private final String finalClusters;
		private final long runtimeMillis;
		private final long generatedCandidates;
		private final long linkageEvaluations;
		private final long peakUsedMemoryBytes;

		private BenchmarkRow(String algorithm, int tests, String linkage, String status, String levels, String finalClusters, long runtimeMillis, long generatedCandidates, long linkageEvaluations, long peakUsedMemoryBytes) {
			this.algorithm = algorithm;
			this.tests = tests;
			this.linkage = linkage;
			this.status = status;
			this.levels = levels;
			this.finalClusters = finalClusters;
			this.runtimeMillis = runtimeMillis;
			this.generatedCandidates = generatedCandidates;
			this.linkageEvaluations = linkageEvaluations;
			this.peakUsedMemoryBytes = peakUsedMemoryBytes;
		}

		private static BenchmarkRow completed(String algorithm, int tests, String linkage, int levels, int finalClusters, long runtimeMillis, long generatedCandidates, long linkageEvaluations, long peakUsedMemoryBytes) {
			return new BenchmarkRow(algorithm, tests, linkage, "completed", String.valueOf(levels), String.valueOf(finalClusters), runtimeMillis, generatedCandidates, linkageEvaluations, peakUsedMemoryBytes);
		}

		private static BenchmarkRow skipped(String algorithm, int tests, String linkage, String status) {
			return new BenchmarkRow(algorithm, tests, linkage, status, "", "", 0, 0, 0, 0);
		}

		private boolean isCompleted() {
			return status.equals("completed");
		}

		private boolean matches(BenchmarkRow other) {
			return tests == other.tests && linkage.equals(other.linkage);
		}
	}

	private enum Mode {
		BASELINE("Baseline", "baseline.csv"),
		OPTIMIZED("Optimized", "optimized.csv");

		private final String label;
		private final String fileName;

		Mode(String label, String fileName) {
			this.label = label;
			this.fileName = fileName;
		}
	}
}
