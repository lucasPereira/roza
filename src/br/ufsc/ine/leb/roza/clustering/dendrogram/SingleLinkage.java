package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;

class SingleLinkage extends AbstractLinkage implements Linkage {

	@Override
	protected BigDecimal evaluateSimilarity(Cluster first, Cluster second, SimilarityReport similarityReport) {
		BigDecimal mostSimilar = BigDecimal.ZERO;
		for (TestCase firstTestCase : first.getElements()) {
			for (TestCase secondTestCase : second.getElements()) {
				BigDecimal assessmentDistance = similarityReport.getPair(firstTestCase, secondTestCase).getScore();
				if (assessmentDistance.compareTo(mostSimilar) > 0) {
					mostSimilar = assessmentDistance;
				}
			}
		}
		return mostSimilar;
	}

}
