package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.util.List;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

public final class LccssTestCaseSimilarityMeasurer implements TestCaseSimilarityMeasurer {

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
		List<String> sourceProjection = projection(source);
		List<String> targetProjection = projection(target);
		int commonPrefixSize = commonPrefixSize(sourceProjection, targetProjection);
		int totalProjectionSize = sourceProjection.size() + targetProjection.size();
		if (totalProjectionSize == 0) {
			return 0.0;
		}
		return (2.0 * commonPrefixSize) / totalProjectionSize;
	}

	private List<String> projection(TestCase testCase) {
		return testCase.body()
				.statements()
				.stream()
				.takeWhile(statement -> !statement.isAssertion())
				.map(CodeStatement::normalizedText)
				.collect(Collectors.toList());
	}

	private int commonPrefixSize(List<String> source, List<String> target) {
		int commonPrefixSize = 0;
		while (commonPrefixSize < source.size() && commonPrefixSize < target.size() && source.get(commonPrefixSize).equals(target.get(commonPrefixSize))) {
			commonPrefixSize++;
		}
		return commonPrefixSize;
	}
}
