package br.ufsc.ine.leb.roza.core.modern.arrangement;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.measurement.TextualPrefixSimilarity;

public final class AdmissiblePrefixMatcher {

	public static final int DEFAULT_MAP_NODE_LIMIT = 50_000;

	public static int greedyPrefixSize(
			TestCase source,
			List<String> targetStatements,
			ArrangeDependencyGraphCache graphCache) {
		ArrangeDependencyGraph graph = graphCache.graph(source);
		if (!graph.valid()) {
			return textualPrefixSize(source, targetStatements);
		}
		BitSet placed = new BitSet(graph.size());
		int prefixSize = 0;
		while (prefixSize < targetStatements.size()) {
			int matchingStatement = firstReadyMatch(graph, placed, targetStatements.get(prefixSize));
			if (matchingStatement < 0) {
				break;
			}
			placed.set(matchingStatement);
			prefixSize++;
		}
		return prefixSize;
	}

	public static int maximumPrefixSize(
			TestCase source,
			List<String> targetStatements,
			ArrangeDependencyGraphCache graphCache,
			int nodeLimit) {
		if (nodeLimit <= 0) {
			throw new IllegalArgumentException("MAP node limit must be positive.");
		}
		ArrangeDependencyGraph graph = graphCache.graph(source);
		if (!graph.valid()) {
			return textualPrefixSize(source, targetStatements);
		}
		MaximumSearch search = new MaximumSearch(graph, targetStatements, nodeLimit);
		int prefixSize = search.find();
		return search.limitExceeded()
				? greedyPrefixSize(source, targetStatements, graphCache)
				: prefixSize;
	}

	private static int firstReadyMatch(
			ArrangeDependencyGraph graph,
			BitSet placed,
			String targetStatement) {
		for (int statementIndex = 0; statementIndex < graph.size(); statementIndex++) {
			if (!placed.get(statementIndex)
					&& ready(graph, placed, statementIndex)
					&& graph.statement(statementIndex).normalizedText().equals(targetStatement)) {
				return statementIndex;
			}
		}
		return -1;
	}

	private static boolean ready(
			ArrangeDependencyGraph graph,
			BitSet placed,
			int statementIndex) {
		for (int predecessor : graph.predecessors(statementIndex)) {
			if (!placed.get(predecessor)) {
				return false;
			}
		}
		return true;
	}

	private static int textualPrefixSize(TestCase source, List<String> targetStatements) {
		return TextualPrefixSimilarity.commonPrefixSize(
				ArrangeProjection.normalizedStatements(source),
				targetStatements);
	}

	private static final class MaximumSearch {

		private final ArrangeDependencyGraph graph;
		private final List<String> targetStatements;
		private final int nodeLimit;
		private final Map<BitSet, Integer> memoizedPrefixSizes;
		private int visitedNodes;
		private boolean limitExceeded;

		private MaximumSearch(
				ArrangeDependencyGraph graph,
				List<String> targetStatements,
				int nodeLimit) {
			this.graph = graph;
			this.targetStatements = targetStatements;
			this.nodeLimit = nodeLimit;
			memoizedPrefixSizes = new HashMap<>();
		}

		private int find() {
			return find(new BitSet(graph.size()));
		}

		private int find(BitSet placed) {
			visitedNodes++;
			if (visitedNodes > nodeLimit) {
				limitExceeded = true;
				return placed.cardinality();
			}
			Integer memoized = memoizedPrefixSizes.get(placed);
			if (memoized != null) {
				return memoized;
			}
			int prefixIndex = placed.cardinality();
			if (prefixIndex >= targetStatements.size()) {
				return prefixIndex;
			}
			int best = prefixIndex;
			String targetStatement = targetStatements.get(prefixIndex);
			for (int statementIndex = 0; statementIndex < graph.size(); statementIndex++) {
				if (placed.get(statementIndex)
						|| !ready(graph, placed, statementIndex)
						|| !graph.statement(statementIndex).normalizedText().equals(targetStatement)) {
					continue;
				}
				placed.set(statementIndex);
				best = Math.max(best, find(placed));
				placed.clear(statementIndex);
				if (limitExceeded) {
					return best;
				}
			}
			memoizedPrefixSizes.put((BitSet) placed.clone(), best);
			return best;
		}

		private boolean limitExceeded() {
			return limitExceeded;
		}
	}
}
