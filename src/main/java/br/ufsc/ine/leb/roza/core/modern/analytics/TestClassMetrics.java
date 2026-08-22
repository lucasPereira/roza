package br.ufsc.ine.leb.roza.core.modern.analytics;

public final class TestClassMetrics {

	private final int testClasses;
	private final int testMethods;
	private final int setupMethods;
	private final int attributes;
	private final int totalStatements;
	private final int duplicatedStatements;

	public TestClassMetrics(
			int testClasses,
			int testMethods,
			int setupMethods,
			int attributes,
			int totalStatements,
			int duplicatedStatements) {
		this.testClasses = testClasses;
		this.testMethods = testMethods;
		this.setupMethods = setupMethods;
		this.attributes = attributes;
		this.totalStatements = totalStatements;
		this.duplicatedStatements = duplicatedStatements;
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

	public int attributes() {
		return attributes;
	}

	public int totalStatements() {
		return totalStatements;
	}

	public int duplicatedStatements() {
		return duplicatedStatements;
	}

	public double duplicationRate() {
		if (totalStatements == 0) {
			return 0.0;
		}
		return (double) duplicatedStatements / totalStatements;
	}
}
