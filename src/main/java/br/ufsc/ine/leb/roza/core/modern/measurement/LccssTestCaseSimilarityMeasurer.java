package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.util.List;

import br.ufsc.ine.leb.roza.core.modern.StageProgress;
import br.ufsc.ine.leb.roza.core.modern.arrangement.ArrangeProjection;
import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;

public final class LccssTestCaseSimilarityMeasurer implements TestCaseSimilarityMeasurer {

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
		int commonPrefixSize = TextualPrefixSimilarity.commonPrefixSize(sourceProjection, targetProjection);
		return DiceMatchSimilarity.score(commonPrefixSize, sourceProjection.size(), targetProjection.size());
	}
}
