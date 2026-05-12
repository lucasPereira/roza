package br.ufsc.ine.leb.roza.core.modern.clustering;

import java.util.Objects;

public final class MergeCandidate {

	private final ClusterPair pair;
	private final double similarity;

	public MergeCandidate(ClusterPair pair, double similarity) {
		this.pair = Objects.requireNonNull(pair);
		this.similarity = similarity;
	}

	public ClusterPair pair() {
		return pair;
	}

	public double similarity() {
		return similarity;
	}

	public TestCaseCluster mergedCluster() {
		return pair.mergedCluster();
	}
}
