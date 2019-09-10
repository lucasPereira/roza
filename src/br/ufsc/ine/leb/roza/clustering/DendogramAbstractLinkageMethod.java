package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;
import java.util.List;

import br.ufsc.ine.leb.roza.SimilarityReport;

public abstract class DendogramAbstractLinkageMethod implements DendogramLinkageMethod {

	@Override
	public DendogramLinkage link(List<DendogramCluster> clusters, SimilarityReport similarityReport) {
		DendogramCluster firstMostSimilar = null;
		DendogramCluster secondMostSimilar = null;
		BigDecimal mostSimilarDistance = BigDecimal.ZERO;
		for (DendogramCluster first : clusters) {
			for (DendogramCluster second : clusters) {
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
		return new DendogramLinkage(firstMostSimilar, secondMostSimilar);
	}

	protected abstract BigDecimal evaluateSimilarity(DendogramCluster first, DendogramCluster second, SimilarityReport similarityReport);

}
