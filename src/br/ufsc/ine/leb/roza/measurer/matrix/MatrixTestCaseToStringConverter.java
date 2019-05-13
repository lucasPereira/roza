package br.ufsc.ine.leb.roza.measurer.matrix;

import br.ufsc.ine.leb.roza.TestCase;

public class MatrixTestCaseToStringConverter implements MatrixElementToKeyConverter<TestCase, String> {

	@Override
	public String convert(TestCase element) {
		return element.getName();
	}

}
