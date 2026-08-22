package br.ufsc.ine.leb.roza.core.modern.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DiceMatchSimilarityTest {

	@Test
	void shouldComputeDiceScore() {
		assertEquals(0.0, DiceMatchSimilarity.score(0, 0, 0));
		assertEquals(1.0, DiceMatchSimilarity.score(2, 2, 2));
		assertEquals(0.8, DiceMatchSimilarity.score(2, 2, 3));
	}
}
