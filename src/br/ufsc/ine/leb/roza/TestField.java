package br.ufsc.ine.leb.roza;

import java.util.List;

public class TestField implements TestBlock {

	private TestClass testClass;
	private List<Statement> fields;

	public TestField(TestClass testClass, List<Statement> fields) {
		this.testClass = testClass;
		this.fields = fields;
	}

	public TestClass getTestClass() {
		return testClass;
	}

	public List<Statement> getFields() {
		return fields;
	}

}
