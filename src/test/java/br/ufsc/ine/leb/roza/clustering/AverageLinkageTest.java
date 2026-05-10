package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityReportBuilder;
import br.ufsc.ine.leb.roza.TestCase;

class AverageLinkageTest {

	private BigDecimal dotTwo;
	private BigDecimal dotThree;
	private BigDecimal dotFour;
	private BigDecimal dotFive;
	private BigDecimal dotSix;
	private BigDecimal dotSeven;
	private BigDecimal dotEight;
	private TestCase alpha;
	private TestCase beta;
	private TestCase gamma;
	private TestCase delta;
	private Cluster alphaCluster;
	private Cluster betaCluster;
	private Cluster gammaCluster;
	private Cluster alphaBetaCluster;
	private Cluster gammaDeltaCluster;

	@BeforeEach
	void setup() {
		dotTwo = new BigDecimal("0.2");
		dotThree = new BigDecimal("0.3");
		dotFour = new BigDecimal("0.4");
		dotFive = new BigDecimal("0.5");
		dotSix = new BigDecimal("0.6");
		dotSeven = new BigDecimal("0.7");
		dotEight = new BigDecimal("0.8");
		alpha = new TestCase("alpha", List.of(), List.of());
		beta = new TestCase("beta", List.of(), List.of());
		gamma = new TestCase("gamma", List.of(), List.of());
		delta = new TestCase("delta", List.of(), List.of());
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		gammaCluster = new Cluster(gamma);
		Cluster deltaCluster = new Cluster(delta);
		alphaBetaCluster = alphaCluster.merge(betaCluster);
		gammaDeltaCluster = gammaCluster.merge(deltaCluster);
	}

	@Test
	void singleElementClustersWithSymmetricSimilarity() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(true);
		builder.add(alpha, beta, dotFive);
		Linkage linkage = new AverageLinkage(builder.build());
		assertEquals(dotFive, linkage.evaluate(alphaCluster, betaCluster));
	}

	@Test
	void singleAndNonSingleElementClustersWithSymmetricSimilarity() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(true);
		builder.add(alpha, beta, dotEight).add(alpha, gamma, dotTwo);
		builder.add(beta, gamma, dotSix);
		Linkage linkage = new AverageLinkage(builder.build());
		assertEquals(dotFour, linkage.evaluate(alphaBetaCluster, gammaCluster).stripTrailingZeros());
	}

	@Test
	void nonSingleElementClustersWithSymmetricSimilarity() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(true);
		builder.add(alpha, beta, dotEight).add(alpha, gamma, dotTwo).add(alpha, delta, dotFour);
		builder.add(beta, gamma, dotSix).add(beta, delta, dotEight);
		builder.add(gamma, delta, dotSeven);
		Linkage linkage = new AverageLinkage(builder.build());
		assertEquals(dotFive, linkage.evaluate(alphaBetaCluster, gammaDeltaCluster).stripTrailingZeros());
	}

	@Test
	void singleElementClustersWithAsymmetricSimilarityWithTheSameValues() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(alpha, beta, dotFive);
		builder.add(beta, alpha, dotFive);
		Linkage linkage = new AverageLinkage(builder.build());
		assertEquals(dotFive, linkage.evaluate(alphaCluster, betaCluster));
	}

	@Test
	void singleElementClustersWithAsymmetricSimilarityWithTheDistinctValues() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(alpha, beta, dotTwo);
		builder.add(beta, alpha, dotEight);
		Linkage linkage = new AverageLinkage(builder.build());
		assertEquals(dotEight, linkage.evaluate(alphaCluster, betaCluster));
	}

	@Test
	void singleAndNonSingleElementClustersWithAsymmetricSimilarity() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(alpha, beta, dotEight).add(alpha, gamma, dotTwo);
		builder.add(beta, alpha, dotEight).add(beta, gamma, dotSix);
		builder.add(gamma, alpha, dotFour).add(gamma, beta, dotTwo);
		Linkage linkage = new AverageLinkage(builder.build());
		assertEquals(dotFive, linkage.evaluate(alphaBetaCluster, gammaCluster).stripTrailingZeros());
	}

	@Test
	void nonSingleElementClustersWithAsymmetricSimilarity() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(alpha, beta, dotEight).add(alpha, gamma, dotTwo).add(alpha, delta, dotThree);
		builder.add(beta, alpha, dotEight).add(beta, gamma, dotSeven).add(beta, delta, dotTwo);
		builder.add(gamma, alpha, dotFour).add(gamma, beta, dotTwo).add(gamma, delta, dotEight);
		builder.add(delta, alpha, dotTwo).add(delta, beta, dotSix).add(delta, gamma, dotEight);
		Linkage linkage = new AverageLinkage(builder.build());
		assertEquals(dotFive, linkage.evaluate(alphaBetaCluster, gammaDeltaCluster).stripTrailingZeros());
	}

}
