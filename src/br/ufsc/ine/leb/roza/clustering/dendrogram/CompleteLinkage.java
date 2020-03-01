package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.math.BigDecimal;
import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;

class CompleteLinkage extends AbstractLinkage implements Linkage {

	public CompleteLinkage(SimilarityReport report) {
		super(report);
	}

	@Override
	protected BigDecimal evaluateSimilarity(Cluster first, Cluster second, SimilarityReport report) {
		BigDecimal mostDisimilar = BigDecimal.ONE;
		Set<Pair> pairs = new Pairing(first, second).getPairs();
		for (Pair pair : pairs) {
			TestCase firstTestCase = pair.getFirst();
			TestCase secondTestCase = pair.getSecond();
			BigDecimal assessmentDistance = report.getPair(firstTestCase, secondTestCase).getScore();
			if (assessmentDistance.compareTo(mostDisimilar) < 0) {
				mostDisimilar = assessmentDistance;
			}
		}
		return mostDisimilar;
	}

}
