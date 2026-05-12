package br.ufsc.ine.leb.roza.core.modern.clustering;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class LargestMergedClusterTieBreaker implements MergeTieBreaker {

	@Override
	public Optional<MergeCandidate> breakTie(List<MergeCandidate> candidates) {
		Optional<Integer> optionalBest = candidates.stream().map(candidate -> candidate.mergedCluster().size()).max(Integer::compareTo);
		if (optionalBest.isEmpty()) {
			return Optional.empty();
		}
		int best = optionalBest.get();
		List<MergeCandidate> selected = candidates.stream().filter(candidate -> candidate.mergedCluster().size() == best).collect(Collectors.toList());
		if (selected.size() == 1) {
			return Optional.of(selected.get(0));
		}
		return Optional.empty();
	}
}
