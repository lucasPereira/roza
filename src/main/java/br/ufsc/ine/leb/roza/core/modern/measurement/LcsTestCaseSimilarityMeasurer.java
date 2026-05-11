package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.util.List;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

public final class LcsTestCaseSimilarityMeasurer implements TestCaseSimilarityMeasurer {

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
		int commonSubsequenceSize = commonSubsequenceSize(sourceProjection, targetProjection);
		int totalProjectionSize = sourceProjection.size() + targetProjection.size();
		if (totalProjectionSize == 0) {
			return 0.0;
		}
		return (2.0 * commonSubsequenceSize) / totalProjectionSize;
	}

	private List<String> projection(TestCase testCase) {
		return testCase.body()
				.statements()
				.stream()
				.takeWhile(statement -> !statement.isAssertion())
				.map(CodeStatement::normalizedText)
				.collect(Collectors.toList());
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
