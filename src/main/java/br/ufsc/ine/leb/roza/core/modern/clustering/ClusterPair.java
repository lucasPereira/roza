package br.ufsc.ine.leb.roza.core.modern.clustering;

import java.util.Objects;

public final class ClusterPair {

	private final TestCaseCluster first;
	private final TestCaseCluster second;

	public ClusterPair(TestCaseCluster first, TestCaseCluster second) {
		this.first = Objects.requireNonNull(first);
		this.second = Objects.requireNonNull(second);
	}

	public TestCaseCluster first() {
		return first;
	}

	public TestCaseCluster second() {
		return second;
	}

	public TestCaseCluster mergedCluster() {
		return first.merge(second);
	}
}
