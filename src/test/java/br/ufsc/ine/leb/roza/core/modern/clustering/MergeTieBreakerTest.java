package br.ufsc.ine.leb.roza.core.modern.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;

class MergeTieBreakerTest {

	@Test
	void shouldReturnEmptyWhenCompositeHasNoTieBreakersAndTieExists() {
		List<MergeCandidate> candidates = List.of(candidate(cluster(0), cluster(1)), candidate(cluster(2), cluster(3)));

		assertTrue(new CompositeMergeTieBreaker(List.of()).breakTie(candidates).isEmpty());
	}

	@Test
	void shouldUseOrderedFallbackWhenFirstTieBreakerDoesNotResolve() {
		MergeCandidate first = candidate(cluster(2), cluster(3));
		MergeCandidate second = candidate(cluster(0), cluster(1));

		MergeCandidate selected = new CompositeMergeTieBreaker(List.of(new LargestMergedClusterTieBreaker(), new StableTestCaseOrderMergeTieBreaker()))
				.breakTie(List.of(first, second))
				.orElseThrow();

		assertSame(second, selected);
	}

	@Test
	void shouldUseStableTestCaseOrderAsDeterministicFallback() {
		MergeCandidate first = candidate(cluster(2), cluster(3));
		MergeCandidate second = candidate(cluster(0), cluster(1));

		MergeCandidate selected = new StableTestCaseOrderMergeTieBreaker().breakTie(List.of(first, second)).orElseThrow();

		assertEquals(List.of(0, 1), selected.mergedCluster().testCaseIndexes());
	}

	private MergeCandidate candidate(TestCaseCluster first, TestCaseCluster second) {
		return new MergeCandidate(new ClusterPair(first, second), 0.5);
	}

	private TestCaseCluster cluster(int index) {
		return new TestCaseCluster(index, new TestCase("test" + index, new CodeBlock(List.of())));
	}
}
