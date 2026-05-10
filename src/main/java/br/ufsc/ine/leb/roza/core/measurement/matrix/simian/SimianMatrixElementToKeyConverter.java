package br.ufsc.ine.leb.roza.core.measurement.matrix.simian;

import br.ufsc.ine.leb.roza.core.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.core.measurement.matrix.MatrixElementToKeyConverter;

public class SimianMatrixElementToKeyConverter implements MatrixElementToKeyConverter<TestCaseMaterialization, String> {

	@Override
	public String convert(TestCaseMaterialization element) {
		return element.getAbsoluteFilePath();
	}

}
