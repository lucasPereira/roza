package br.ufsc.ine.leb.roza;

import java.util.List;

public class TestClass {

	private String name;
	private List<TestMethod> testMethods;

	public TestClass(String name, List<TestMethod> testMethods) {
		this.name = name;
		this.testMethods = testMethods;
		if (testMethods.size() == 0) {
			throw new NoTestMethodException();
		}
	}

	public String getName() {
		return name;
	}

	public List<TestMethod> getTestMethods() {
		return testMethods;
	}

}
