package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;

public class SingleLinkage implements Linkage {

	private final SimilarityReport report;

	public SingleLinkage(SimilarityReport report) {
		this.report = report;
	}

	@Override
	public BigDecimal evaluate(Cluster first, Cluster second) {
		BigDecimal nearestDistance = BigDecimal.ZERO;
		for (TestCase firstTest : first.getTestCases()) {
			for (TestCase secondTest : second.getTestCases()) {
				BigDecimal firstSecondDistance = report.getPair(firstTest, secondTest).getScore();
				BigDecimal secondFirstDistance = report.getPair(secondTest, firstTest).getScore();
				if (firstSecondDistance.compareTo(nearestDistance) > 0) {
					nearestDistance = firstSecondDistance;
				}
				if (secondFirstDistance.compareTo(nearestDistance) > 0) {
					nearestDistance = secondFirstDistance;
				}
			}
		}
		return nearestDistance;
	}

	@Override
	public String toString() {
		return "Single linkage";
	}

}
