package br.ufsc.ine.leb.roza.core.modern.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class JplagMeasurementConfigurationTest {

	@Test
	void shouldUseDefaultSensitivity() {
		JplagMeasurementConfiguration configuration = new JplagMeasurementConfiguration();

		assertEquals(1, configuration.sensitivity());
	}

	@Test
	void shouldRejectZeroSensitivity() {
		assertThrows(IllegalArgumentException.class, () -> new JplagMeasurementConfiguration(0));
	}

	@Test
	void shouldRejectNegativeSensitivity() {
		assertThrows(IllegalArgumentException.class, () -> new JplagMeasurementConfiguration(-1));
	}
}
