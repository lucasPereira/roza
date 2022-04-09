package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;
import java.util.Set;

import br.ufsc.ine.leb.roza.extraction.TestCase;
import br.ufsc.ine.leb.roza.measurement.SimilarityReport;

public class CompleteLinkage implements Linkage {

	private SimilarityReport report;

	public CompleteLinkage(SimilarityReport report) {
		this.report = report;
	}

	@Override
	public BigDecimal evaluate(Cluster first, Cluster second) {
		BigDecimal farthestDistance = BigDecimal.ONE;
		Set<Pair> pairs = new Pairing(first, second).getPairs();
		for (Pair pair : pairs) {
			TestCase firstTest = pair.getFirst();
			TestCase secondTest = pair.getSecond();
			BigDecimal distance = report.getPair(firstTest, secondTest).getScore();
			if (distance.compareTo(farthestDistance) < 0) {
				farthestDistance = distance;
			}
		}
		return farthestDistance;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
