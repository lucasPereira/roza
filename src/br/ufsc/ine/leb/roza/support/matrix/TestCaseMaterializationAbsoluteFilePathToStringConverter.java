package br.ufsc.ine.leb.roza.support.matrix;

import br.ufsc.ine.leb.roza.TestCaseMaterialization;

public class TestCaseMaterializationAbsoluteFilePathToStringConverter implements MatrixElementToKeyConverter<TestCaseMaterialization, String> {

	@Override
	public String convert(TestCaseMaterialization element) {
		return element.getAbsoluteFilePath();
	}

}
