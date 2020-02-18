package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.List;

import br.ufsc.ine.leb.roza.exceptions.NoClustersToMergeException;

public class ClustersToMerge {

	private List<Cluster> clusters;

	public ClustersToMerge(List<Cluster> clusters) {
		if (clusters.size() < 2) {
			throw new NoClustersToMergeException();
		}
		this.clusters = clusters;
	}

	public List<Cluster> getClusters() {
		return clusters;
	}

}
