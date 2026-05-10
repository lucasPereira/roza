package br.ufsc.ine.leb.roza.core.legacy.clustering;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.core.legacy.Cluster;
import br.ufsc.ine.leb.roza.core.legacy.SimilarityReport;
import br.ufsc.ine.leb.roza.core.legacy.TestCase;

public class CompleteLinkage implements Linkage {

	private final SimilarityReport report;

	public CompleteLinkage(SimilarityReport report) {
		this.report = report;
	}

	@Override
	public BigDecimal evaluate(Cluster first, Cluster second) {
		BigDecimal farthestDistance = BigDecimal.ONE;
		for (TestCase firstTest : first.getTestCases()) {
			for (TestCase secondTest : second.getTestCases()) {
				BigDecimal firstSecondDistance = report.getPair(firstTest, secondTest).getScore();
				BigDecimal secondFirstDistance = report.getPair(secondTest, firstTest).getScore();
				if (firstSecondDistance.compareTo(farthestDistance) < 0) {
					farthestDistance = firstSecondDistance;
				}
				if (secondFirstDistance.compareTo(farthestDistance) < 0) {
					farthestDistance = secondFirstDistance;
				}
			}
		}
		return farthestDistance;
	}

	@Override
	public String toString() {
		return "Complete linkage";
	}

}
