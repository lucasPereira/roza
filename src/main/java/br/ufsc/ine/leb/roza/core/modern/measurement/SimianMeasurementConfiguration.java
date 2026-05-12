package br.ufsc.ine.leb.roza.core.modern.measurement;

public final class SimianMeasurementConfiguration {

	public static final int DEFAULT_THRESHOLD = 6;

	private final int threshold;

	public SimianMeasurementConfiguration() {
		this(DEFAULT_THRESHOLD);
	}

	public SimianMeasurementConfiguration(int threshold) {
		if (threshold < 2) {
			throw new IllegalArgumentException("Simian threshold must be at least 2.");
		}
		this.threshold = threshold;
	}

	public int threshold() {
		return threshold;
	}
}
