package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.extraction.TestCase;

class PairingTest {

	private TestCase alpha;
	private TestCase beta;
	private TestCase gamma;
	private Pair alphaAlphaPair;
	private Pair alphaBetaPair;
	private Pair betaAlphaPair;
	private Pair alphaGammaPair;
	private Pair gammaAlphaPair;
	private Pair betaGammaPair;
	private Pair gammaBetaPair;

	@BeforeEach
	void setup() {
		alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		alphaAlphaPair = new Pair(alpha, alpha);
		alphaBetaPair = new Pair(alpha, beta);
		betaAlphaPair = new Pair(beta, alpha);
		alphaGammaPair = new Pair(alpha, gamma);
		gammaAlphaPair = new Pair(gamma, alpha);
		betaGammaPair = new Pair(beta, gamma);
		gammaBetaPair = new Pair(gamma, beta);
	}

	@Test
	void pairClusterWithItself() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		Set<Pair> alphaPairedAlpha = new Pairing(alphaCluster, alphaCluster).getPairs();
		assertEquals(1, alphaPairedAlpha.size());
		assertTrue(alphaPairedAlpha.contains(alphaAlphaPair));
	}

	@Test
	void pairClustersWithTheSameElement() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		Set<Pair> alphaPairedAlpha = new Pairing(alphaCluster, new Cluster(alpha)).getPairs();
		assertEquals(1, alphaPairedAlpha.size());
		assertTrue(alphaPairedAlpha.contains(alphaAlphaPair));
	}

	@Test
	void pairClustersWithDistincElements() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Set<Pair> alphaPairedBeta = new Pairing(alphaCluster, betaCluster).getPairs();
		Set<Pair> betaPairedAlpha = new Pairing(betaCluster, alphaCluster).getPairs();

		assertEquals(2, alphaPairedBeta.size());
		assertTrue(alphaPairedBeta.contains(alphaBetaPair));
		assertTrue(alphaPairedBeta.contains(betaAlphaPair));

		assertEquals(2, betaPairedAlpha.size());
		assertTrue(betaPairedAlpha.contains(alphaBetaPair));
		assertTrue(betaPairedAlpha.contains(betaAlphaPair));
	}

	@Test
	void pairClustersWithDistincElementsAndDistinctSizes() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Cluster gammaCluster = new Cluster(gamma);
		Cluster alphaBetaCluster = alphaCluster.merge(betaCluster);
		Set<Pair> alphaBetaPairedGamma = new Pairing(alphaBetaCluster, gammaCluster).getPairs();
		Set<Pair> gammaPairedAlphaBeta = new Pairing(gammaCluster, alphaBetaCluster).getPairs();

		assertEquals(4, alphaBetaPairedGamma.size());
		assertTrue(alphaBetaPairedGamma.contains(alphaGammaPair));
		assertTrue(alphaBetaPairedGamma.contains(betaGammaPair));
		assertTrue(alphaBetaPairedGamma.contains(gammaAlphaPair));
		assertTrue(alphaBetaPairedGamma.contains(gammaBetaPair));

		assertEquals(4, gammaPairedAlphaBeta.size());
		assertTrue(gammaPairedAlphaBeta.contains(alphaGammaPair));
		assertTrue(gammaPairedAlphaBeta.contains(betaGammaPair));
		assertTrue(gammaPairedAlphaBeta.contains(gammaAlphaPair));
		assertTrue(gammaPairedAlphaBeta.contains(gammaBetaPair));
	}

	@Test
	void pairClustersWithDistincElementsAndDistinctSizesWithoutRepetition() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Cluster gammaCluster = new Cluster(gamma);
		Cluster alphaBetaCluster = alphaCluster.merge(betaCluster);
		Set<Pair> alphaBetaPairedGamma = new Pairing(alphaBetaCluster, gammaCluster).getPairsWithoutRepetition();
		Set<Pair> gammaPairedAlphaBeta = new Pairing(gammaCluster, alphaBetaCluster).getPairsWithoutRepetition();

		assertEquals(2, alphaBetaPairedGamma.size());
		assertTrue(alphaBetaPairedGamma.contains(alphaGammaPair));
		assertTrue(alphaBetaPairedGamma.contains(betaGammaPair));

		assertEquals(2, gammaPairedAlphaBeta.size());
		assertTrue(gammaPairedAlphaBeta.contains(gammaAlphaPair));
		assertTrue(gammaPairedAlphaBeta.contains(gammaBetaPair));
	}

}
