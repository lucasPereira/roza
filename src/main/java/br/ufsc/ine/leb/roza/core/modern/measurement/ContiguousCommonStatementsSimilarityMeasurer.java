package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.util.List;

import br.ufsc.ine.leb.roza.core.modern.arrangement.ArrangeProjection;
import br.ufsc.ine.leb.roza.core.modern.arrangement.ExtractableArrangeRun;
import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;

public final class ContiguousCommonStatementsSimilarityMeasurer implements TestCaseSimilarityMeasurer {

	private final int minimumLength;

	public ContiguousCommonStatementsSimilarityMeasurer() {
		this(1);
	}

	public ContiguousCommonStatementsSimilarityMeasurer(int minimumLength) {
		if (minimumLength < 1) {
			throw new IllegalArgumentException("CCS minimum length must be at least 1.");
		}
		this.minimumLength = minimumLength;
	}

	@Override
	public TestCaseSimilarityMatrix measure(DecomposedTestCases decomposedTestCases) {
		List<TestCase> testCases = decomposedTestCases.testCases();
		TestCaseSimilarityMatrix matrix = new TestCaseSimilarityMatrix(testCases);
		for (int source = 0; source < testCases.size(); source++) {
			for (int target = 0; target < testCases.size(); target++) {
				if (source != target) {
					matrix.setSimilarity(source, target, measure(testCases.get(source), testCases.get(target)));
				}
			}
		}
		return matrix;
	}

	private double measure(TestCase source, TestCase target) {
		int matchSize = ExtractableArrangeRun.longestPairwiseLength(source, target, minimumLength);
		return DiceMatchSimilarity.score(
				matchSize,
				ArrangeProjection.normalizedStatements(source).size(),
				ArrangeProjection.normalizedStatements(target).size());
	}
}
