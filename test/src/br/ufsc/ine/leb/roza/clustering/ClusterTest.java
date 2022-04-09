package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.extraction.TestCase;

class ClusterTest {

	private TestCase alpha;
	private TestCase beta;
	private TestCase gamma;

	@BeforeEach
	void setup() {
		alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
	}

	@Test
	void cluster() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		assertEquals(1, alphaCluster.getTestCases().size());
		assertTrue(alphaCluster.getTestCases().contains(alpha));
		assertEquals(alphaCluster, alphaCluster);
		assertEquals(alphaCluster, new Cluster(alpha));
		assertEquals(alphaCluster.hashCode(), new Cluster(alpha).hashCode());
	}

	@Test
	void mergeClusterWithItself() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		Cluster alphaMergedAlpha = alphaCluster.merge(alphaCluster);
		assertEquals(1, alphaMergedAlpha.getTestCases().size());
		assertTrue(alphaMergedAlpha.getTestCases().contains(alpha));
		assertEquals(alphaMergedAlpha, alphaCluster);
		assertEquals(alphaMergedAlpha.hashCode(), alphaCluster.hashCode());
	}

	@Test
	void mergeClustersWithTheSameElement() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		Cluster alphaMergedAlpha = alphaCluster.merge(new Cluster(alpha));
		assertEquals(1, alphaMergedAlpha.getTestCases().size());
		assertTrue(alphaMergedAlpha.getTestCases().contains(alpha));
		assertEquals(alphaMergedAlpha, alphaCluster);
		assertEquals(alphaMergedAlpha.hashCode(), alphaCluster.hashCode());
	}

	@Test
	void mergeClustersWithDistincElements() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Cluster alphaMergedBeta = alphaCluster.merge(betaCluster);
		Cluster betaMergedAlpha = betaCluster.merge(alphaCluster);

		assertEquals(alphaMergedBeta, betaMergedAlpha);
		assertEquals(2, alphaMergedBeta.getTestCases().size());
		assertTrue(alphaMergedBeta.getTestCases().contains(alpha));
		assertTrue(alphaMergedBeta.getTestCases().contains(beta));

		assertEquals(betaMergedAlpha, alphaMergedBeta);
		assertEquals(2, alphaMergedBeta.getTestCases().size());
		assertTrue(betaMergedAlpha.getTestCases().contains(alpha));
		assertTrue(betaMergedAlpha.getTestCases().contains(beta));
	}

	@Test
	void mergeClustersWithDistincElementsAndDistinctSizes() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Cluster gammaCluster = new Cluster(gamma);
		Cluster alphaBetaCluster = alphaCluster.merge(betaCluster);
		Cluster alphaBetaMergedGamma = alphaBetaCluster.merge(gammaCluster);
		Cluster gammaMergedAlphaBeta = gammaCluster.merge(alphaBetaCluster);

		assertEquals(alphaBetaMergedGamma, gammaMergedAlphaBeta);
		assertEquals(3, alphaBetaMergedGamma.getTestCases().size());
		assertTrue(alphaBetaMergedGamma.getTestCases().contains(alpha));
		assertTrue(alphaBetaMergedGamma.getTestCases().contains(beta));
		assertTrue(alphaBetaMergedGamma.getTestCases().contains(gamma));

		assertEquals(gammaMergedAlphaBeta, alphaBetaMergedGamma);
		assertEquals(3, gammaMergedAlphaBeta.getTestCases().size());
		assertTrue(gammaMergedAlphaBeta.getTestCases().contains(alpha));
		assertTrue(gammaMergedAlphaBeta.getTestCases().contains(beta));
		assertTrue(gammaMergedAlphaBeta.getTestCases().contains(gamma));
	}

	@Test
	void mergeClustersWithOneEqualElementsAndOneDistincElement() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Cluster gammaCluster = new Cluster(gamma);
		Cluster alphaMergedGamma = alphaCluster.merge(gammaCluster);
		Cluster betaMergedGama = betaCluster.merge(gammaCluster);

		assertNotEquals(alphaMergedGamma, betaMergedGama);
		assertEquals(2, alphaMergedGamma.getTestCases().size());
		assertTrue(alphaMergedGamma.getTestCases().contains(alpha));
		assertFalse(alphaMergedGamma.getTestCases().contains(beta));
		assertTrue(alphaMergedGamma.getTestCases().contains(gamma));

		assertNotEquals(betaMergedGama, alphaMergedGamma);
		assertEquals(2, betaMergedGama.getTestCases().size());
		assertFalse(betaMergedGama.getTestCases().contains(alpha));
		assertTrue(betaMergedGama.getTestCases().contains(beta));
		assertTrue(betaMergedGama.getTestCases().contains(gamma));
	}

}
