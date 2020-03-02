package br.ufsc.ine.leb.roza.measurement.matrix.jplag;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.measurement.matrix.MatrixValueFactory;

public class JplagMatrixValueFactory implements MatrixValueFactory<TestCaseMaterialization, BigDecimal> {

	@Override
	public BigDecimal create(TestCaseMaterialization source, TestCaseMaterialization target) {
		return source.equals(target) ? BigDecimal.ONE : BigDecimal.ZERO;
	}

}
