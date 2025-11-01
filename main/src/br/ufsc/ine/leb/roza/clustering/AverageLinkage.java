package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;
import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.utils.MathUtils;

public class AverageLinkage implements Linkage {

	private final SimilarityReport report;

	public AverageLinkage(SimilarityReport report) {
		this.report = report;
	}

	@Override
	public BigDecimal evaluate(Cluster first, Cluster second) {
		BigDecimal averageDistance = BigDecimal.ZERO;
		Set<Pair> pairs = new Pairing(first, second).getPairsWithoutRepetition();
		for (Pair pair : pairs) {
			TestCase firstTest = pair.getFirst();
			TestCase secondTest = pair.getSecond();
			BigDecimal firstSecondDistance = report.getPair(firstTest, secondTest).getScore();
			BigDecimal secondFirstDistance = report.getPair(secondTest, firstTest).getScore();
			BigDecimal distance = firstSecondDistance.compareTo(secondFirstDistance) > 0 ? firstSecondDistance : secondFirstDistance;
			averageDistance = averageDistance.add(distance);
		}
		BigDecimal dimension = new MathUtils().oneOver(first.getTestCases().size() * second.getTestCases().size());
		return dimension.multiply(averageDistance);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
