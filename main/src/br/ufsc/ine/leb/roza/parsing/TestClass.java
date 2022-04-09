package br.ufsc.ine.leb.roza.parsing;

import java.util.List;

import br.ufsc.ine.leb.roza.exceptions.NoTestMethodException;

public class TestClass {

	private String name;
	private List<SetupMethod> setupMethods;
	private List<TestMethod> testMethods;
	private List<Field> fields;

	public TestClass(String name, List<Field> fields, List<SetupMethod> setupMethods, List<TestMethod> testMethods) {
		this.name = name;
		this.fields = fields;
		this.setupMethods = setupMethods;
		this.testMethods = testMethods;
		if (testMethods.size() == 0) {
			throw new NoTestMethodException();
		}
	}

	public String getName() {
		return name;
	}

	public List<Field> getFields() {
		return fields;
	}

	public List<SetupMethod> getSetupMethods() {
		return setupMethods;
	}

	public List<TestMethod> getTestMethods() {
		return testMethods;
	}

	@Override
	public String toString() {
		return name;
	}

}
