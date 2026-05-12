package br.ufsc.ine.leb.roza.core.modern.analytics;

import java.util.Objects;

public final class TestCodeMetricComparison {

	private final TestClassMetrics original;
	private final TestClassMetrics refactored;

	public TestCodeMetricComparison(TestClassMetrics original, TestClassMetrics refactored) {
		this.original = Objects.requireNonNull(original);
		this.refactored = Objects.requireNonNull(refactored);
	}

	public TestClassMetrics original() {
		return original;
	}

	public TestClassMetrics refactored() {
		return refactored;
	}
}
