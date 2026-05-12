package br.ufsc.ine.leb.roza.core.modern.analytics;

public final class TestClassMetrics {

	private final int testClasses;
	private final int testMethods;
	private final int setupMethods;
	private final int fields;

	public TestClassMetrics(int testClasses, int testMethods, int setupMethods, int fields) {
		this.testClasses = testClasses;
		this.testMethods = testMethods;
		this.setupMethods = setupMethods;
		this.fields = fields;
	}

	public int testClasses() {
		return testClasses;
	}

	public int testMethods() {
		return testMethods;
	}

	public int setupMethods() {
		return setupMethods;
	}

	public int fields() {
		return fields;
	}
}
