package br.ufsc.ine.leb.roza.collections;

import br.ufsc.ine.leb.roza.TestCaseMaterialization;

public class MatrixTestCaseMaterializationAbsoluteFilePathToStringConverter implements MatrixElementToKeyConverter<TestCaseMaterialization, String> {

	@Override
	public String convert(TestCaseMaterialization element) {
		return element.getAbsoluteFilePath();
	}

}
