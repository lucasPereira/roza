package br.ufsc.ine.leb.roza.ui.modern;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMatrix;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;

class RankedSimilarityPairsTest {

	@Test
	void shouldOmitSelfPairsAndRankHighestFirst() {
		TestCaseSimilarityMatrix matrix = matrix();

		List<String> labels = labels(RankedSimilarityPairs.top(matrix, true, 10));

		assertEquals(List.of("alpha->beta:0.90", "beta->alpha:0.80", "alpha->gamma:0.50", "gamma->alpha:0.40", "gamma->beta:0.20", "beta->gamma:0.10"), labels);
	}

	@Test
	void shouldKeepOnlyTheBestPairsUpToTheLimit() {
		TestCaseSimilarityMatrix matrix = matrix();

		List<String> highest = labels(RankedSimilarityPairs.top(matrix, true, 2));
		List<String> lowest = labels(RankedSimilarityPairs.top(matrix, false, 2));

		assertEquals(List.of("alpha->beta:0.90", "beta->alpha:0.80"), highest);
		assertEquals(List.of("beta->gamma:0.10", "gamma->beta:0.20"), lowest);
	}

	@Test
	void shouldReturnNoPairsWhenTheMatrixHasFewerThanTwoTests() {
		TestCaseSimilarityMatrix matrix = new TestCaseSimilarityMatrix(List.of(testCase("alpha")));

		assertEquals(List.of(), RankedSimilarityPairs.top(matrix, true));
	}

	private List<String> labels(List<RankedSimilarityPairs.Item> items) {
		return items.stream()
				.map(item -> item.sourceTestCase().name() + "->" + item.targetTestCase().name() + ":" + String.format(Locale.ROOT, "%.2f", item.similarity()))
				.collect(Collectors.toList());
	}

	private TestCaseSimilarityMatrix matrix() {
		TestCaseSimilarityMatrix matrix = new TestCaseSimilarityMatrix(List.of(testCase("alpha"), testCase("beta"), testCase("gamma")));
		setSimilarity(matrix, 0, 1, 0.9);
		setSimilarity(matrix, 1, 0, 0.8);
		setSimilarity(matrix, 0, 2, 0.5);
		setSimilarity(matrix, 2, 0, 0.4);
		setSimilarity(matrix, 2, 1, 0.2);
		setSimilarity(matrix, 1, 2, 0.1);
		return matrix;
	}

	private void setSimilarity(TestCaseSimilarityMatrix matrix, int source, int target, double similarity) {
		try {
			Method method = TestCaseSimilarityMatrix.class.getDeclaredMethod("setSimilarity", int.class, int.class, double.class);
			method.setAccessible(true);
			method.invoke(matrix, source, target, similarity);
		} catch (ReflectiveOperationException exception) {
			throw new AssertionError(exception);
		}
	}

	private TestCase testCase(String name) {
		return new TestCase(name, new CodeBlock(List.of()));
	}
}
