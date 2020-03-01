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

public class CompleteLinkageTest {

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
	void linkSingleElementClusters() {
		SimilarityAssessment assessmentAlfaAlfa = new SimilarityAssessment(alpha, alpha, BigDecimal.ONE);
		SimilarityAssessment assessmentAlfaBeta = new SimilarityAssessment(alpha, beta, new BigDecimal("0.2"));
		SimilarityAssessment assessmentAlfaGamma = new SimilarityAssessment(alpha, gamma, new BigDecimal("0.8"));
		SimilarityAssessment assessmentAlfaDelta = new SimilarityAssessment(alpha, delta, new BigDecimal("0.7"));
		SimilarityAssessment assessmentBetaAlfa = new SimilarityAssessment(beta, alpha, new BigDecimal("0.2"));
		SimilarityAssessment assessmentBetaBeta = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaGamma = new SimilarityAssessment(beta, gamma, new BigDecimal("0.5"));
		SimilarityAssessment assessmentBetaDelta = new SimilarityAssessment(beta, delta, new BigDecimal("0.5"));
		SimilarityAssessment assessmentGammaAlfa = new SimilarityAssessment(gamma, alpha, new BigDecimal("0.8"));
		SimilarityAssessment assessmentGammaBeta = new SimilarityAssessment(gamma, beta, new BigDecimal("0.5"));
		SimilarityAssessment assessmentGammaGamma = new SimilarityAssessment(gamma, gamma, BigDecimal.ONE);
		SimilarityAssessment assessmentGammaDelta = new SimilarityAssessment(gamma, delta, new BigDecimal("0.3"));
		SimilarityAssessment assessmentDeltaAlfa = new SimilarityAssessment(delta, alpha, new BigDecimal("0.7"));
		SimilarityAssessment assessmentDeltaBeta = new SimilarityAssessment(delta, beta, new BigDecimal("0.5"));
		SimilarityAssessment assessmentDeltaGamma = new SimilarityAssessment(delta, gamma, new BigDecimal("0.3"));
		SimilarityAssessment assessmentDeltaDelta = new SimilarityAssessment(delta, delta, BigDecimal.ONE);
		SimilarityReport similarityReport = new SimilarityReport(Arrays.asList(assessmentAlfaAlfa, assessmentAlfaBeta, assessmentAlfaGamma, assessmentAlfaDelta, assessmentBetaAlfa, assessmentBetaBeta, assessmentBetaGamma, assessmentBetaDelta, assessmentGammaAlfa, assessmentGammaBeta, assessmentGammaGamma, assessmentGammaDelta, assessmentDeltaAlfa, assessmentDeltaBeta, assessmentDeltaGamma, assessmentDeltaDelta));
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaGammaCluster, betaCluster, deltaCluster));
		Combination combination = new CompleteLinkage(similarityReport).link(clusters);
		assertEquals(new Combination(betaCluster, deltaCluster), combination);
		assertEquals(new Combination(deltaCluster, betaCluster), combination);
	}

	@Test
	void linkSingleElementClusterWithNonSingleElementCluster() {
		SimilarityAssessment assessmentAlfaAlfa = new SimilarityAssessment(alpha, alpha, BigDecimal.ONE);
		SimilarityAssessment assessmentAlfaBeta = new SimilarityAssessment(alpha, beta, new BigDecimal("0.2"));
		SimilarityAssessment assessmentAlfaGamma = new SimilarityAssessment(alpha, gamma, new BigDecimal("0.8"));
		SimilarityAssessment assessmentAlfaDelta = new SimilarityAssessment(alpha, delta, new BigDecimal("0.7"));
		SimilarityAssessment assessmentBetaAlfa = new SimilarityAssessment(beta, alpha, new BigDecimal("0.2"));
		SimilarityAssessment assessmentBetaBeta = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaGamma = new SimilarityAssessment(beta, gamma, new BigDecimal("0.5"));
		SimilarityAssessment assessmentBetaDelta = new SimilarityAssessment(beta, delta, new BigDecimal("0.1"));
		SimilarityAssessment assessmentGammaAlfa = new SimilarityAssessment(gamma, alpha, new BigDecimal("0.8"));
		SimilarityAssessment assessmentGammaBeta = new SimilarityAssessment(gamma, beta, new BigDecimal("0.5"));
		SimilarityAssessment assessmentGammaGamma = new SimilarityAssessment(gamma, gamma, BigDecimal.ONE);
		SimilarityAssessment assessmentGammaDelta = new SimilarityAssessment(gamma, delta, new BigDecimal("0.3"));
		SimilarityAssessment assessmentDeltaAlfa = new SimilarityAssessment(delta, alpha, new BigDecimal("0.7"));
		SimilarityAssessment assessmentDeltaBeta = new SimilarityAssessment(delta, beta, new BigDecimal("0.1"));
		SimilarityAssessment assessmentDeltaGamma = new SimilarityAssessment(delta, gamma, new BigDecimal("0.3"));
		SimilarityAssessment assessmentDeltaDelta = new SimilarityAssessment(delta, delta, BigDecimal.ONE);
		SimilarityReport similarityReport = new SimilarityReport(Arrays.asList(assessmentAlfaAlfa, assessmentAlfaBeta, assessmentAlfaGamma, assessmentAlfaDelta, assessmentBetaAlfa, assessmentBetaBeta, assessmentBetaGamma, assessmentBetaDelta, assessmentGammaAlfa, assessmentGammaBeta, assessmentGammaGamma, assessmentGammaDelta, assessmentDeltaAlfa, assessmentDeltaBeta, assessmentDeltaGamma, assessmentDeltaDelta));
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaGammaCluster, betaCluster, deltaCluster));
		Combination combination = new CompleteLinkage(similarityReport).link(clusters);
		assertEquals(new Combination(alphaGammaCluster, deltaCluster), combination);
		assertEquals(new Combination(deltaCluster, alphaGammaCluster), combination);
	}

	@Test
	void nonSimetricAlphaBeta() {
		SimilarityAssessment assessmentAlfaAlfa = new SimilarityAssessment(alpha, alpha, BigDecimal.ONE);
		SimilarityAssessment assessmentAlfaBeta = new SimilarityAssessment(alpha, beta, new BigDecimal("0.7"));
		SimilarityAssessment assessmentAlfaGamma = new SimilarityAssessment(alpha, gamma, new BigDecimal("0.6"));
		SimilarityAssessment assessmentBetaAlfa = new SimilarityAssessment(beta, alpha, new BigDecimal("0.9"));
		SimilarityAssessment assessmentBetaBeta = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaGamma = new SimilarityAssessment(beta, gamma, new BigDecimal("0.6"));
		SimilarityAssessment assessmentGammaAlfa = new SimilarityAssessment(gamma, alpha, new BigDecimal("0.6"));
		SimilarityAssessment assessmentGammaBeta = new SimilarityAssessment(gamma, beta, new BigDecimal("0.6"));
		SimilarityAssessment assessmentGammaGamma = new SimilarityAssessment(gamma, gamma, BigDecimal.ONE);
		SimilarityReport similarityReport = new SimilarityReport(Arrays.asList(assessmentAlfaAlfa, assessmentAlfaBeta, assessmentAlfaGamma, assessmentBetaAlfa, assessmentBetaBeta, assessmentBetaGamma, assessmentGammaAlfa, assessmentGammaBeta, assessmentGammaGamma));
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaCluster, betaCluster, gammaCluster));
		Combination combination = new CompleteLinkage(similarityReport).link(clusters);
		assertEquals(new Combination(betaCluster, alphaCluster), combination);
		assertEquals(new Combination(alphaCluster, betaCluster), combination);
	}

	@Test
	void nonSimetricBetaAlpha() {
		SimilarityAssessment assessmentAlfaAlfa = new SimilarityAssessment(alpha, alpha, BigDecimal.ONE);
		SimilarityAssessment assessmentAlfaBeta = new SimilarityAssessment(alpha, beta, new BigDecimal("0.9"));
		SimilarityAssessment assessmentAlfaGamma = new SimilarityAssessment(alpha, gamma, new BigDecimal("0.6"));
		SimilarityAssessment assessmentBetaAlfa = new SimilarityAssessment(beta, alpha, new BigDecimal("0.7"));
		SimilarityAssessment assessmentBetaBeta = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaGamma = new SimilarityAssessment(beta, gamma, new BigDecimal("0.6"));
		SimilarityAssessment assessmentGammaAlfa = new SimilarityAssessment(gamma, alpha, new BigDecimal("0.6"));
		SimilarityAssessment assessmentGammaBeta = new SimilarityAssessment(gamma, beta, new BigDecimal("0.6"));
		SimilarityAssessment assessmentGammaGamma = new SimilarityAssessment(gamma, gamma, BigDecimal.ONE);
		SimilarityReport similarityReport = new SimilarityReport(Arrays.asList(assessmentAlfaAlfa, assessmentAlfaBeta, assessmentAlfaGamma, assessmentBetaAlfa, assessmentBetaBeta, assessmentBetaGamma, assessmentGammaAlfa, assessmentGammaBeta, assessmentGammaGamma));
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaCluster, betaCluster, gammaCluster));
		Combination combination = new CompleteLinkage(similarityReport).link(clusters);
		assertEquals(new Combination(betaCluster, alphaCluster), combination);
		assertEquals(new Combination(alphaCluster, betaCluster), combination);
	}

	@Test
	void multiplePossibilities() {
		SimilarityAssessment assessmentAlfaAlfa = new SimilarityAssessment(alpha, alpha, BigDecimal.ONE);
		SimilarityAssessment assessmentAlfaBeta = new SimilarityAssessment(alpha, beta, new BigDecimal("0.5"));
		SimilarityAssessment assessmentAlfaGamma = new SimilarityAssessment(alpha, gamma, new BigDecimal("0.5"));
		SimilarityAssessment assessmentBetaAlfa = new SimilarityAssessment(beta, alpha, new BigDecimal("0.5"));
		SimilarityAssessment assessmentBetaBeta = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaGamma = new SimilarityAssessment(beta, gamma, new BigDecimal("0.5"));
		SimilarityAssessment assessmentGammaAlfa = new SimilarityAssessment(gamma, alpha, new BigDecimal("0.5"));
		SimilarityAssessment assessmentGammaBeta = new SimilarityAssessment(gamma, beta, new BigDecimal("0.5"));
		SimilarityAssessment assessmentGammaGamma = new SimilarityAssessment(gamma, gamma, BigDecimal.ONE);
		SimilarityReport similarityReport = new SimilarityReport(Arrays.asList(assessmentAlfaAlfa, assessmentAlfaBeta, assessmentAlfaGamma, assessmentBetaAlfa, assessmentBetaBeta, assessmentBetaGamma, assessmentGammaAlfa, assessmentGammaBeta, assessmentGammaGamma));
		ClustersToMerge clusters = new ClustersToMerge(collectionUtils.set(alphaCluster, betaCluster, gammaCluster));
		assertThrows(TiebreakException.class, () -> {
			new CompleteLinkage(similarityReport).link(clusters);
		});
	}

}
