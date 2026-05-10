package br.ufsc.ine.leb.roza.core.measurement.matrix.jplag;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.core.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.core.measurement.matrix.MatrixValueFactory;

public class JplagMatrixValueFactory implements MatrixValueFactory<TestCaseMaterialization, BigDecimal> {

	@Override
	public BigDecimal create(TestCaseMaterialization source, TestCaseMaterialization target) {
		return source.equals(target) ? BigDecimal.ONE : BigDecimal.ZERO;
	}

}
