package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.SimilarityReport;

abstract class AbstractLinkage implements Linkage {

	@Override
	public final Link link(ClustersToMerge clusters, SimilarityReport similarityReport) {
		Cluster firstMostSimilar = null;
		Cluster secondMostSimilar = null;
		BigDecimal mostSimilarDistance = BigDecimal.ZERO;
		for (Cluster first : clusters) {
			for (Cluster second : clusters) {
				if (first != second) {
					BigDecimal clusterDistance = evaluateSimilarity(first, second, similarityReport);
					if (clusterDistance.compareTo(mostSimilarDistance) > 0) {
						mostSimilarDistance = clusterDistance;
						firstMostSimilar = first;
						secondMostSimilar = second;
					}
				}
			}
		}
		return new Link(firstMostSimilar, secondMostSimilar);
	}

	protected abstract BigDecimal evaluateSimilarity(Cluster first, Cluster second, SimilarityReport similarityReport);

}
