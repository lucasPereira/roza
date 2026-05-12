package br.ufsc.ine.leb.roza.core.modern.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SimianMeasurementConfigurationTest {

	@Test
	void shouldUseDefaultThreshold() {
		SimianMeasurementConfiguration configuration = new SimianMeasurementConfiguration();

		assertEquals(6, configuration.threshold());
	}

	@Test
	void shouldRejectThresholdSmallerThanTwo() {
		assertThrows(IllegalArgumentException.class, () -> new SimianMeasurementConfiguration(1));
	}
}
