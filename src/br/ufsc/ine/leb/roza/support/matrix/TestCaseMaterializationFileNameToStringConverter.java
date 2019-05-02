package br.ufsc.ine.leb.roza.support.matrix;

import br.ufsc.ine.leb.roza.TestCaseMaterialization;

public class TestCaseMaterializationFileNameToStringConverter implements MatrixElementToKeyConverter<TestCaseMaterialization, String> {

	@Override
	public String convert(TestCaseMaterialization element) {
		return element.getFileName();
	}

}
