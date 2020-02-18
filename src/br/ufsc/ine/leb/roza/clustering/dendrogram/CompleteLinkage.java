package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;

class CompleteLinkage extends AbstractLinkage implements Linkage {

	@Override
	protected BigDecimal evaluateSimilarity(Cluster first, Cluster second, SimilarityReport similarityReport) {
		BigDecimal mostDisimilar = BigDecimal.ONE;
		for (TestCase firstTestCase : first.getElements()) {
			for (TestCase secondTestCase : second.getElements()) {
				BigDecimal assessmentDistance = similarityReport.getPair(firstTestCase, secondTestCase).getScore();
				if (assessmentDistance.compareTo(mostDisimilar) < 0) {
					mostDisimilar = assessmentDistance;
				}
			}
		}
		return mostDisimilar;
	}

}
