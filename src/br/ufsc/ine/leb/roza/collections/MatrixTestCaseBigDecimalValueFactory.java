package br.ufsc.ine.leb.roza.collections;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.TestCase;

public class MatrixTestCaseBigDecimalValueFactory implements MatrixValueFactory<TestCase, BigDecimal> {

	@Override
	public BigDecimal create(TestCase source, TestCase target) {
		return source.equals(target) ? BigDecimal.ONE : BigDecimal.ZERO;
	}

}
