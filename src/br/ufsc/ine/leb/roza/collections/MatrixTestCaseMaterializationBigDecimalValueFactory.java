package br.ufsc.ine.leb.roza.collections;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.TestCaseMaterialization;

public class MatrixTestCaseMaterializationBigDecimalValueFactory implements MatrixValueFactory<TestCaseMaterialization, BigDecimal> {

	@Override
	public BigDecimal create(TestCaseMaterialization source, TestCaseMaterialization target) {
		return source.equals(target) ? BigDecimal.ONE : BigDecimal.ZERO;
	}

}
