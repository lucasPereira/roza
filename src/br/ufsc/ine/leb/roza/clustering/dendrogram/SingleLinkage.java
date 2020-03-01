package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.math.BigDecimal;
import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;

class SingleLinkage extends AbstractLinkage implements Linkage {

	public SingleLinkage(Referee referee, SimilarityReport report) {
		super(referee, report);
	}

	@Override
	protected BigDecimal evaluateSimilarity(Cluster first, Cluster second, SimilarityReport report) {
		BigDecimal nearestDistance = BigDecimal.ZERO;
		Set<Pair> pairs = new Pairing(first, second).getPairs();
		for (Pair pair : pairs) {
			TestCase firstTest = pair.getFirst();
			TestCase secondTest = pair.getSecond();
			BigDecimal distance = report.getPair(firstTest, secondTest).getScore();
			if (distance.compareTo(nearestDistance) > 0) {
				nearestDistance = distance;
			}
		}
		return nearestDistance;
	}

}
