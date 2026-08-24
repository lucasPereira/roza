package br.ufsc.ine.leb.roza.core.modern.analytics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import br.ufsc.ine.leb.roza.core.modern.clustering.ClusteringLevel;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseClusters;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.refactoring.RefactoredTestClasses;
import br.ufsc.ine.leb.roza.core.modern.refactoring.TestClassRefactorer;

public final class RefactoringLevelRanker {

	private RefactoringLevelRanker() {
	}

	public static List<Integer> topLevelIndices(List<ClusteringLevel> levels, TestClassRefactorer refactorer, int limit) {
		Objects.requireNonNull(levels);
		Objects.requireNonNull(refactorer);
		if (limit < 1) {
			throw new IllegalArgumentException("Refactoring level ranking limit must be at least 1.");
		}
		List<int[]> rankedLevels = new ArrayList<>();
		for (int levelIndex = 0; levelIndex < levels.size(); levelIndex++) {
			RefactoredTestClasses refactored = refactorer.refactor(new TestCaseClusters(levels.get(levelIndex).clusters()));
			List<TestClass> classes = new ArrayList<>(refactored.testClasses());
			classes.addAll(refactored.helperClasses());
			rankedLevels.add(new int[] { levelIndex, TestClassMetricsCalculator.forSetupCode(classes).duplicatedStatements() });
		}
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
}
