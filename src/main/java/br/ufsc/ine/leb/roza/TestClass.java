package br.ufsc.ine.leb.roza;

import java.util.List;

import br.ufsc.ine.leb.roza.exceptions.NoTestMethodException;

public class TestClass {

	private final String name;
	private final List<SetupMethod> setupMethods;
	private final List<TestMethod> testMethods;
	private final List<Field> fields;

	public TestClass(String name, List<Field> fields, List<SetupMethod> setupMethods, List<TestMethod> testMethods) {
		this.name = name;
		this.fields = fields;
		this.setupMethods = setupMethods;
		this.testMethods = testMethods;
		if (testMethods.isEmpty()) {
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
