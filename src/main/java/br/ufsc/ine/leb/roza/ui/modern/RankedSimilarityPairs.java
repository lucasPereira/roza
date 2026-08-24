package br.ufsc.ine.leb.roza.ui.modern;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMatrix;

final class RankedSimilarityPairs {

	static final int DISPLAY_LIMIT = 1000;

	private RankedSimilarityPairs() {
	}

	static List<Item> top(TestCaseSimilarityMatrix matrix, boolean descending) {
		return top(matrix, descending, DISPLAY_LIMIT);
	}

	static List<Item> top(TestCaseSimilarityMatrix matrix, boolean descending, int limit) {
		Objects.requireNonNull(matrix);
		int size = matrix.size();
		if (size < 2 || limit < 1) {
			return List.of();
		}
		long pairCount = (long) size * (size - 1);
		if (pairCount <= limit) {
			return allPairs(matrix, descending);
		}
		return limitedPairs(matrix, descending, limit);
	}

	private static List<Item> allPairs(TestCaseSimilarityMatrix matrix, boolean descending) {
		int size = matrix.size();
		List<Item> items = new ArrayList<>(size * (size - 1));
		for (int source = 0; source < size; source++) {
			for (int target = 0; target < size; target++) {
				if (source != target) {
					items.add(item(matrix, source, target));
				}
			}
		}
		items.sort(displayOrder(descending));
		return List.copyOf(items);
	}

	private static List<Item> limitedPairs(TestCaseSimilarityMatrix matrix, boolean descending, int limit) {
		Comparator<Item> order = displayOrder(descending);
		PriorityQueue<Item> selected = new PriorityQueue<>(order.reversed());
		int size = matrix.size();
		for (int source = 0; source < size; source++) {
			for (int target = 0; target < size; target++) {
				if (source == target) {
					continue;
				}
				double similarity = matrix.similarity(source, target);
				if (selected.size() < limit) {
					selected.add(item(matrix, source, target, similarity));
					continue;
				}
				if (compareCandidate(matrix, source, target, similarity, selected.peek(), descending) < 0) {
					selected.poll();
					selected.add(item(matrix, source, target, similarity));
				}
			}
		}
		List<Item> items = new ArrayList<>(selected);
		items.sort(order);
		return List.copyOf(items);
	}

	private static Item item(TestCaseSimilarityMatrix matrix, int source, int target) {
		return item(matrix, source, target, matrix.similarity(source, target));
	}

	private static Item item(TestCaseSimilarityMatrix matrix, int source, int target, double similarity) {
		return new Item(source, target, matrix.testCaseAt(source), matrix.testCaseAt(target), similarity);
	}

	private static Comparator<Item> displayOrder(boolean descending) {
		Comparator<Item> order = Comparator
				.comparingDouble(Item::similarity)
				.thenComparing(item -> item.sourceTestCase().name())
				.thenComparing(item -> item.targetTestCase().name());
		return descending ? order.reversed() : order;
	}

	private static int compareCandidate(
			TestCaseSimilarityMatrix matrix,
			int source,
			int target,
			double similarity,
			Item other,
			boolean descending) {
		int bySimilarity = Double.compare(similarity, other.similarity());
		int ordered = bySimilarity != 0
				? bySimilarity
				: compareNames(
						matrix.testCaseAt(source).name(),
						other.sourceTestCase().name(),
						matrix.testCaseAt(target).name(),
						other.targetTestCase().name());
		return descending ? -ordered : ordered;
	}

	private static int compareNames(String sourceName, String otherSourceName, String targetName, String otherTargetName) {
		int bySource = sourceName.compareTo(otherSourceName);
		return bySource != 0 ? bySource : targetName.compareTo(otherTargetName);
	}

	static final class Item {

		private final int sourceIndex;
		private final int targetIndex;
		private final TestCase sourceTestCase;
		private final TestCase targetTestCase;
		private final double similarity;

		private Item(int sourceIndex, int targetIndex, TestCase sourceTestCase, TestCase targetTestCase, double similarity) {
			this.sourceIndex = sourceIndex;
			this.targetIndex = targetIndex;
			this.sourceTestCase = sourceTestCase;
			this.targetTestCase = targetTestCase;
			this.similarity = similarity;
		}

		int sourceIndex() {
			return sourceIndex;
		}

		int targetIndex() {
			return targetIndex;
		}

		TestCase sourceTestCase() {
			return sourceTestCase;
		}

		TestCase targetTestCase() {
			return targetTestCase;
		}

		double similarity() {
			return similarity;
		}
	}
}
