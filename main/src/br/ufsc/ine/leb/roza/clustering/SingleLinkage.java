package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;
import java.util.Set;

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

	@Override
	public String toString() {
		return "Single linkage";
	}

}
