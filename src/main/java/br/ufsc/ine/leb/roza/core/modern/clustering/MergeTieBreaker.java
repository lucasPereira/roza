package br.ufsc.ine.leb.roza.core.modern.clustering;

import java.util.List;
import java.util.Optional;

public interface MergeTieBreaker {

	Optional<MergeCandidate> breakTie(List<MergeCandidate> candidates);
}
