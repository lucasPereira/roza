package br.ufsc.ine.leb.roza.core.clustering;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.core.Cluster;
import br.ufsc.ine.leb.roza.core.SimilarityReport;
import br.ufsc.ine.leb.roza.core.TestCase;
import br.ufsc.ine.leb.roza.core.utils.MathUtils;

public class AverageLinkage implements Linkage {

	private final SimilarityReport report;

	public AverageLinkage(SimilarityReport report) {
		this.report = report;
	}

	@Override
	public BigDecimal evaluate(Cluster first, Cluster second) {
		BigDecimal averageDistance = BigDecimal.ZERO;
		for (TestCase firstTest : first.getTestCases()) {
			for (TestCase secondTest : second.getTestCases()) {
				BigDecimal firstSecondDistance = report.getPair(firstTest, secondTest).getScore();
				BigDecimal secondFirstDistance = report.getPair(secondTest, firstTest).getScore();
				BigDecimal distance = firstSecondDistance.compareTo(secondFirstDistance) > 0 ? firstSecondDistance : secondFirstDistance;
				averageDistance = averageDistance.add(distance);
			}
		}
		BigDecimal dimension = new MathUtils().oneOver(first.getTestCases().size() * second.getTestCases().size());
		return dimension.multiply(averageDistance);
	}

	@Override
	public String toString() {
		return "Average linkage";
	}

}
