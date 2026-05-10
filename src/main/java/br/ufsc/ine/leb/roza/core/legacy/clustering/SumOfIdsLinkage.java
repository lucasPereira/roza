package br.ufsc.ine.leb.roza.core.legacy.clustering;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.core.legacy.Cluster;
import br.ufsc.ine.leb.roza.core.legacy.TestCase;
import br.ufsc.ine.leb.roza.core.legacy.utils.MathUtils;

class SumOfIdsLinkage implements Linkage {

	@Override
	public BigDecimal evaluate(Cluster first, Cluster second) {
		Integer sumFirst = first.getTestCases().stream().map(TestCase::getId).reduce(0, Integer::sum);
		Integer sumSecond = second.getTestCases().stream().map(TestCase::getId).reduce(0, Integer::sum);
		return new MathUtils().oneOver(sumFirst, sumSecond);
	}

}
