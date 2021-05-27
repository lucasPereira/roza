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

 class ClusterJoinerWithAverageLinkageTest {

	private BigDecimal dotTwo;
	private BigDecimal dotThree;
	private BigDecimal dotFour;
	private BigDecimal dotFive;
	private BigDecimal dotFiveFive;
	private BigDecimal dotSix;
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
	private InsecureReferee referee;

	@BeforeEach
	void setup() {
		dotTwo = new BigDecimal("0.2");
		dotThree = new BigDecimal("0.3");
		dotFour = new BigDecimal("0.4");
		dotFive = new BigDecimal("0.5");
		dotFiveFive = new BigDecimal("0.55");
		dotSix = new BigDecimal("0.6");
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
		referee = new InsecureReferee();
	}

	@Test
	void linkSingleElementClusters() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(true);
		builder.add(alpha, beta, dotTwo).add(alpha, gamma, dotEight).add(alpha, delta, dotSeven);
		builder.add(beta, gamma, dotFive).add(beta, delta, dotSix);
		builder.add(gamma, delta, dotThree);
		SimilarityReport report = builder.build();
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaGammaCluster, betaCluster, deltaCluster));
		WinnerCombination winner = new ClusterJoiner(new AverageLinkage(report), referee).join(clusters);
		Combination combination = winner.getCombination();
		assertEquals(new Combination(betaCluster, deltaCluster), combination);
		assertEquals(new Combination(deltaCluster, betaCluster), combination);
		assertEquals(dotSix, winner.getEvaluation());
	}

	@Test
	void linkSingleElementClusterWithNonSingleElementCluster() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(true);
		builder.add(alpha, beta, dotSeven).add(alpha, gamma, dotEight).add(alpha, delta, dotThree);
		builder.add(beta, gamma, dotFour).add(beta, delta, dotTwo);
		builder.add(gamma, delta, dotFive);
		SimilarityReport report = builder.build();
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaGammaCluster, betaCluster, deltaCluster));
		WinnerCombination winner = new ClusterJoiner(new AverageLinkage(report), referee).join(clusters);
		Combination combination = winner.getCombination();
		assertEquals(new Combination(alphaGammaCluster, betaCluster), combination);
		assertEquals(new Combination(betaCluster, alphaGammaCluster), combination);
		assertEquals(dotFiveFive, winner.getEvaluation());
	}

	@Test
	void nonSymmetricAlphaBeta() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(alpha, beta, dotFive).add(alpha, gamma, dotSix);
		builder.add(beta, alpha, dotSeven).add(beta, gamma, dotSix);
		builder.add(gamma, alpha, dotSix).add(gamma, beta, dotSix);
		SimilarityReport report = builder.build();
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaCluster, betaCluster, gammaCluster));
		WinnerCombination winner = new ClusterJoiner(new AverageLinkage(report), referee).join(clusters);
		Combination combination = winner.getCombination();
		assertEquals(new Combination(betaCluster, alphaCluster), combination);
		assertEquals(new Combination(alphaCluster, betaCluster), combination);
		assertEquals(dotSeven, winner.getEvaluation());
	}

	@Test
	void nonSymmetricBetaAlpha() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(alpha, beta, dotSeven).add(alpha, gamma, dotSix);
		builder.add(beta, alpha, dotFive).add(beta, gamma, dotSix);
		builder.add(gamma, alpha, dotSix).add(gamma, beta, dotSix);
		SimilarityReport report = builder.build();
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaCluster, betaCluster, gammaCluster));
		WinnerCombination winner = new ClusterJoiner(new AverageLinkage(report), referee).join(clusters);
		Combination combination = winner.getCombination();
		assertEquals(new Combination(betaCluster, alphaCluster), combination);
		assertEquals(new Combination(alphaCluster, betaCluster), combination);
		assertEquals(dotSeven, winner.getEvaluation());
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
			new ClusterJoiner(new AverageLinkage(report), referee).join(clusters);
		});
		assertEquals(2, exception.getTies().size());
		assertTrue(exception.getTies().contains(new Combination(alphaCluster, gammaCluster)));
		assertTrue(exception.getTies().contains(new Combination(betaCluster, gammaCluster)));
	}

}
