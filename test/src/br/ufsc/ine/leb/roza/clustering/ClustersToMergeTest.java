package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.exceptions.NoClustersToMergeException;
import br.ufsc.ine.leb.roza.utils.CollectionUtils;

class ClustersToMergeTest {

	private Cluster alphaCluster;
	private Cluster betaCluster;
	private Cluster gammaCluster;
	private CollectionUtils collectionUtils;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", List.of(), List.of());
		TestCase beta = new TestCase("beta", List.of(), List.of());
		TestCase gamma = new TestCase("gamma", List.of(), List.of());
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		gammaCluster = new Cluster(gamma);
		collectionUtils = new CollectionUtils();
	}

	@Test
	void zeroElements() {
		assertThrows(NoClustersToMergeException.class, () -> new ClustersToMerge(collectionUtils.set()));
	}

	@Test
	void oneElement() {
		assertThrows(NoClustersToMergeException.class, () -> new ClustersToMerge(collectionUtils.set(alphaCluster)));
	}

	@Test
	void twoElements() {
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaCluster, betaCluster));
		Combination alphaBetaCombination = new Combination(alphaCluster, betaCluster);
		Combination betaAlphaCombination = new Combination(betaCluster, alphaCluster);
		assertEquals(1, clusters.getCombinations().size());
		assertTrue(clusters.getCombinations().contains(alphaBetaCombination));
		assertTrue(clusters.getCombinations().contains(betaAlphaCombination));
	}

	@Test
	void threeElements() {
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaCluster, betaCluster, gammaCluster));
		Combination alphaBetaCombination = new Combination(alphaCluster, betaCluster);
		Combination alphaGammaCombination = new Combination(alphaCluster, gammaCluster);
		Combination betaGammaCombination = new Combination(betaCluster, gammaCluster);
		assertEquals(3, clusters.getCombinations().size());
		assertTrue(clusters.getCombinations().contains(alphaBetaCombination));
		assertTrue(clusters.getCombinations().contains(alphaGammaCombination));
		assertTrue(clusters.getCombinations().contains(betaGammaCombination));
	}

}
