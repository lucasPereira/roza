package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.exceptions.NoNextLevelException;

class Level {

	private Linkage linkage;
	private Set<Cluster> clusters;

	public Level(Linkage linkage, Set<Cluster> clusters) {
		this.linkage = linkage;
		this.clusters = clusters;
	}

	public Boolean hasNextLevel() {
		return clusters.size() > 1;
	}

	public Level generateNextLevel() {
		if (!hasNextLevel()) {
			throw new NoNextLevelException();
		}
		Set<Cluster> next = new HashSet<>(clusters.size() - 1);
		Combination combination = linkage.link(new ClustersToMerge(clusters));
		Cluster first = combination.getFirst();
		Cluster second = combination.getSecond();
		Cluster merged = first.merge(second);
		next.add(merged);
		clusters.stream().filter((cluster) -> !cluster.equals(first) && !cluster.equals(second)).forEach((cluster) -> next.add(cluster));
		return new Level(linkage, next);
	}

	public Set<Cluster> getClusters() {
		return Collections.unmodifiableSet(clusters);
	}

}
