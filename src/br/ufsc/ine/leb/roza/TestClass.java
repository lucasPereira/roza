package br.ufsc.ine.leb.roza;

import java.util.List;

import br.ufsc.ine.leb.roza.exceptions.NoTestMethodException;

public class TestClass {

	private String name;
	private List<SetupMethod> setupMethods;
	private List<TestMethod> testMethods;

	public TestClass(String name, List<SetupMethod> setupMethods, List<TestMethod> testMethods) {
		this.name = name;
		this.setupMethods = setupMethods;
		this.testMethods = testMethods;
		if (testMethods.size() == 0) {
			throw new NoTestMethodException();
		}
	}

	public String getName() {
		return name;
	}

	public List<SetupMethod> getSetupMethods() {
		return setupMethods;
	}

	public List<TestMethod> getTestMethods() {
		return testMethods;
	}

}
