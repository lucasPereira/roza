package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityReportBuilder;
import br.ufsc.ine.leb.roza.TestCase;

class SingleLinkageTest {

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
	private Cluster deltaCluster;
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
		alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		delta = new TestCase("delta", Arrays.asList(), Arrays.asList());
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		gammaCluster = new Cluster(gamma);
		deltaCluster = new Cluster(delta);
		alphaBetaCluster = alphaCluster.merge(betaCluster);
		gammaDeltaCluster = gammaCluster.merge(deltaCluster);
	}

	@Test
	void singleElementClustersWithSymmetricSimilarity() throws Exception {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(true);
		builder.add(alpha, beta, dotFive);
		Linkage linkage = new SingleLinkage(builder.build());
		assertEquals(dotFive, linkage.evaluate(alphaCluster, betaCluster));
	}

	@Test
	void singleAndNonSingleElementClustersWithSymmetricSimilarity() throws Exception {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(true);
		builder.add(alpha, beta, dotEight).add(alpha, gamma, dotTwo);
		builder.add(beta, gamma, dotSix);
		Linkage linkage = new SingleLinkage(builder.build());
		assertEquals(dotSix, linkage.evaluate(alphaBetaCluster, gammaCluster).stripTrailingZeros());
	}

	@Test
	void nonSingleElementClustersWithSymmetricSimilarity() throws Exception {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(true);
		builder.add(alpha, beta, dotEight).add(alpha, gamma, dotTwo).add(alpha, delta, dotFour);
		builder.add(beta, gamma, dotSix).add(beta, delta, dotSeven);
		builder.add(gamma, delta, dotEight);
		Linkage linkage = new SingleLinkage(builder.build());
		assertEquals(dotSeven, linkage.evaluate(alphaBetaCluster, gammaDeltaCluster).stripTrailingZeros());
	}

	@Test
	void singleElementClustersWithAsymmetricSymilarityWithTheSameValues() throws Exception {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(alpha, beta, dotFive);
		builder.add(beta, alpha, dotFive);
		Linkage linkage = new SingleLinkage(builder.build());
		assertEquals(dotFive, linkage.evaluate(alphaCluster, betaCluster));
	}

	@Test
	void singleElementClustersWithAsymmetricSymilarityWithTheDistinctValues() throws Exception {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(alpha, beta, dotTwo);
		builder.add(beta, alpha, dotEight);
		Linkage linkage = new SingleLinkage(builder.build());
		assertEquals(dotEight, linkage.evaluate(alphaCluster, betaCluster));
	}

	@Test
	void singleAndNonSingleElementClustersWithAsymmetricSimilarity() throws Exception {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(alpha, beta, dotEight).add(alpha, gamma, dotTwo);
		builder.add(beta, alpha, dotEight).add(beta, gamma, dotThree);
		builder.add(gamma, alpha, dotFour).add(gamma, beta, dotFive);
		Linkage linkage = new SingleLinkage(builder.build());
		assertEquals(dotFive, linkage.evaluate(alphaBetaCluster, gammaCluster).stripTrailingZeros());
	}

	@Test
	void nonSingleElementClustersWithAsymmetricSimilarity() throws Exception {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(alpha, beta, dotEight).add(alpha, gamma, dotTwo).add(alpha, delta, dotThree);
		builder.add(beta, alpha, dotEight).add(beta, gamma, dotFour).add(beta, delta, dotFive);
		builder.add(gamma, alpha, dotSix).add(gamma, beta, dotSeven).add(gamma, delta, dotEight);
		builder.add(delta, alpha, dotTwo).add(delta, beta, dotThree).add(delta, gamma, dotEight);
		Linkage linkage = new SingleLinkage(builder.build());
		assertEquals(dotSeven, linkage.evaluate(alphaBetaCluster, gammaDeltaCluster).stripTrailingZeros());
	}

}
