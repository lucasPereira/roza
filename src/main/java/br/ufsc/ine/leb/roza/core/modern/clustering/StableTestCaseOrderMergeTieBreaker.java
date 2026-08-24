package br.ufsc.ine.leb.roza.core.modern.clustering;

import java.util.List;
import java.util.Optional;

public final class StableTestCaseOrderMergeTieBreaker implements MergeTieBreaker {

	@Override
	public Optional<MergeCandidate> breakTie(List<MergeCandidate> candidates) {
		return candidates.stream()
				.min(this::compare);
	}

	private int compare(MergeCandidate first, MergeCandidate second) {
		int merged = compareMergedIndexes(first.pair(), second.pair());
		if (merged != 0) {
			return merged;
		}
		int firstIndexes = compareIndexes(first.pair().first().testCaseIndexes(), second.pair().first().testCaseIndexes());
		if (firstIndexes != 0) {
			return firstIndexes;
		}
		return compareIndexes(first.pair().second().testCaseIndexes(), second.pair().second().testCaseIndexes());
	}

	private int compareMergedIndexes(ClusterPair first, ClusterPair second) {
		List<Integer> firstLeft = first.first().testCaseIndexes();
		List<Integer> firstRight = first.second().testCaseIndexes();
		List<Integer> secondLeft = second.first().testCaseIndexes();
		List<Integer> secondRight = second.second().testCaseIndexes();
		int firstSize = firstLeft.size() + firstRight.size();
		int secondSize = secondLeft.size() + secondRight.size();
		int firstLeftIndex = 0;
		int firstRightIndex = 0;
		int secondLeftIndex = 0;
		int secondRightIndex = 0;
		int compared = 0;
		int limit = Math.min(firstSize, secondSize);
		while (compared < limit) {
			int firstValue = nextMergedIndex(firstLeft, firstRight, firstLeftIndex, firstRightIndex);
			int secondValue = nextMergedIndex(secondLeft, secondRight, secondLeftIndex, secondRightIndex);
			int comparison = Integer.compare(firstValue, secondValue);
			if (comparison != 0) {
				return comparison;
			}
			if (firstLeftIndex < firstLeft.size() && firstValue == firstLeft.get(firstLeftIndex)) {
				firstLeftIndex++;
			} else {
				firstRightIndex++;
			}
			if (secondLeftIndex < secondLeft.size() && secondValue == secondLeft.get(secondLeftIndex)) {
				secondLeftIndex++;
			} else {
				secondRightIndex++;
			}
			compared++;
		}
		return Integer.compare(firstSize, secondSize);
	}

	private int nextMergedIndex(List<Integer> left, List<Integer> right, int leftIndex, int rightIndex) {
		if (leftIndex >= left.size()) {
			return right.get(rightIndex);
		}
		if (rightIndex >= right.size()) {
			return left.get(leftIndex);
		}
		int leftValue = left.get(leftIndex);
		int rightValue = right.get(rightIndex);
		return leftValue <= rightValue ? leftValue : rightValue;
	}

	private int compareIndexes(List<Integer> first, List<Integer> second) {
		int size = Math.min(first.size(), second.size());
		for (int index = 0; index < size; index++) {
			int comparison = first.get(index).compareTo(second.get(index));
			if (comparison != 0) {
				return comparison;
			}
		}
		return Integer.compare(first.size(), second.size());
	}
}
