package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.extraction.TestCase;

class CombinationTest {

	private Cluster alphaCluster;
	private Cluster betaCluster;
	private Combination alphaBetaCombination;
	private Combination betaAlphaCombination;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		alphaBetaCombination = new Combination(alphaCluster, betaCluster);
		betaAlphaCombination = new Combination(betaCluster, alphaCluster);
	}

	@Test
	void alphaBeta() throws Exception {
		assertEquals(alphaCluster, alphaBetaCombination.getFirst());
		assertEquals(betaCluster, alphaBetaCombination.getSecond());
		assertEquals(alphaBetaCombination, new Combination(alphaCluster, betaCluster));
		assertEquals(alphaBetaCombination.hashCode(), new Combination(alphaCluster, betaCluster).hashCode());
		assertEquals(alphaBetaCombination, betaAlphaCombination);
	}

	@Test
	void betaAlpha() throws Exception {
		assertEquals(betaCluster, betaAlphaCombination.getFirst());
		assertEquals(alphaCluster, betaAlphaCombination.getSecond());
		assertEquals(betaAlphaCombination, new Combination(betaCluster, alphaCluster));
		assertEquals(betaAlphaCombination.hashCode(), new Combination(betaCluster, alphaCluster).hashCode());
		assertEquals(betaAlphaCombination, alphaBetaCombination);
	}

}
