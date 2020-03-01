package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.exceptions.TiebreakException;
import br.ufsc.ine.leb.roza.utils.CollectionUtils;

public class ClusterJoinerWithSingleLinkageInsecureRefereeTest {

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
		SimilarityAssessment alphaAlphaAssessment = new SimilarityAssessment(alpha, alpha, BigDecimal.ONE);
		SimilarityAssessment alphaBetaAssessment = new SimilarityAssessment(alpha, beta, new BigDecimal("0.2"));
		SimilarityAssessment alphaGammaAssessment = new SimilarityAssessment(alpha, gamma, new BigDecimal("0.8"));
		SimilarityAssessment alphaDeltaAssessment = new SimilarityAssessment(alpha, delta, new BigDecimal("0.7"));
		SimilarityAssessment betaAlphaAssessment = new SimilarityAssessment(beta, alpha, new BigDecimal("0.2"));
		SimilarityAssessment betaBetaAssessment = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment betaGammaAssessment = new SimilarityAssessment(beta, gamma, new BigDecimal("0.5"));
		SimilarityAssessment betaDeltaAssessment = new SimilarityAssessment(beta, delta, new BigDecimal("0.5"));
		SimilarityAssessment gammaAlphaAssessment = new SimilarityAssessment(gamma, alpha, new BigDecimal("0.8"));
		SimilarityAssessment gammaBetaAssessment = new SimilarityAssessment(gamma, beta, new BigDecimal("0.5"));
		SimilarityAssessment gammaGammaAssessment = new SimilarityAssessment(gamma, gamma, BigDecimal.ONE);
		SimilarityAssessment gammaDeltaAssessment = new SimilarityAssessment(gamma, delta, new BigDecimal("0.3"));
		SimilarityAssessment deltaAlphaAssessment = new SimilarityAssessment(delta, alpha, new BigDecimal("0.7"));
		SimilarityAssessment deltaBetaAssessment = new SimilarityAssessment(delta, beta, new BigDecimal("0.5"));
		SimilarityAssessment deltaGammaAssessment = new SimilarityAssessment(delta, gamma, new BigDecimal("0.3"));
		SimilarityAssessment deltaDeltaAssessment = new SimilarityAssessment(delta, delta, BigDecimal.ONE);
		SimilarityReport report = new SimilarityReport(Arrays.asList(alphaAlphaAssessment, alphaBetaAssessment, alphaGammaAssessment, alphaDeltaAssessment, betaAlphaAssessment, betaBetaAssessment, betaGammaAssessment, betaDeltaAssessment, gammaAlphaAssessment, gammaBetaAssessment, gammaGammaAssessment, gammaDeltaAssessment, deltaAlphaAssessment, deltaBetaAssessment, deltaGammaAssessment, deltaDeltaAssessment));
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaGammaCluster, betaCluster, deltaCluster));
		Combination combination = new ClusterJoiner(new SingleLinkage(report), new InsecureReferee()).join(clusters);
		assertEquals(combination, new Combination(alphaGammaCluster, deltaCluster));
		assertEquals(combination, new Combination(deltaCluster, alphaGammaCluster));
	}

	@Test
	void linkSingleElementClusters() {
		SimilarityAssessment alphaAlphaAssessment = new SimilarityAssessment(alpha, alpha, BigDecimal.ONE);
		SimilarityAssessment alphaBetaAssessment = new SimilarityAssessment(alpha, beta, new BigDecimal("0.2"));
		SimilarityAssessment alphaGammaAssessment = new SimilarityAssessment(alpha, gamma, new BigDecimal("0.8"));
		SimilarityAssessment alphaDeltaAssessment = new SimilarityAssessment(alpha, delta, new BigDecimal("0.1"));
		SimilarityAssessment betaAlphaAssessment = new SimilarityAssessment(beta, alpha, new BigDecimal("0.2"));
		SimilarityAssessment betaBetaAssessment = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment betaGammaAssessment = new SimilarityAssessment(beta, gamma, new BigDecimal("0.5"));
		SimilarityAssessment betaDeltaAssessment = new SimilarityAssessment(beta, delta, new BigDecimal("0.7"));
		SimilarityAssessment gammaAlphaAssessment = new SimilarityAssessment(gamma, alpha, new BigDecimal("0.8"));
		SimilarityAssessment gammaBetaAssessment = new SimilarityAssessment(gamma, beta, new BigDecimal("0.5"));
		SimilarityAssessment gammaGammaAssessment = new SimilarityAssessment(gamma, gamma, BigDecimal.ONE);
		SimilarityAssessment gammaDeltaAssessment = new SimilarityAssessment(gamma, delta, new BigDecimal("0.3"));
		SimilarityAssessment deltaAlphaAssessment = new SimilarityAssessment(delta, alpha, new BigDecimal("0.1"));
		SimilarityAssessment deltaBetaAssessment = new SimilarityAssessment(delta, beta, new BigDecimal("0.7"));
		SimilarityAssessment deltaGammaAssessment = new SimilarityAssessment(delta, gamma, new BigDecimal("0.3"));
		SimilarityAssessment deltaDeltaAssessment = new SimilarityAssessment(delta, delta, BigDecimal.ONE);
		SimilarityReport report = new SimilarityReport(Arrays.asList(alphaAlphaAssessment, alphaBetaAssessment, alphaGammaAssessment, alphaDeltaAssessment, betaAlphaAssessment, betaBetaAssessment, betaGammaAssessment, betaDeltaAssessment, gammaAlphaAssessment, gammaBetaAssessment, gammaGammaAssessment, gammaDeltaAssessment, deltaAlphaAssessment, deltaBetaAssessment, deltaGammaAssessment, deltaDeltaAssessment));
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaGammaCluster, betaCluster, deltaCluster));
		Combination combination = new ClusterJoiner(new SingleLinkage(report), new InsecureReferee()).join(clusters);
		assertEquals(new Combination(betaCluster, deltaCluster), combination);
		assertEquals(new Combination(deltaCluster, betaCluster), combination);
	}

	@Test
	void nonSimetricAlphaaBeta() {
		SimilarityAssessment alphaAlphaAssessment = new SimilarityAssessment(alpha, alpha, BigDecimal.ONE);
		SimilarityAssessment alphaBetaAssessment = new SimilarityAssessment(alpha, beta, new BigDecimal("0.7"));
		SimilarityAssessment alphaGammaAssessment = new SimilarityAssessment(alpha, gamma, new BigDecimal("0.8"));
		SimilarityAssessment betaAlphaAssessment = new SimilarityAssessment(beta, alpha, new BigDecimal("0.9"));
		SimilarityAssessment betaBetaAssessment = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment betaGammaAssessment = new SimilarityAssessment(beta, gamma, new BigDecimal("0.8"));
		SimilarityAssessment gammaAlphaAssessment = new SimilarityAssessment(gamma, alpha, new BigDecimal("0.8"));
		SimilarityAssessment gammaBetaAssessment = new SimilarityAssessment(gamma, beta, new BigDecimal("0.8"));
		SimilarityAssessment gammaGammaAssessment = new SimilarityAssessment(gamma, gamma, BigDecimal.ONE);
		SimilarityReport report = new SimilarityReport(Arrays.asList(alphaAlphaAssessment, alphaBetaAssessment, alphaGammaAssessment, betaAlphaAssessment, betaBetaAssessment, betaGammaAssessment, gammaAlphaAssessment, gammaBetaAssessment, gammaGammaAssessment));
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaCluster, betaCluster, gammaCluster));
		Combination combination = new ClusterJoiner(new SingleLinkage(report), new InsecureReferee()).join(clusters);
		assertEquals(new Combination(betaCluster, alphaCluster), combination);
		assertEquals(new Combination(alphaCluster, betaCluster), combination);
	}

	@Test
	void nonSimetricBetaAlpha() {
		SimilarityAssessment alphaAlphaAssessment = new SimilarityAssessment(alpha, alpha, BigDecimal.ONE);
		SimilarityAssessment alphaBetaAssessment = new SimilarityAssessment(alpha, beta, new BigDecimal("0.9"));
		SimilarityAssessment alphaGammaAssessment = new SimilarityAssessment(alpha, gamma, new BigDecimal("0.8"));
		SimilarityAssessment betaAlphaAssessment = new SimilarityAssessment(beta, alpha, new BigDecimal("0.7"));
		SimilarityAssessment betaBetaAssessment = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment betaGammaAssessment = new SimilarityAssessment(beta, gamma, new BigDecimal("0.8"));
		SimilarityAssessment gammaAlphaAssessment = new SimilarityAssessment(gamma, alpha, new BigDecimal("0.8"));
		SimilarityAssessment gammaBetaAssessment = new SimilarityAssessment(gamma, beta, new BigDecimal("0.8"));
		SimilarityAssessment gammaGammaAssessment = new SimilarityAssessment(gamma, gamma, BigDecimal.ONE);
		SimilarityReport report = new SimilarityReport(Arrays.asList(alphaAlphaAssessment, alphaBetaAssessment, alphaGammaAssessment, betaAlphaAssessment, betaBetaAssessment, betaGammaAssessment, gammaAlphaAssessment, gammaBetaAssessment, gammaGammaAssessment));
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaCluster, betaCluster, gammaCluster));
		Combination combination = new ClusterJoiner(new SingleLinkage(report), new InsecureReferee()).join(clusters);
		assertEquals(new Combination(betaCluster, alphaCluster), combination);
		assertEquals(new Combination(alphaCluster, betaCluster), combination);
	}

	@Test
	void multiplePossibilities() {
		SimilarityAssessment alphaAlphaAssessment = new SimilarityAssessment(alpha, alpha, BigDecimal.ONE);
		SimilarityAssessment alphaBetaAssessment = new SimilarityAssessment(alpha, beta, new BigDecimal("0.5"));
		SimilarityAssessment alphaGammaAssessment = new SimilarityAssessment(alpha, gamma, new BigDecimal("0.5"));
		SimilarityAssessment betaAlphaAssessment = new SimilarityAssessment(beta, alpha, new BigDecimal("0.5"));
		SimilarityAssessment betaBetaAssessment = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment betaGammaAssessment = new SimilarityAssessment(beta, gamma, new BigDecimal("0.9"));
		SimilarityAssessment gammaAlphaAssessment = new SimilarityAssessment(gamma, alpha, new BigDecimal("0.9"));
		SimilarityAssessment gammaBetaAssessment = new SimilarityAssessment(gamma, beta, new BigDecimal("0.5"));
		SimilarityAssessment gammaGammaAssessment = new SimilarityAssessment(gamma, gamma, BigDecimal.ONE);
		SimilarityReport report = new SimilarityReport(Arrays.asList(alphaAlphaAssessment, alphaBetaAssessment, alphaGammaAssessment, betaAlphaAssessment, betaBetaAssessment, betaGammaAssessment, gammaAlphaAssessment, gammaBetaAssessment, gammaGammaAssessment));
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaCluster, betaCluster, gammaCluster));
		assertThrows(TiebreakException.class, () -> {
			new ClusterJoiner(new SingleLinkage(report), new InsecureReferee()).join(clusters);
		});
	}

}
