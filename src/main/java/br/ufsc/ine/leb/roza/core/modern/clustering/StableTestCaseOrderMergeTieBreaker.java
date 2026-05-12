package br.ufsc.ine.leb.roza.core.modern.clustering;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public final class StableTestCaseOrderMergeTieBreaker implements MergeTieBreaker {

	@Override
	public Optional<MergeCandidate> breakTie(List<MergeCandidate> candidates) {
		return candidates.stream()
				.min(this::compare);
	}

	private int compare(MergeCandidate first, MergeCandidate second) {
		return Comparator.<MergeCandidate, List<Integer>>comparing(candidate -> candidate.mergedCluster().testCaseIndexes(), this::compareIndexes)
				.thenComparing(candidate -> candidate.pair().first().testCaseIndexes(), this::compareIndexes)
				.thenComparing(candidate -> candidate.pair().second().testCaseIndexes(), this::compareIndexes)
				.compare(first, second);
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
