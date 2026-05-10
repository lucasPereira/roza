package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;

class CombinationTest {

	private Cluster alphaCluster;
	private Cluster betaCluster;
	private Combination alphaBetaCombination;
	private Combination betaAlphaCombination;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", List.of(), List.of());
		TestCase beta = new TestCase("beta", List.of(), List.of());
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		alphaBetaCombination = new Combination(alphaCluster, betaCluster);
		betaAlphaCombination = new Combination(betaCluster, alphaCluster);
	}

	@Test
	void alphaBeta() {
		assertEquals(alphaCluster, alphaBetaCombination.getFirst());
		assertEquals(betaCluster, alphaBetaCombination.getSecond());
		assertEquals(alphaBetaCombination, new Combination(alphaCluster, betaCluster));
		assertEquals(alphaBetaCombination.hashCode(), new Combination(alphaCluster, betaCluster).hashCode());
		assertEquals(alphaBetaCombination, betaAlphaCombination);
	}

	@Test
	void betaAlpha() {
		assertEquals(betaCluster, betaAlphaCombination.getFirst());
		assertEquals(alphaCluster, betaAlphaCombination.getSecond());
		assertEquals(betaAlphaCombination, new Combination(betaCluster, alphaCluster));
		assertEquals(betaAlphaCombination.hashCode(), new Combination(betaCluster, alphaCluster).hashCode());
		assertEquals(betaAlphaCombination, alphaBetaCombination);
	}

}
