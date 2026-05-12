package br.ufsc.ine.leb.roza.core.modern.clustering;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

class StopCriterionTest {

	@Test
	void shouldNotStopWhenCompositeHasNoCriteria() {
		assertFalse(new CompositeStopCriterion(List.of()).shouldStop(context(0.5, 1, clusters("alpha", "beta"))));
	}

	@Test
	void shouldStopWhenSimilarityIsAtMinimumBoundary() {
		assertTrue(new MinimumSimilarityStopCriterion(0.5).shouldStop(context(0.5, 1, clusters("alpha", "beta"))));
	}

	@Test
	void shouldStopWhenMergedClusterWouldExceedMaximumTests() {
		assertTrue(new MaxTestsPerClusterStopCriterion(1).shouldStop(context(0.5, 1, clusters("alpha", "beta"))));
	}

	@Test
	void shouldStopWhenNextLevelWouldExceedMaximumLevel() {
		assertTrue(new MaxLevelStopCriterion(0).shouldStop(context(0.5, 1, clusters("alpha", "beta"))));
	}

	@Test
	void shouldStopWhenTargetClusterCountWasReached() {
		assertTrue(new TargetClusterCountStopCriterion(2).shouldStop(context(0.5, 1, clusters("alpha", "beta"))));
	}

	@Test
	void shouldStopWhenMergedTestsDoNotShareEnoughInitialStatements() {
		TestCase first = testCase("alpha", statement("shared();"), statement("alpha();"));
		TestCase second = testCase("beta", statement("shared();"), statement("beta();"));
		StopCriterionContext context = context(0.5, 1, List.of(new TestCaseCluster(0, first), new TestCaseCluster(1, second)));

		assertTrue(new MinimumSharedPrefixStopCriterion(1).shouldStop(context));
		assertFalse(new MinimumSharedPrefixStopCriterion(0).shouldStop(context));
	}

	private StopCriterionContext context(double similarity, int nextLevel, List<TestCaseCluster> clusters) {
		MergeCandidate candidate = new MergeCandidate(new ClusterPair(clusters.get(0), clusters.get(1)), similarity);
		return new StopCriterionContext(clusters, candidate, nextLevel, clusters.size() - 1);
	}

	private List<TestCaseCluster> clusters(String first, String second) {
		return List.of(new TestCaseCluster(0, testCase(first)), new TestCaseCluster(1, testCase(second)));
	}

	private TestCase testCase(String name, CodeStatement... statements) {
		return new TestCase(name, new CodeBlock(List.of(statements)));
	}

	private CodeStatement statement(String text) {
		return new CodeStatement(text, text);
	}
}
