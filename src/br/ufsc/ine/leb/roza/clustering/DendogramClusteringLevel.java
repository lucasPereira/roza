package br.ufsc.ine.leb.roza.clustering;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.exceptions.NoNextLevelException;

public class DendogramClusteringLevel {

	private List<DendogramCluster> clusters;
	private SimilarityReport similarityReport;

	public DendogramClusteringLevel(List<DendogramCluster> clusters, SimilarityReport similarityReport) {
		this.clusters = clusters;
		this.similarityReport = similarityReport;
	}

	public Boolean hasNextLevel() {
		return clusters.size() > 1;
	}

	public DendogramClusteringLevel generateNextLevel() {
		if (!hasNextLevel()) {
			throw new NoNextLevelException();
		}
		List<DendogramCluster> newClusters = new ArrayList<>(clusters.size() - 1);
		DendogramCluster clusterToBeMerged1 = clusters.get(0);
		DendogramCluster clusterToBeMerged2 = clusters.get(1);
		DendogramCluster mergedCluster = clusterToBeMerged1.merge(clusterToBeMerged2);
		newClusters.add(mergedCluster);
		for (DendogramCluster cluster : clusters) {
			if (!cluster.equals(clusterToBeMerged1) && !cluster.equals(clusterToBeMerged2)) {
				newClusters.add(cluster);
			}
		}
		return new DendogramClusteringLevel(newClusters, similarityReport);
	}

	public List<DendogramCluster> getClusters() {
		return clusters;
	}

}
