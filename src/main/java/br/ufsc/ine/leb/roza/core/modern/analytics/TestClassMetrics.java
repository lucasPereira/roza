package br.ufsc.ine.leb.roza.core.modern.analytics;

public final class TestClassMetrics {

	private final int testClasses;
	private final int testMethods;
	private final int setupMethods;
	private final int attributes;
	private final int duplicatedLines;
	private final int uniqueDuplicatedLines;

	public TestClassMetrics(
			int testClasses,
			int testMethods,
			int setupMethods,
			int attributes,
			int duplicatedLines,
			int uniqueDuplicatedLines) {
		this.testClasses = testClasses;
		this.testMethods = testMethods;
		this.setupMethods = setupMethods;
		this.attributes = attributes;
		this.duplicatedLines = duplicatedLines;
		this.uniqueDuplicatedLines = uniqueDuplicatedLines;
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

	public int duplicatedLines() {
		return duplicatedLines;
	}

	public int uniqueDuplicatedLines() {
		return uniqueDuplicatedLines;
	}
}
