package br.ufsc.ine.leb.roza.measurer;

import br.ufsc.ine.leb.roza.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.support.matrix.MatrixElementToKeyConverter;

public class SimianMatrixElementToKeyConverter implements MatrixElementToKeyConverter<TestCaseMaterialization, String> {

	@Override
	public String convert(TestCaseMaterialization element) {
		return element.getAbsoluteFilePath();
	}

}
