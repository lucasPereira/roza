package br.ufsc.ine.leb.roza.core.modern.analytics;

public final class OriginalTestCodeMetrics {

	private final int testClasses;
	private final int testClassesWithoutViolations;
	private final int testClassesWithViolations;
	private final int testMethods;
	private final int testMethodsWithoutViolations;
	private final int testMethodsWithViolations;

	public OriginalTestCodeMetrics(
			int testClasses,
			int testClassesWithoutViolations,
			int testClassesWithViolations,
			int testMethods,
			int testMethodsWithoutViolations,
			int testMethodsWithViolations) {
		this.testClasses = testClasses;
		this.testClassesWithoutViolations = testClassesWithoutViolations;
		this.testClassesWithViolations = testClassesWithViolations;
		this.testMethods = testMethods;
		this.testMethodsWithoutViolations = testMethodsWithoutViolations;
		this.testMethodsWithViolations = testMethodsWithViolations;
	}

	public int testClasses() {
		return testClasses;
	}

	public int testClassesWithoutViolations() {
		return testClassesWithoutViolations;
	}

	public int testClassesWithViolations() {
		return testClassesWithViolations;
	}

	public int testMethods() {
		return testMethods;
	}

	public int testMethodsWithoutViolations() {
		return testMethodsWithoutViolations;
	}

	public int testMethodsWithViolations() {
		return testMethodsWithViolations;
	}
}
