package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;

public class PairingTest {

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
		Set<Pair> alphaPairsAlpha = new Pairing(alphaCluster, alphaCluster).getPairs();
		assertEquals(1, alphaPairsAlpha.size());
		assertTrue(alphaPairsAlpha.contains(alphaAlphaPair));
	}

	@Test
	void pairClustersWithTheSameElement() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		Set<Pair> alphaPairsAlpha = new Pairing(alphaCluster, new Cluster(alpha)).getPairs();
		assertEquals(1, alphaPairsAlpha.size());
		assertTrue(alphaPairsAlpha.contains(alphaAlphaPair));
	}

	@Test
	void pairClustersWithDistincElements() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Set<Pair> alphaPairsBeta = new Pairing(alphaCluster, betaCluster).getPairs();
		Set<Pair> betaPairsAlpha = new Pairing(betaCluster, alphaCluster).getPairs();

		assertEquals(2, alphaPairsBeta.size());
		assertTrue(alphaPairsBeta.contains(alphaBetaPair));
		assertTrue(alphaPairsBeta.contains(betaAlphaPair));

		assertEquals(2, betaPairsAlpha.size());
		assertTrue(betaPairsAlpha.contains(alphaBetaPair));
		assertTrue(betaPairsAlpha.contains(betaAlphaPair));
	}

	@Test
	void pairClustersWithDistincElementsAndDistinctSizes() throws Exception {
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Cluster gammaCluster = new Cluster(gamma);
		Cluster alphaBetaCluster = alphaCluster.merge(betaCluster);
		Set<Pair> alphaBetaPairsGamma = new Pairing(alphaBetaCluster, gammaCluster).getPairs();
		Set<Pair> gammaPairsAlphaBeta = new Pairing(gammaCluster, alphaBetaCluster).getPairs();

		assertEquals(4, alphaBetaPairsGamma.size());
		assertTrue(alphaBetaPairsGamma.contains(alphaGammaPair));
		assertTrue(alphaBetaPairsGamma.contains(betaGammaPair));
		assertTrue(alphaBetaPairsGamma.contains(gammaAlphaPair));
		assertTrue(alphaBetaPairsGamma.contains(gammaBetaPair));

		assertEquals(4, gammaPairsAlphaBeta.size());
		assertTrue(gammaPairsAlphaBeta.contains(alphaGammaPair));
		assertTrue(gammaPairsAlphaBeta.contains(betaGammaPair));
		assertTrue(gammaPairsAlphaBeta.contains(gammaAlphaPair));
		assertTrue(gammaPairsAlphaBeta.contains(gammaBetaPair));
	}
}
