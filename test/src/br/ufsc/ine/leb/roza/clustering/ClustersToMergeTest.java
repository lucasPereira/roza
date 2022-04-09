package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.exceptions.NoClustersToMergeException;
import br.ufsc.ine.leb.roza.extraction.TestCase;
import br.ufsc.ine.leb.roza.utils.CollectionUtils;

class ClustersToMergeTest {

	private Cluster alphaCluster;
	private Cluster betaCluster;
	private Cluster gammaCluster;
	private CollectionUtils collectionUtils;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		gammaCluster = new Cluster(gamma);
		collectionUtils = new CollectionUtils();
	}

	@Test
	void zeroElements() throws Exception {
		assertThrows(NoClustersToMergeException.class, () -> {
			new ClustersToMerge(collectionUtils.set());
		});
	}

	@Test
	void oneElement() throws Exception {
		assertThrows(NoClustersToMergeException.class, () -> {
			new ClustersToMerge(collectionUtils.set(alphaCluster));
		});
	}

	@Test
	void twoElements() throws Exception {
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaCluster, betaCluster));
		Combination alphaBetaCombination = new Combination(alphaCluster, betaCluster);
		Combination betaAlphaCombination = new Combination(betaCluster, alphaCluster);
		assertEquals(1, clusters.getCombinations().size());
		assertTrue(clusters.getCombinations().contains(alphaBetaCombination));
		assertTrue(clusters.getCombinations().contains(betaAlphaCombination));
	}

	@Test
	void threeElements() throws Exception {
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
