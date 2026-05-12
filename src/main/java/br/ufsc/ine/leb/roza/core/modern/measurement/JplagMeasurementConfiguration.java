package br.ufsc.ine.leb.roza.core.modern.measurement;

public final class JplagMeasurementConfiguration {

	public static final int DEFAULT_SENSITIVITY = 1;

	private final int sensitivity;

	public JplagMeasurementConfiguration() {
		this(DEFAULT_SENSITIVITY);
	}

	public JplagMeasurementConfiguration(int sensitivity) {
		if (sensitivity <= 0) {
			throw new IllegalArgumentException("JPlag sensitivity must be positive.");
		}
		this.sensitivity = sensitivity;
	}

	public int sensitivity() {
		return sensitivity;
	}
}
