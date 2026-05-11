package br.ufsc.ine.leb.roza.core.modern.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class DeckardMeasurementConfigurationTest {

	@Test
	void shouldUsePaperAndLegacyDefaults() {
		DeckardMeasurementConfiguration configuration = new DeckardMeasurementConfiguration();

		assertEquals(1, configuration.minTokens());
		assertEquals(0, configuration.stride());
		assertEquals(1.0, configuration.similarity());
	}

	@Test
	void shouldRejectNonPositiveMinTokens() {
		assertThrows(IllegalArgumentException.class, () -> new DeckardMeasurementConfiguration(0, 0, 1.0));
	}

	@Test
	void shouldRejectNegativeStride() {
		assertThrows(IllegalArgumentException.class, () -> new DeckardMeasurementConfiguration(1, -1, 1.0));
	}

	@Test
	void shouldRejectSimilarityOutsideRange() {
		assertThrows(IllegalArgumentException.class, () -> new DeckardMeasurementConfiguration(1, 0, -0.1));
		assertThrows(IllegalArgumentException.class, () -> new DeckardMeasurementConfiguration(1, 0, 1.1));
	}
}
