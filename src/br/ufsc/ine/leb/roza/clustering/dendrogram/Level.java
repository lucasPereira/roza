package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.exceptions.NoNextLevelException;

class Level {

	private List<Cluster> clusters;
	private SimilarityReport similarityReport;
	private Linkage linkageMethod;

	public Level(Linkage linkageMethod, List<Cluster> clusters, SimilarityReport similarityReport) {
		this.linkageMethod = linkageMethod;
		this.clusters = clusters;
		this.similarityReport = similarityReport;
	}

	public Boolean hasNextLevel() {
		return clusters.size() > 1;
	}

	public Level generateNextLevel() {
		if (!hasNextLevel()) {
			throw new NoNextLevelException();
		}
		List<Cluster> newClusters = new ArrayList<>(clusters.size() - 1);
		Link linkage = linkageMethod.link(new ClustersToMerge(clusters), similarityReport);
		Cluster firtClusterToBeMerged = linkage.getFirst();
		Cluster secondClusterToBeMerged = linkage.getSecond();
		Cluster mergedCluster = firtClusterToBeMerged.merge(secondClusterToBeMerged);
		newClusters.add(mergedCluster);
		for (Cluster cluster : clusters) {
			if (!cluster.equals(firtClusterToBeMerged) && !cluster.equals(secondClusterToBeMerged)) {
				newClusters.add(cluster);
			}
		}
		return new Level(linkageMethod, newClusters, similarityReport);
	}

	public List<Cluster> getClusters() {
		return clusters;
	}

}
