package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.util.List;

import br.ufsc.ine.leb.roza.core.modern.arrangement.ArrangeProjection;
import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;

public final class SetupExtractionPotentialTestCaseSimilarityMeasurer implements TestCaseSimilarityMeasurer {

	@Override
	public TestCaseSimilarityMatrix measure(DecomposedTestCases decomposedTestCases) {
		List<TestCase> testCases = decomposedTestCases.testCases();
		TestCaseSimilarityMatrix matrix = new TestCaseSimilarityMatrix(testCases);
		for (int source = 0; source < testCases.size(); source++) {
			for (int target = 0; target < testCases.size(); target++) {
				matrix.setSimilarity(source, target, measure(testCases.get(source), testCases.get(target)));
			}
		}
		return matrix;
	}

	private double measure(TestCase source, TestCase target) {
		List<String> sourceProjection = ArrangeProjection.normalizedStatements(source);
		List<String> targetProjection = ArrangeProjection.normalizedStatements(target);
		return TextualPrefixSimilarity.commonPrefixSize(sourceProjection, targetProjection);
	}
}
