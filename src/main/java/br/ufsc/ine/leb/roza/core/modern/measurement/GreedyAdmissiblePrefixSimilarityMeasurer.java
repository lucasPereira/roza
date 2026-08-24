package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.util.List;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.StageProgress;
import br.ufsc.ine.leb.roza.core.modern.arrangement.AdmissiblePrefixMatcher;
import br.ufsc.ine.leb.roza.core.modern.arrangement.ArrangeDependencyGraphCache;
import br.ufsc.ine.leb.roza.core.modern.arrangement.ArrangeProjection;
import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;

public final class GreedyAdmissiblePrefixSimilarityMeasurer implements TestCaseSimilarityMeasurer {

	@Override
	public TestCaseSimilarityMatrix measure(DecomposedTestCases decomposedTestCases) {
		return measure(decomposedTestCases, StageProgress.ignore());
	}

	@Override
	public TestCaseSimilarityMatrix measure(DecomposedTestCases decomposedTestCases, StageProgress progress) {
		StageProgress reporter = progress == null ? StageProgress.ignore() : progress;
		List<TestCase> testCases = decomposedTestCases.testCases();
		List<List<String>> projections = testCases.stream()
				.map(ArrangeProjection::normalizedStatements)
				.collect(Collectors.toList());
		ArrangeDependencyGraphCache graphCache = new ArrangeDependencyGraphCache();
		TestCaseSimilarityMatrix matrix = new TestCaseSimilarityMatrix(testCases);
		int n = testCases.size();
		for (int source = 0; source < n; source++) {
			for (int target = source + 1; target < n; target++) {
				int prefixSize = Math.max(
						AdmissiblePrefixMatcher.greedyPrefixSize(
								testCases.get(source),
								projections.get(target),
								graphCache),
						AdmissiblePrefixMatcher.greedyPrefixSize(
								testCases.get(target),
								projections.get(source),
								graphCache));
				double score = DiceMatchSimilarity.score(
						prefixSize,
						projections.get(source).size(),
						projections.get(target).size());
				matrix.setSimilarity(source, target, score);
				matrix.setSimilarity(target, source, score);
			}
			MeasurementProgress.afterUpperTriangleRow(reporter, source, n);
		}
		if (n <= 1) {
			reporter.report(1, 1);
		}
		return matrix;
	}
}
