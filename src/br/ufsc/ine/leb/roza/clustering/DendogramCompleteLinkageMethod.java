package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;


public class DendogramCompleteLinkageMethod extends DendogramAbstractLinkageMethod implements DendogramLinkageMethod {

	@Override
	protected BigDecimal evaluateSimilarity(DendogramCluster first, DendogramCluster second, SimilarityReport similarityReport) {
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
