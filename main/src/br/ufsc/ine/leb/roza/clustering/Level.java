package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

public class Level {

	private Set<Cluster> clusters;
	private Integer step;
	private BigDecimal evaluation;

	public Level(Set<Cluster> clusters) {
		this.step = 0;
		this.clusters = clusters;
	}

	public Level(Level current, Set<Cluster> clusters, BigDecimal evaluation) {
		this.step = current.step + 1;
		this.clusters = clusters;
		this.evaluation = evaluation;
	}

	public Set<Cluster> getClusters() {
		return Collections.unmodifiableSet(clusters);
	}

	public BigDecimal getEvaluationToThisLevel() {
		return evaluation;
	}

	public Integer getStep() {
		return step;
	}

	@Override
	public String toString() {
		return String.format("Level %d with %d clusters", step, clusters.size());
	}
}
