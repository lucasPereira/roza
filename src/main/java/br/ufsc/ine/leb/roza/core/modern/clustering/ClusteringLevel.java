package br.ufsc.ine.leb.roza.core.modern.clustering;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class ClusteringLevel {

	private final int number;
	private final List<TestCaseCluster> clusters;
	private final MergeCandidate acceptedMerge;

	public ClusteringLevel(int number, List<TestCaseCluster> clusters) {
		this(number, clusters, null);
	}

	public ClusteringLevel(int number, List<TestCaseCluster> clusters, MergeCandidate acceptedMerge) {
		if (number < 0) {
			throw new IllegalArgumentException("Clustering level number must not be negative.");
		}
		this.number = number;
		this.clusters = List.copyOf(Objects.requireNonNull(clusters));
		this.acceptedMerge = acceptedMerge;
	}

	public int number() {
		return number;
	}

	public List<TestCaseCluster> clusters() {
		return clusters;
	}

	public Optional<MergeCandidate> acceptedMerge() {
		return Optional.ofNullable(acceptedMerge);
	}
}
