package br.ufsc.ine.leb.roza.ui.modern;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.clustering.AgglomerativeHierarchicalTestCaseClusterer;
import br.ufsc.ine.leb.roza.core.modern.clustering.ClusteringLevel;
import br.ufsc.ine.leb.roza.core.modern.clustering.CompositeMergeTieBreaker;
import br.ufsc.ine.leb.roza.core.modern.clustering.CompositeStopCriterion;
import br.ufsc.ine.leb.roza.core.modern.clustering.SingleLinkage;
import br.ufsc.ine.leb.roza.core.modern.clustering.StableTestCaseOrderMergeTieBreaker;
import br.ufsc.ine.leb.roza.core.modern.clustering.TargetClusterCountStopCriterion;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseCluster;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMatrix;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;

class ModernRozaUiTest {

	@Test
	void shouldKeepFormationSimilarityForOlderClustersAtSelectedLevel() {
		List<ClusteringLevel> levels = clusterer().generateLevels(matrix());

		Map<Set<Integer>, Double> similaritiesByCluster = ModernRozaUi.clusterFormationSimilaritiesByCluster(levels, 2);

		assertEquals(3, levels.size());
		assertEquals(2, similaritiesByCluster.size());
		assertEquals(0.9, similaritiesByCluster.get(Set.of(1, 0)), 0.000001);
		assertEquals(0.8, similaritiesByCluster.get(Set.of(3, 2)), 0.000001);
		assertEquals(0.9,
				ModernRozaUi.clusterFormationSimilarity(clusterAt(levels.get(2), Set.of(0, 1)), similaritiesByCluster).orElseThrow(),
				0.000001);
		assertEquals(0.8,
				ModernRozaUi.clusterFormationSimilarity(clusterAt(levels.get(2), Set.of(2, 3)), similaritiesByCluster).orElseThrow(),
				0.000001);
	}

	@Test
	void shouldShowSimilarityForSingletonClusters() {
		TestCaseCluster singleton = new TestCaseCluster(0, testCase("alpha"));

		assertEquals(0.7, ModernRozaUi.clusterFormationSimilarity(singleton, Map.of(Set.of(0), 0.7)).orElseThrow(), 0.000001);
		assertEquals(1.0, ModernRozaUi.clusterFormationSimilarity(singleton, Map.of()).orElseThrow(), 0.000001);
	}

	private AgglomerativeHierarchicalTestCaseClusterer clusterer() {
		return new AgglomerativeHierarchicalTestCaseClusterer(
				new SingleLinkage(),
				new CompositeStopCriterion(List.of(new TargetClusterCountStopCriterion(2))),
				new CompositeMergeTieBreaker(List.of(new StableTestCaseOrderMergeTieBreaker())));
	}

	private TestCaseCluster clusterAt(ClusteringLevel level, Set<Integer> indexes) {
		return level.clusters().stream()
				.filter(cluster -> ModernRozaUi.clusterKey(cluster).equals(indexes))
				.findFirst()
				.orElseThrow();
	}

	private TestCaseSimilarityMatrix matrix() {
		TestCaseSimilarityMatrix matrix = new TestCaseSimilarityMatrix(List.of(
				testCase("alpha"),
				testCase("beta"),
				testCase("gamma"),
				testCase("delta")));
		setSimilarity(matrix, 0, 1, 0.9);
		setSimilarity(matrix, 1, 0, 0.9);
		setSimilarity(matrix, 2, 3, 0.8);
		setSimilarity(matrix, 3, 2, 0.8);
		setCrossSimilarities(matrix, 0.1);
		return matrix;
	}

	private void setCrossSimilarities(TestCaseSimilarityMatrix matrix, double similarity) {
		setSimilarity(matrix, 0, 2, similarity);
		setSimilarity(matrix, 2, 0, similarity);
		setSimilarity(matrix, 0, 3, similarity);
		setSimilarity(matrix, 3, 0, similarity);
		setSimilarity(matrix, 1, 2, similarity);
		setSimilarity(matrix, 2, 1, similarity);
		setSimilarity(matrix, 1, 3, similarity);
		setSimilarity(matrix, 3, 1, similarity);
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
