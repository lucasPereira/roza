package br.ufsc.ine.leb.roza.core.modern.clustering;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class CompositeMergeTieBreaker implements MergeTieBreaker {

	private final List<MergeTieBreaker> tieBreakers;

	public CompositeMergeTieBreaker(List<MergeTieBreaker> tieBreakers) {
		this.tieBreakers = List.copyOf(Objects.requireNonNull(tieBreakers));
	}

	@Override
	public Optional<MergeCandidate> breakTie(List<MergeCandidate> candidates) {
		if (candidates.size() == 1) {
			return Optional.of(candidates.get(0));
		}
		for (MergeTieBreaker tieBreaker : tieBreakers) {
			Optional<MergeCandidate> selected = tieBreaker.breakTie(candidates);
			if (selected.isPresent()) {
				return selected;
			}
		}
		return Optional.empty();
	}
}
