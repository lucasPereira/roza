package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.util.List;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.arrangement.AdmissiblePrefixMatcher;
import br.ufsc.ine.leb.roza.core.modern.arrangement.ArrangeDependencyGraphCache;
import br.ufsc.ine.leb.roza.core.modern.arrangement.ArrangeProjection;
import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;

public final class GreedyAdmissiblePrefixSimilarityMeasurer implements TestCaseSimilarityMeasurer {

	@Override
	public TestCaseSimilarityMatrix measure(DecomposedTestCases decomposedTestCases) {
		List<TestCase> testCases = decomposedTestCases.testCases();
		List<List<String>> projections = testCases.stream()
				.map(ArrangeProjection::normalizedStatements)
				.collect(Collectors.toList());
		ArrangeDependencyGraphCache graphCache = new ArrangeDependencyGraphCache();
		TestCaseSimilarityMatrix matrix = new TestCaseSimilarityMatrix(testCases);
		for (int source = 0; source < testCases.size(); source++) {
			for (int target = source + 1; target < testCases.size(); target++) {
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
		}
		return matrix;
	}
}
