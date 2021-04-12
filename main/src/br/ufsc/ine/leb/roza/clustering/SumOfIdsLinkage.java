package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.utils.MathUtils;

class SumOfIdsLinkage implements Linkage {

	@Override
	public BigDecimal evaluate(Cluster first, Cluster second) {
		Integer sumFirst = first.getTestCases().stream().map(test -> test.getId()).reduce(0, (sum, value) -> sum + value).intValue();
		Integer sumSecond = second.getTestCases().stream().map(test -> test.getId()).reduce(0, (sum, value) -> sum + value).intValue();
		return new MathUtils().oneOver(sumFirst, sumSecond);
	}

}
