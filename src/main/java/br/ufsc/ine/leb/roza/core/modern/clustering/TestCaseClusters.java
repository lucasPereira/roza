package br.ufsc.ine.leb.roza.core.modern.clustering;

import java.util.List;
import java.util.Objects;

public final class TestCaseClusters {

	private final List<TestCaseCluster> clusters;

	public TestCaseClusters(List<TestCaseCluster> clusters) {
		this.clusters = List.copyOf(Objects.requireNonNull(clusters));
	}

	public List<TestCaseCluster> clusters() {
		return clusters;
	}
}
