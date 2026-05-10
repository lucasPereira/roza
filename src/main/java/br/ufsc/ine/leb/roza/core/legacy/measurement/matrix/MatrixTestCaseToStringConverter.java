package br.ufsc.ine.leb.roza.core.legacy.measurement.matrix;

import br.ufsc.ine.leb.roza.core.legacy.TestCase;

public class MatrixTestCaseToStringConverter implements MatrixElementToKeyConverter<TestCase, String> {

	@Override
	public String convert(TestCase element) {
		return element.getName();
	}

}
