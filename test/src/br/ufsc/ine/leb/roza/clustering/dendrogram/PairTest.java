package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TestCase;

class PairTest {

	private Pair alphaBetaPair;
	private Pair betaAlphaPair;
	private TestCase alpha;
	private TestCase beta;

	@BeforeEach
	void setup() {
		alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		alphaBetaPair = new Pair(alpha, beta);
		betaAlphaPair = new Pair(beta, alpha);
	}

	@Test
	void alphaBeta() throws Exception {
		assertEquals(alpha, alphaBetaPair.getFirst());
		assertEquals(beta, alphaBetaPair.getSecond());
		assertEquals(alphaBetaPair, new Pair(alpha, beta));
		assertEquals(alphaBetaPair.hashCode(), new Pair(alpha, beta).hashCode());
		assertNotEquals(alphaBetaPair, betaAlphaPair);
	}

	@Test
	void betaAlpha() throws Exception {
		assertEquals(beta, betaAlphaPair.getFirst());
		assertEquals(alpha, betaAlphaPair.getSecond());
		assertEquals(betaAlphaPair, new Pair(beta, alpha));
		assertEquals(betaAlphaPair.hashCode(), new Pair(beta, alpha).hashCode());
		assertNotEquals(betaAlphaPair, alphaBetaPair);
	}

}
