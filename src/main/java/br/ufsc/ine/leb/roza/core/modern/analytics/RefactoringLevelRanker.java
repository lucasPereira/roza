package br.ufsc.ine.leb.roza.core.modern.analytics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import br.ufsc.ine.leb.roza.core.modern.StageProgress;
import br.ufsc.ine.leb.roza.core.modern.clustering.ClusteringLevel;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseCluster;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseClusters;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.refactoring.RankingSetupContributor;
import br.ufsc.ine.leb.roza.core.modern.refactoring.RefactoredTestClasses;
import br.ufsc.ine.leb.roza.core.modern.refactoring.TestClassRefactorer;

public final class RefactoringLevelRanker {

	private RefactoringLevelRanker() {
	}

	public static List<Integer> topLevelIndices(List<ClusteringLevel> levels, TestClassRefactorer refactorer, int limit) {
		return topLevelIndices(levels, refactorer, limit, StageProgress.ignore());
	}

	public static List<Integer> topLevelIndices(
			List<ClusteringLevel> levels,
			TestClassRefactorer refactorer,
			int limit,
			StageProgress progress) {
		Objects.requireNonNull(levels);
		Objects.requireNonNull(refactorer);
		StageProgress reporter = progress == null ? StageProgress.ignore() : progress;
		if (limit < 1) {
			throw new IllegalArgumentException("Refactoring level ranking limit must be at least 1.");
		}
		if (refactorer instanceof RankingSetupContributor) {
			return topLevelIndicesIncrementally(levels, (RankingSetupContributor) refactorer, limit, reporter);
		}
		return topLevelIndicesByFullRefactor(levels, refactorer, limit, reporter);
	}

	private static List<Integer> topLevelIndicesIncrementally(
			List<ClusteringLevel> levels,
			RankingSetupContributor contributor,
			int limit,
			StageProgress reporter) {
		int total = Math.max(1, levels.size());
		reporter.report(0, total);
		if (levels.isEmpty()) {
			return List.of();
		}
		List<TestCase> tests = new ArrayList<>();
		for (TestCaseCluster cluster : levels.get(0).clusters()) {
			tests.addAll(cluster.testCases());
		}
		IncrementalSetupRanking ranking = new IncrementalSetupRanking(contributor);
		ranking.addShared(tests);
		List<int[]> rankedLevels = new ArrayList<>();
		Set<List<Integer>> previousIndexes = Set.of();
		for (int levelIndex = 0; levelIndex < levels.size(); levelIndex++) {
			Map<List<Integer>, TestCaseCluster> current = new LinkedHashMap<>();
			for (TestCaseCluster cluster : levels.get(levelIndex).clusters()) {
				current.put(cluster.testCaseIndexes(), cluster);
			}
			for (List<Integer> indexes : previousIndexes) {
				if (!current.containsKey(indexes)) {
					ranking.remove(indexes);
				}
			}
			for (Map.Entry<List<Integer>, TestCaseCluster> entry : current.entrySet()) {
				if (!previousIndexes.contains(entry.getKey())) {
					ranking.add(entry.getValue());
				}
			}
			rankedLevels.add(new int[] { levelIndex, ranking.duplicatedStatements() });
			reporter.report(levelIndex + 1, total);
			previousIndexes = Set.copyOf(current.keySet());
		}
		return topIndices(rankedLevels, limit);
	}

	private static List<Integer> topLevelIndicesByFullRefactor(
			List<ClusteringLevel> levels,
			TestClassRefactorer refactorer,
			int limit,
			StageProgress reporter) {
		int total = Math.max(1, levels.size());
		reporter.report(0, total);
		List<int[]> rankedLevels = new ArrayList<>();
		for (int levelIndex = 0; levelIndex < levels.size(); levelIndex++) {
			rankedLevels.add(new int[] { levelIndex, duplicatedStatements(refactorer, levels.get(levelIndex)) });
			reporter.report(levelIndex + 1, total);
		}
		return topIndices(rankedLevels, limit);
	}

	static int duplicatedStatements(TestClassRefactorer refactorer, ClusteringLevel level) {
		RefactoredTestClasses refactored = refactorer.refactor(new TestCaseClusters(level.clusters()));
		List<TestClass> classes = new ArrayList<>(refactored.testClasses());
		classes.addAll(refactored.helperClasses());
		return TestClassMetricsCalculator.forSetupCode(classes).duplicatedStatements();
	}

	private static List<Integer> topIndices(List<int[]> rankedLevels, int limit) {
		rankedLevels.sort(Comparator
				.comparingInt((int[] entry) -> entry[1])
				.thenComparingInt(entry -> entry[0]));
		int resultSize = Math.min(limit, rankedLevels.size());
		List<Integer> topLevelIndices = new ArrayList<>(resultSize);
		for (int index = 0; index < resultSize; index++) {
			topLevelIndices.add(rankedLevels.get(index)[0]);
		}
		return topLevelIndices;
	}

	private static final class IncrementalSetupRanking {

		private final RankingSetupContributor contributor;
		private final boolean residual;
		private final SetupStatementTally tally = new SetupStatementTally();
		private final Map<List<Integer>, List<String>> clusterStatements = new HashMap<>();
		private final Map<List<Integer>, TestClass> singletonSources = new HashMap<>();
		private final Map<String, Integer> leftoverCount = new HashMap<>();
		private final Map<String, List<String>> residualSetup = new HashMap<>();

		private IncrementalSetupRanking(RankingSetupContributor contributor) {
			this.contributor = contributor;
			this.residual = contributor.countsResidualSourceSetupWhileSingletonsRemain();
		}

		private void addShared(List<TestCase> tests) {
			tally.add(SetupCodeProjection.statements(contributor.sharedRankingClasses(tests)));
		}

		private void add(TestCaseCluster cluster) {
			List<String> statements = SetupCodeProjection.statements(contributor.clusterRankingClasses(cluster));
			clusterStatements.put(cluster.testCaseIndexes(), statements);
			tally.add(statements);
			if (residual && cluster.size() == 1) {
				TestClass source = cluster.testCases().get(0).sourceTestClass().orElseThrow(
						() -> new IllegalStateException("Residual ranking requires a source class for leftover tests."));
				singletonSources.put(cluster.testCaseIndexes(), source);
				int count = leftoverCount.merge(source.qualifiedName(), 1, Integer::sum);
				if (count == 1) {
					List<String> setup = residualSetup.computeIfAbsent(
							source.qualifiedName(),
							key -> SetupCodeProjection.statements(List.of(contributor.residualSourceSetupClass(source))));
					tally.add(setup);
				}
			}
		}

		private void remove(List<Integer> indexes) {
			tally.remove(clusterStatements.remove(indexes));
			if (!residual) {
				return;
			}
			TestClass source = singletonSources.remove(indexes);
			if (source == null) {
				return;
			}
			int count = leftoverCount.merge(source.qualifiedName(), -1, Integer::sum);
			if (count == 0) {
				leftoverCount.remove(source.qualifiedName());
				tally.remove(residualSetup.get(source.qualifiedName()));
			}
		}

		private int duplicatedStatements() {
			return tally.duplicatedStatements();
		}
	}
}
