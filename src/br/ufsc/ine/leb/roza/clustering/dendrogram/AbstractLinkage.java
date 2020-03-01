package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.math.BigDecimal;
import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityReport;

abstract class AbstractLinkage implements Linkage {

	private SimilarityReport report;

	public AbstractLinkage(SimilarityReport report) {
		this.report = report;
	}

	@Override
	public final Combination link(ClustersToMerge clusters) {
		Combination choosed = null;
		BigDecimal mostSimilarDistance = BigDecimal.ZERO;
		Set<Combination> combinations = clusters.getCombinations();
		for (Combination combination : combinations) {
			Cluster first = combination.getFirst();
			Cluster second = combination.getSecond();
			BigDecimal clusterDistance = evaluateSimilarity(first, second, report);
			if (clusterDistance.compareTo(mostSimilarDistance) > 0) {
				mostSimilarDistance = clusterDistance;
				choosed = combination;
			}
		}
		return choosed;
	}

	protected abstract BigDecimal evaluateSimilarity(Cluster first, Cluster second, SimilarityReport report);

}
