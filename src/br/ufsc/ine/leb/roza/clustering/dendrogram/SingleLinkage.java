package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.math.BigDecimal;
import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;

class SingleLinkage extends AbstractLinkage implements Linkage {

	public SingleLinkage(SimilarityReport report) {
		super(report);
	}

	@Override
	protected BigDecimal evaluateSimilarity(Cluster first, Cluster second, SimilarityReport report) {
		BigDecimal mostSimilar = BigDecimal.ZERO;
		Set<Pair> pairs = new Pairing(first, second).getPairs();
		for (Pair pair : pairs) {
			TestCase firstTestCase = pair.getFirst();
			TestCase secondTestCase = pair.getSecond();
			BigDecimal assessmentDistance = report.getPair(firstTestCase, secondTestCase).getScore();
			if (assessmentDistance.compareTo(mostSimilar) > 0) {
				mostSimilar = assessmentDistance;
			}
		}
		return mostSimilar;
	}

}
