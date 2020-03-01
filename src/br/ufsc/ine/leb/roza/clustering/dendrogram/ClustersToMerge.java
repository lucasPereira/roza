package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.HashSet;
import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.exceptions.NoClustersToMergeException;

public class ClustersToMerge {

	private Set<Cluster> clusters;

	public ClustersToMerge(Set<Cluster> clusters) {
		if (clusters.size() < 2) {
			throw new NoClustersToMergeException();
		}
		this.clusters = clusters;
	}

	public Set<Combination> getCombinations() {
		Set<Combination> combinations = new HashSet<>();
		for (Cluster first : clusters) {
			for (Cluster second : clusters) {
				if (first != second) {
					combinations.add(new Combination(first, second));
				}
			}
		}
		return combinations;
	}

}
