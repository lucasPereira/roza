package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.util.List;

import br.ufsc.ine.leb.roza.core.modern.StageProgress;
import br.ufsc.ine.leb.roza.core.modern.arrangement.ArrangeProjection;
import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;

public final class LcsTestCaseSimilarityMeasurer implements TestCaseSimilarityMeasurer {

	@Override
	public TestCaseSimilarityMatrix measure(DecomposedTestCases decomposedTestCases) {
		return measure(decomposedTestCases, StageProgress.ignore());
	}

	@Override
	public TestCaseSimilarityMatrix measure(DecomposedTestCases decomposedTestCases, StageProgress progress) {
		StageProgress reporter = progress == null ? StageProgress.ignore() : progress;
		List<TestCase> testCases = decomposedTestCases.testCases();
		TestCaseSimilarityMatrix matrix = new TestCaseSimilarityMatrix(testCases);
		int n = testCases.size();
		for (int source = 0; source < n; source++) {
			for (int target = 0; target < n; target++) {
				if (source != target) {
					matrix.setSimilarity(source, target, measure(testCases.get(source), testCases.get(target)));
				}
			}
			MeasurementProgress.afterDirectedRow(reporter, source, n);
		}
		if (n == 0) {
			reporter.report(1, 1);
		}
		return matrix;
	}

	private double measure(TestCase source, TestCase target) {
		List<String> sourceProjection = ArrangeProjection.normalizedStatements(source);
		List<String> targetProjection = ArrangeProjection.normalizedStatements(target);
		int commonSubsequenceSize = commonSubsequenceSize(sourceProjection, targetProjection);
		return DiceMatchSimilarity.score(commonSubsequenceSize, sourceProjection.size(), targetProjection.size());
	}

	private int commonSubsequenceSize(List<String> source, List<String> target) {
		int[][] matrix = new int[source.size() + 1][target.size() + 1];
		for (int sourceIndex = 1; sourceIndex <= source.size(); sourceIndex++) {
			for (int targetIndex = 1; targetIndex <= target.size(); targetIndex++) {
				if (source.get(sourceIndex - 1).equals(target.get(targetIndex - 1))) {
					matrix[sourceIndex][targetIndex] = matrix[sourceIndex - 1][targetIndex - 1] + 1;
				} else {
					matrix[sourceIndex][targetIndex] = Math.max(matrix[sourceIndex - 1][targetIndex], matrix[sourceIndex][targetIndex - 1]);
				}
			}
		}
		return matrix[source.size()][target.size()];
	}
}
