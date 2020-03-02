package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.utils.MathUtils;

public class SumOfIdsLinkageTest {

	private TestCase alpha;
	private TestCase beta;
	private TestCase gamma;
	private Linkage linkage;
	private MathUtils mathUtils;
	private Cluster alphaCluster;
	private Cluster betaCluster;
	private Cluster gammaCluster;
	private Cluster alphaBetaCluster;
	private Cluster betaGammaCluster;

	@BeforeEach
	void setup() {
		linkage = new SumOfIdsLinkage();
		mathUtils = new MathUtils();
		alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		gammaCluster = new Cluster(gamma);
		alphaBetaCluster = alphaCluster.merge(betaCluster);
		betaGammaCluster = gammaCluster.merge(betaCluster);
	}

	@Test
	void alphaAndBeta() throws Exception {
		BigDecimal evaluation = linkage.evaluate(alphaCluster, betaCluster);
		assertEquals(mathUtils.oneOver(alpha.getId(), beta.getId()), evaluation);
	}

	@Test
	void betaAndAlpha() throws Exception {
		BigDecimal evaluation = linkage.evaluate(betaCluster, alphaCluster);
		assertEquals(mathUtils.oneOver(alpha.getId(), beta.getId()), evaluation);
	}

	@Test
	void alphaBetaAndGamma() throws Exception {
		BigDecimal evaluation = linkage.evaluate(alphaBetaCluster, gammaCluster);
		assertEquals(mathUtils.oneOver(alpha.getId(), beta.getId(), gamma.getId()), evaluation);
	}

	@Test
	void gammaAndAlphaBeta() throws Exception {
		BigDecimal evaluation = linkage.evaluate(gammaCluster, alphaBetaCluster);
		assertEquals(mathUtils.oneOver(alpha.getId(), beta.getId(), gamma.getId()), evaluation);
	}

	@Test
	void alphaAndBetaGamma() throws Exception {
		BigDecimal evaluation = linkage.evaluate(alphaCluster, betaGammaCluster);
		assertEquals(mathUtils.oneOver(alpha.getId(), beta.getId(), gamma.getId()), evaluation);
	}

	@Test
	void betaGammaAndAlpha() throws Exception {
		BigDecimal evaluation = linkage.evaluate(betaGammaCluster, alphaCluster);
		assertEquals(mathUtils.oneOver(alpha.getId(), beta.getId(), gamma.getId()), evaluation);
	}

}
