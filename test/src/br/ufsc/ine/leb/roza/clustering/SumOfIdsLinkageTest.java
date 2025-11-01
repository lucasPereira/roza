package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.utils.MathUtils;

class SumOfIdsLinkageTest {

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
		alpha = new TestCase("alpha", List.of(), List.of());
		beta = new TestCase("beta", List.of(), List.of());
		gamma = new TestCase("gamma", List.of(), List.of());
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		gammaCluster = new Cluster(gamma);
		alphaBetaCluster = alphaCluster.merge(betaCluster);
		betaGammaCluster = gammaCluster.merge(betaCluster);
	}

	@Test
	void alphaAndBeta() {
		BigDecimal evaluation = linkage.evaluate(alphaCluster, betaCluster);
		assertEquals(mathUtils.oneOver(alpha.getId(), beta.getId()), evaluation);
	}

	@Test
	void betaAndAlpha() {
		BigDecimal evaluation = linkage.evaluate(betaCluster, alphaCluster);
		assertEquals(mathUtils.oneOver(alpha.getId(), beta.getId()), evaluation);
	}

	@Test
	void alphaBetaAndGamma() {
		BigDecimal evaluation = linkage.evaluate(alphaBetaCluster, gammaCluster);
		assertEquals(mathUtils.oneOver(alpha.getId(), beta.getId(), gamma.getId()), evaluation);
	}

	@Test
	void gammaAndAlphaBeta() {
		BigDecimal evaluation = linkage.evaluate(gammaCluster, alphaBetaCluster);
		assertEquals(mathUtils.oneOver(alpha.getId(), beta.getId(), gamma.getId()), evaluation);
	}

	@Test
	void alphaAndBetaGamma() {
		BigDecimal evaluation = linkage.evaluate(alphaCluster, betaGammaCluster);
		assertEquals(mathUtils.oneOver(alpha.getId(), beta.getId(), gamma.getId()), evaluation);
	}

	@Test
	void betaGammaAndAlpha() {
		BigDecimal evaluation = linkage.evaluate(betaGammaCluster, alphaCluster);
		assertEquals(mathUtils.oneOver(alpha.getId(), beta.getId(), gamma.getId()), evaluation);
	}

}
