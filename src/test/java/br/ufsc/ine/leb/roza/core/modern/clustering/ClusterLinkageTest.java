package br.ufsc.ine.leb.roza.core.modern.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMatrix;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;

class ClusterLinkageTest {

	@Test
	void shouldComputeSingleLinkageFromHighestDirectedPairSimilarity() {
		TestCaseSimilarityMatrix matrix = matrix();
		TestCaseCluster first = new TestCaseCluster(0, matrix.testCaseAt(0)).merge(new TestCaseCluster(1, matrix.testCaseAt(1)));
		TestCaseCluster second = new TestCaseCluster(2, matrix.testCaseAt(2));

		assertEquals(0.8, new SingleLinkage().evaluate(matrix, first, second), 0.000001);
	}

	@Test
	void shouldComputeCompleteLinkageFromLowestDirectedPairSimilarity() {
		TestCaseSimilarityMatrix matrix = matrix();
		TestCaseCluster first = new TestCaseCluster(0, matrix.testCaseAt(0)).merge(new TestCaseCluster(1, matrix.testCaseAt(1)));
		TestCaseCluster second = new TestCaseCluster(2, matrix.testCaseAt(2));

		assertEquals(0.2, new CompleteLinkage().evaluate(matrix, first, second), 0.000001);
	}

	@Test
	void shouldComputeAverageLinkageFromBestDirectionForEachCrossPair() {
		TestCaseSimilarityMatrix matrix = matrix();
		TestCaseCluster first = new TestCaseCluster(0, matrix.testCaseAt(0)).merge(new TestCaseCluster(1, matrix.testCaseAt(1)));
		TestCaseCluster second = new TestCaseCluster(2, matrix.testCaseAt(2));

		assertEquals(0.55, new AverageLinkage().evaluate(matrix, first, second), 0.000001);
	}

	private TestCaseSimilarityMatrix matrix() {
		TestCaseSimilarityMatrix matrix = new TestCaseSimilarityMatrix(List.of(testCase("alpha"), testCase("beta"), testCase("gamma")));
		setSimilarity(matrix, 0, 2, 0.2);
		setSimilarity(matrix, 2, 0, 0.3);
		setSimilarity(matrix, 1, 2, 0.8);
		setSimilarity(matrix, 2, 1, 0.5);
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
