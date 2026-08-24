package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.util.List;

import br.ufsc.ine.leb.roza.core.modern.StageProgress;
import br.ufsc.ine.leb.roza.core.modern.arrangement.ArrangeProjection;
import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;

public final class SetupExtractionPotentialTestCaseSimilarityMeasurer implements TestCaseSimilarityMeasurer {

	private final Integer maximumMethodSize;

	public SetupExtractionPotentialTestCaseSimilarityMeasurer() {
		this.maximumMethodSize = null;
	}

	public SetupExtractionPotentialTestCaseSimilarityMeasurer(int maximumMethodSize) {
		if (maximumMethodSize < 1) {
			throw new IllegalArgumentException("SEP maximum method size must be at least 1.");
		}
		this.maximumMethodSize = maximumMethodSize;
	}

	@Override
	public TestCaseSimilarityMatrix measure(DecomposedTestCases decomposedTestCases) {
		return measure(decomposedTestCases, StageProgress.ignore());
	}

	@Override
	public TestCaseSimilarityMatrix measure(DecomposedTestCases decomposedTestCases, StageProgress progress) {
		StageProgress reporter = progress == null ? StageProgress.ignore() : progress;
		List<TestCase> testCases = decomposedTestCases.testCases();
		int denominator = denominator(testCases);
		TestCaseSimilarityMatrix matrix = new TestCaseSimilarityMatrix(testCases);
		int n = testCases.size();
		for (int source = 0; source < n; source++) {
			for (int target = 0; target < n; target++) {
				if (source != target) {
					matrix.setSimilarity(source, target, measure(testCases.get(source), testCases.get(target), denominator));
				}
			}
			MeasurementProgress.afterDirectedRow(reporter, source, n);
		}
		if (n == 0) {
			reporter.report(1, 1);
		}
		return matrix;
	}

	private int denominator(List<TestCase> testCases) {
		if (maximumMethodSize != null) {
			return maximumMethodSize;
		}
		int largest = 0;
		for (TestCase testCase : testCases) {
			largest = Math.max(largest, ArrangeProjection.normalizedStatements(testCase).size());
		}
		return largest;
	}

	private double measure(TestCase source, TestCase target, int denominator) {
		if (denominator == 0) {
			return 0.0;
		}
		int commonPrefixSize = TextualPrefixSimilarity.commonPrefixSize(
				ArrangeProjection.normalizedStatements(source),
				ArrangeProjection.normalizedStatements(target));
		return Math.min(1.0, commonPrefixSize / (double) denominator);
	}
}
