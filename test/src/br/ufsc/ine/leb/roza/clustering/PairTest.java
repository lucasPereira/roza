package br.ufsc.ine.leb.roza.clustering;

import br.ufsc.ine.leb.roza.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PairTest {

	private Pair alphaBetaPair;
	private Pair betaAlphaPair;
	private TestCase alpha;
	private TestCase beta;

	@BeforeEach
	void setup() {
		alpha = new TestCase("alpha", List.of(), List.of());
		beta = new TestCase("beta", List.of(), List.of());
		alphaBetaPair = new Pair(alpha, beta);
		betaAlphaPair = new Pair(beta, alpha);
	}

	@Test
	void alphaBeta() {
		assertEquals(alpha, alphaBetaPair.getFirst());
		assertEquals(beta, alphaBetaPair.getSecond());
		assertEquals(alphaBetaPair, new Pair(alpha, beta));
		assertEquals(alphaBetaPair.hashCode(), new Pair(alpha, beta).hashCode());
		assertNotEquals(alphaBetaPair, betaAlphaPair);
	}

	@Test
	void betaAlpha() {
		assertEquals(beta, betaAlphaPair.getFirst());
		assertEquals(alpha, betaAlphaPair.getSecond());
		assertEquals(betaAlphaPair, new Pair(beta, alpha));
		assertEquals(betaAlphaPair.hashCode(), new Pair(beta, alpha).hashCode());
		assertNotEquals(betaAlphaPair, alphaBetaPair);
	}

}
