package br.ufsc.ine.leb.roza.core.modern.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMatrix;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;

class AgglomerativeHierarchicalTestCaseClustererTest {

	@Test
	void shouldMergeUntilOneClusterWhenNoStopCriterionStops() {
		TestCaseSimilarityMatrix matrix = matrix();

		TestCaseClusters clusters = clusterer(new CompositeStopCriterion(List.of())).cluster(matrix);

		assertEquals(1, clusters.clusters().size());
		assertEquals(List.of("alpha", "beta", "gamma"), names(clusters.clusters().get(0)));
	}

	@Test
	void shouldStopWhenTargetClusterCountIsReached() {
		TestCaseSimilarityMatrix matrix = matrix();

		TestCaseClusters clusters = clusterer(new CompositeStopCriterion(List.of(new TargetClusterCountStopCriterion(2)))).cluster(matrix);

		assertEquals(2, clusters.clusters().size());
		assertEquals(List.of("alpha", "beta"), names(clusters.clusters().get(0)));
		assertEquals(List.of("gamma"), names(clusters.clusters().get(1)));
	}

	@Test
	void shouldExposeInitialAndAcceptedMergeLevels() {
		TestCaseSimilarityMatrix matrix = matrix();

		List<ClusteringLevel> levels = clusterer(new CompositeStopCriterion(List.of(new TargetClusterCountStopCriterion(2)))).generateLevels(matrix);

		assertEquals(2, levels.size());
		assertEquals(0, levels.get(0).number());
		assertEquals(3, levels.get(0).clusters().size());
		assertEquals(1, levels.get(1).number());
		assertEquals(2, levels.get(1).clusters().size());
		assertEquals(0.9, levels.get(1).acceptedMerge().orElseThrow().similarity(), 0.000001);
	}

	@Test
	void shouldThrowWhenBestMergeIsTiedAndNoTieBreakerResolvesIt() {
		TestCaseSimilarityMatrix matrix = tiedMatrix();

		AgglomerativeHierarchicalTestCaseClusterer clusterer = new AgglomerativeHierarchicalTestCaseClusterer(
				new SingleLinkage(),
				new CompositeStopCriterion(List.of()),
				new CompositeMergeTieBreaker(List.of()));

		assertThrows(IllegalStateException.class, () -> clusterer.cluster(matrix));
	}

	private AgglomerativeHierarchicalTestCaseClusterer clusterer(StopCriterion stopCriterion) {
		return new AgglomerativeHierarchicalTestCaseClusterer(
				new SingleLinkage(),
				stopCriterion,
				new CompositeMergeTieBreaker(List.of(new StableTestCaseOrderMergeTieBreaker())));
	}

	private List<String> names(TestCaseCluster cluster) {
		return cluster.testCases().stream().map(TestCase::name).collect(Collectors.toList());
	}

	private TestCaseSimilarityMatrix matrix() {
		TestCaseSimilarityMatrix matrix = new TestCaseSimilarityMatrix(List.of(testCase("alpha"), testCase("beta"), testCase("gamma")));
		setSimilarity(matrix, 0, 1, 0.9);
		setSimilarity(matrix, 1, 0, 0.9);
		setSimilarity(matrix, 0, 2, 0.4);
		setSimilarity(matrix, 2, 0, 0.4);
		setSimilarity(matrix, 1, 2, 0.2);
		setSimilarity(matrix, 2, 1, 0.2);
		return matrix;
	}

	private TestCaseSimilarityMatrix tiedMatrix() {
		TestCaseSimilarityMatrix matrix = new TestCaseSimilarityMatrix(List.of(testCase("alpha"), testCase("beta"), testCase("gamma")));
		setSimilarity(matrix, 0, 1, 0.9);
		setSimilarity(matrix, 1, 0, 0.9);
		setSimilarity(matrix, 0, 2, 0.9);
		setSimilarity(matrix, 2, 0, 0.9);
		setSimilarity(matrix, 1, 2, 0.2);
		setSimilarity(matrix, 2, 1, 0.2);
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
