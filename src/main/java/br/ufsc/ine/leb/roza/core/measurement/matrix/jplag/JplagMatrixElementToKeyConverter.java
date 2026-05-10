package br.ufsc.ine.leb.roza.core.measurement.matrix.jplag;

import br.ufsc.ine.leb.roza.core.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.core.measurement.matrix.MatrixElementToKeyConverter;

public class JplagMatrixElementToKeyConverter implements MatrixElementToKeyConverter<TestCaseMaterialization, String> {

	@Override
	public String convert(TestCaseMaterialization element) {
		return element.getFileName();
	}

}
