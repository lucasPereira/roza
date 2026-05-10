package br.ufsc.ine.leb.roza.core.clustering;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.core.Cluster;
import br.ufsc.ine.leb.roza.core.TestCase;
import br.ufsc.ine.leb.roza.core.utils.MathUtils;

class SumOfIdsLinkage implements Linkage {

	@Override
	public BigDecimal evaluate(Cluster first, Cluster second) {
		Integer sumFirst = first.getTestCases().stream().map(TestCase::getId).reduce(0, Integer::sum);
		Integer sumSecond = second.getTestCases().stream().map(TestCase::getId).reduce(0, Integer::sum);
		return new MathUtils().oneOver(sumFirst, sumSecond);
	}

}
