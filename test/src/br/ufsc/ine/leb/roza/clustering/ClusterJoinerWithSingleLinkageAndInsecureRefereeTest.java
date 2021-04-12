package br.ufsc.ine.leb.roza.clustering;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.SimilarityReportBuilder;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.exceptions.TiebreakException;
import br.ufsc.ine.leb.roza.utils.CollectionUtils;

class ClusterJoinerWithSingleLinkageAndInsecureRefereeTest {

	private BigDecimal dotOne;
	private BigDecimal dotTwo;
	private BigDecimal dotThree;
	private BigDecimal dotFive;
	private BigDecimal dotSeven;
	private BigDecimal dotEight;
	private BigDecimal dotNine;
	private TestCase alpha;
	private TestCase beta;
	private TestCase gamma;
	private TestCase delta;
	private Cluster alphaCluster;
	private Cluster betaCluster;
	private Cluster gammaCluster;
	private Cluster deltaCluster;
	private Cluster alphaGammaCluster;
	private CollectionUtils collectionUtils;

	@BeforeEach
	void setup() {
		dotOne = new BigDecimal("0.1");
		dotTwo = new BigDecimal("0.2");
		dotThree = new BigDecimal("0.3");
		dotFive = new BigDecimal("0.5");
		dotSeven = new BigDecimal("0.7");
		dotEight = new BigDecimal("0.8");
		dotNine = new BigDecimal("0.9");
		alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		delta = new TestCase("delta", Arrays.asList(), Arrays.asList());
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		gammaCluster = new Cluster(gamma);
		deltaCluster = new Cluster(delta);
		alphaGammaCluster = alphaCluster.merge(gammaCluster);
		collectionUtils = new CollectionUtils();
	}

	@Test
	void linkSingleElementClusterWithNonSingleElementCluster() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(true);
		builder.add(alpha, beta, dotTwo).add(alpha, gamma, dotEight).add(alpha, delta, dotSeven);
		builder.add(beta, gamma, dotFive).add(beta, delta, dotFive);
		builder.add(gamma, delta, dotThree);
		SimilarityReport report = builder.build();
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaGammaCluster, betaCluster, deltaCluster));
		Combination combination = new ClusterJoiner(new SingleLinkage(report), new InsecureReferee()).join(clusters);
		assertEquals(combination, new Combination(alphaGammaCluster, deltaCluster));
		assertEquals(combination, new Combination(deltaCluster, alphaGammaCluster));
	}

	@Test
	void linkSingleElementClusters() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(true);
		builder.add(alpha, beta, dotTwo).add(alpha, gamma, dotEight).add(alpha, delta, dotOne);
		builder.add(beta, gamma, dotFive).add(beta, delta, dotSeven);
		builder.add(gamma, delta, dotThree);
		SimilarityReport report = builder.build();
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaGammaCluster, betaCluster, deltaCluster));
		Combination combination = new ClusterJoiner(new SingleLinkage(report), new InsecureReferee()).join(clusters);
		assertEquals(new Combination(betaCluster, deltaCluster), combination);
		assertEquals(new Combination(deltaCluster, betaCluster), combination);
	}

	@Test
	void nonSymmetricAlphaBeta() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(alpha, beta, dotSeven).add(alpha, gamma, dotEight);
		builder.add(beta, alpha, dotNine).add(beta, gamma, dotEight);
		builder.add(gamma, alpha, dotEight).add(gamma, beta, dotEight);
		SimilarityReport report = builder.build();
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaCluster, betaCluster, gammaCluster));
		Combination combination = new ClusterJoiner(new SingleLinkage(report), new InsecureReferee()).join(clusters);
		assertEquals(new Combination(betaCluster, alphaCluster), combination);
		assertEquals(new Combination(alphaCluster, betaCluster), combination);
	}

	@Test
	void nonSymmetricBetaAlpha() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(alpha, beta, dotNine).add(alpha, gamma, dotEight);
		builder.add(beta, alpha, dotSeven).add(beta, gamma, dotEight);
		builder.add(gamma, alpha, dotEight).add(gamma, beta, dotEight);
		SimilarityReport report = builder.build();
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaCluster, betaCluster, gammaCluster));
		Combination combination = new ClusterJoiner(new SingleLinkage(report), new InsecureReferee()).join(clusters);
		assertEquals(new Combination(betaCluster, alphaCluster), combination);
		assertEquals(new Combination(alphaCluster, betaCluster), combination);
	}

	@Test
	void multiplePossibilities() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(alpha, beta, dotFive).add(alpha, gamma, dotFive);
		builder.add(beta, alpha, dotFive).add(beta, gamma, dotNine);
		builder.add(gamma, alpha, dotNine).add(gamma, beta, dotFive);
		SimilarityReport report = builder.build();
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaCluster, betaCluster, gammaCluster));
		TiebreakException exception = assertThrows(TiebreakException.class, () -> {
			new ClusterJoiner(new SingleLinkage(report), new InsecureReferee()).join(clusters);
		});
		assertEquals(2, exception.getTies().size());
		assertTrue(exception.getTies().contains(new Combination(alphaCluster, gammaCluster)));
		assertTrue(exception.getTies().contains(new Combination(betaCluster, gammaCluster)));
	}

}
