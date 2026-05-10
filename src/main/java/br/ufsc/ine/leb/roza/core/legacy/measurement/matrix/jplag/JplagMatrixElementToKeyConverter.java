package br.ufsc.ine.leb.roza.core.legacy.measurement.matrix.jplag;

import br.ufsc.ine.leb.roza.core.legacy.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.core.legacy.measurement.matrix.MatrixElementToKeyConverter;

public class JplagMatrixElementToKeyConverter implements MatrixElementToKeyConverter<TestCaseMaterialization, String> {

	@Override
	public String convert(TestCaseMaterialization element) {
		return element.getFileName();
	}

}
