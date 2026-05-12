package br.ufsc.ine.leb.roza.core.modern.analytics;

import java.util.Objects;

public final class TestCodeAnalyticsReport {

	private final OriginalTestCodeMetrics original;
	private final TestCodeMetricComparison comparison;

	public TestCodeAnalyticsReport(OriginalTestCodeMetrics original, TestCodeMetricComparison comparison) {
		this.original = Objects.requireNonNull(original);
		this.comparison = Objects.requireNonNull(comparison);
	}

	public OriginalTestCodeMetrics original() {
		return original;
	}

	public TestCodeMetricComparison comparison() {
		return comparison;
	}
}
