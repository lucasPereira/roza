package br.ufsc.ine.leb.roza.core.modern.clustering;

import java.util.List;
import java.util.Objects;

public final class StopCriterionContext {

	private final List<TestCaseCluster> currentClusters;
	private final MergeCandidate candidate;
	private final int nextLevel;
	private final int nextClusterCount;

	public StopCriterionContext(List<TestCaseCluster> currentClusters, MergeCandidate candidate, int nextLevel, int nextClusterCount) {
		this.currentClusters = List.copyOf(Objects.requireNonNull(currentClusters));
		this.candidate = Objects.requireNonNull(candidate);
		this.nextLevel = nextLevel;
		this.nextClusterCount = nextClusterCount;
	}

	public List<TestCaseCluster> currentClusters() {
		return currentClusters;
	}

	public MergeCandidate candidate() {
		return candidate;
	}

	public int nextLevel() {
		return nextLevel;
	}

	public int nextClusterCount() {
		return nextClusterCount;
	}
}
