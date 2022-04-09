package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.extraction.TestCase;

class LevelTest {

	private Cluster alphaCluster;
	private Cluster betaCluster;
	private Cluster gammaCluster;
	private Cluster alphaBetaCluster;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		gammaCluster = new Cluster(gamma);
		alphaBetaCluster = alphaCluster.merge(betaCluster);
	}

	@Test
	void levelZeroWithoutElements() throws Exception {
		Set<Cluster> clusters = new HashSet<>();
		Level zero = new Level(clusters);

		assertEquals(0, zero.getStep());
		assertEquals(0, zero.getClusters().size());
		assertNull(zero.getEvaluationToThisLevel());
	}

	@Test
	void levelZeroWithOneElement() throws Exception {
		Set<Cluster> clusters = new HashSet<>();
		clusters.add(alphaCluster);
		Level zero = new Level(clusters);

		assertEquals(0, zero.getStep());
		assertEquals(1, zero.getClusters().size());
		assertTrue(zero.getClusters().contains(alphaCluster));
		assertNull(zero.getEvaluationToThisLevel());
	}

	@Test
	void levelOne() throws Exception {
		Set<Cluster> clustersZero = new HashSet<>();
		clustersZero.add(alphaCluster);
		clustersZero.add(betaCluster);
		clustersZero.add(gammaCluster);
		Set<Cluster> clustersOne = new HashSet<>();
		clustersOne.add(alphaBetaCluster);
		clustersOne.add(gammaCluster);
		Level zero = new Level(clustersZero);
		Level one = new Level(zero, clustersOne, BigDecimal.ONE);

		assertEquals(1, one.getStep());
		assertEquals(2, one.getClusters().size());
		assertTrue(one.getClusters().contains(alphaBetaCluster));
		assertTrue(one.getClusters().contains(gammaCluster));
		assertEquals(BigDecimal.ONE, one.getEvaluationToThisLevel());
	}

}
