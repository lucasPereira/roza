package br.ufsc.ine.leb.roza.clustering;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.exceptions.NoNextLevelException;

public class DendogramClusteringLevel {

	private List<DendogramCluster> clusters;
	private SimilarityReport similarityReport;
	private DendogramLinkageMethod linkageMethod;

	public DendogramClusteringLevel(DendogramLinkageMethod linkageMethod, List<DendogramCluster> clusters, SimilarityReport similarityReport) {
		this.linkageMethod = linkageMethod;
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
		DendogramLinkage linkage = linkageMethod.link(clusters, similarityReport);
		DendogramCluster firtClusterToBeMerged = linkage.getFirst();
		DendogramCluster secondClusterToBeMerged = linkage.getSecond();
		DendogramCluster mergedCluster = firtClusterToBeMerged.merge(secondClusterToBeMerged);
		newClusters.add(mergedCluster);
		for (DendogramCluster cluster : clusters) {
			if (!cluster.equals(firtClusterToBeMerged) && !cluster.equals(secondClusterToBeMerged)) {
				newClusters.add(cluster);
			}
		}
		return new DendogramClusteringLevel(linkageMethod, newClusters, similarityReport);
	}

	public List<DendogramCluster> getClusters() {
		return clusters;
	}

}
