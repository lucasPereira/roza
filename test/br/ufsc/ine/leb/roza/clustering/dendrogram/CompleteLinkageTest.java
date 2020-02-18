package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.exceptions.TiebreakException;

public class CompleteLinkageTest {

	private Linkage linkage;

	@BeforeEach
	void setup() {
		linkage = new CompleteLinkage();
	}

	@Test
	void linkSingleElementClusters() {
		TestCase alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		TestCase delta = new TestCase("delta", Arrays.asList(), Arrays.asList());
		Cluster clusterAlfa = new Cluster(alfa);
		Cluster clusterBeta = new Cluster(beta);
		Cluster clusterGamma = new Cluster(gamma);
		Cluster clusterDelta = new Cluster(delta);
		Cluster clusterAlfaGamma = clusterAlfa.merge(clusterGamma);
		SimilarityAssessment assessmentAlfaAlfa = new SimilarityAssessment(alfa, alfa, BigDecimal.ONE);
		SimilarityAssessment assessmentAlfaBeta = new SimilarityAssessment(alfa, beta, new BigDecimal("0.2"));
		SimilarityAssessment assessmentAlfaGamma = new SimilarityAssessment(alfa, gamma, new BigDecimal("0.8"));
		SimilarityAssessment assessmentAlfaDelta = new SimilarityAssessment(alfa, delta, new BigDecimal("0.7"));
		SimilarityAssessment assessmentBetaAlfa = new SimilarityAssessment(beta, alfa, new BigDecimal("0.2"));
		SimilarityAssessment assessmentBetaBeta = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaGamma = new SimilarityAssessment(beta, gamma, new BigDecimal("0.5"));
		SimilarityAssessment assessmentBetaDelta = new SimilarityAssessment(beta, delta, new BigDecimal("0.5"));
		SimilarityAssessment assessmentGammaAlfa = new SimilarityAssessment(gamma, alfa, new BigDecimal("0.8"));
		SimilarityAssessment assessmentGammaBeta = new SimilarityAssessment(gamma, beta, new BigDecimal("0.5"));
		SimilarityAssessment assessmentGammaGamma = new SimilarityAssessment(gamma, gamma, BigDecimal.ONE);
		SimilarityAssessment assessmentGammaDelta = new SimilarityAssessment(gamma, delta, new BigDecimal("0.3"));
		SimilarityAssessment assessmentDeltaAlfa = new SimilarityAssessment(delta, alfa, new BigDecimal("0.7"));
		SimilarityAssessment assessmentDeltaBeta = new SimilarityAssessment(delta, beta, new BigDecimal("0.5"));
		SimilarityAssessment assessmentDeltaGamma = new SimilarityAssessment(delta, gamma, new BigDecimal("0.3"));
		SimilarityAssessment assessmentDeltaDelta = new SimilarityAssessment(delta, delta, BigDecimal.ONE);
		SimilarityReport similarityReport = new SimilarityReport(Arrays.asList(assessmentAlfaAlfa, assessmentAlfaBeta, assessmentAlfaGamma, assessmentAlfaDelta, assessmentBetaAlfa, assessmentBetaBeta, assessmentBetaGamma, assessmentBetaDelta, assessmentGammaAlfa, assessmentGammaBeta, assessmentGammaGamma, assessmentGammaDelta, assessmentDeltaAlfa, assessmentDeltaBeta, assessmentDeltaGamma, assessmentDeltaDelta));
		ClustersToMerge clusters = new ClustersToMerge(Arrays.asList(clusterAlfaGamma, clusterBeta, clusterDelta));
		Link link = linkage.link(clusters, similarityReport);
		assertEquals(clusterBeta, link.getFirst());
		assertEquals(clusterDelta, link.getSecond());
	}

	@Test
	void linkSingleElementClusterWithNonSingleElementCluster() {
		TestCase alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		TestCase delta = new TestCase("delta", Arrays.asList(), Arrays.asList());
		Cluster clusterAlfa = new Cluster(alfa);
		Cluster clusterBeta = new Cluster(beta);
		Cluster clusterGamma = new Cluster(gamma);
		Cluster clusterDelta = new Cluster(delta);
		Cluster clusterAlfaGamma = clusterAlfa.merge(clusterGamma);
		SimilarityAssessment assessmentAlfaAlfa = new SimilarityAssessment(alfa, alfa, BigDecimal.ONE);
		SimilarityAssessment assessmentAlfaBeta = new SimilarityAssessment(alfa, beta, new BigDecimal("0.2"));
		SimilarityAssessment assessmentAlfaGamma = new SimilarityAssessment(alfa, gamma, new BigDecimal("0.8"));
		SimilarityAssessment assessmentAlfaDelta = new SimilarityAssessment(alfa, delta, new BigDecimal("0.7"));
		SimilarityAssessment assessmentBetaAlfa = new SimilarityAssessment(beta, alfa, new BigDecimal("0.2"));
		SimilarityAssessment assessmentBetaBeta = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaGamma = new SimilarityAssessment(beta, gamma, new BigDecimal("0.5"));
		SimilarityAssessment assessmentBetaDelta = new SimilarityAssessment(beta, delta, new BigDecimal("0.1"));
		SimilarityAssessment assessmentGammaAlfa = new SimilarityAssessment(gamma, alfa, new BigDecimal("0.8"));
		SimilarityAssessment assessmentGammaBeta = new SimilarityAssessment(gamma, beta, new BigDecimal("0.5"));
		SimilarityAssessment assessmentGammaGamma = new SimilarityAssessment(gamma, gamma, BigDecimal.ONE);
		SimilarityAssessment assessmentGammaDelta = new SimilarityAssessment(gamma, delta, new BigDecimal("0.3"));
		SimilarityAssessment assessmentDeltaAlfa = new SimilarityAssessment(delta, alfa, new BigDecimal("0.7"));
		SimilarityAssessment assessmentDeltaBeta = new SimilarityAssessment(delta, beta, new BigDecimal("0.1"));
		SimilarityAssessment assessmentDeltaGamma = new SimilarityAssessment(delta, gamma, new BigDecimal("0.3"));
		SimilarityAssessment assessmentDeltaDelta = new SimilarityAssessment(delta, delta, BigDecimal.ONE);
		SimilarityReport similarityReport = new SimilarityReport(Arrays.asList(assessmentAlfaAlfa, assessmentAlfaBeta, assessmentAlfaGamma, assessmentAlfaDelta, assessmentBetaAlfa, assessmentBetaBeta, assessmentBetaGamma, assessmentBetaDelta, assessmentGammaAlfa, assessmentGammaBeta, assessmentGammaGamma, assessmentGammaDelta, assessmentDeltaAlfa, assessmentDeltaBeta, assessmentDeltaGamma, assessmentDeltaDelta));
		ClustersToMerge clusters = new ClustersToMerge(Arrays.asList(clusterAlfaGamma, clusterBeta, clusterDelta));
		Link link = linkage.link(clusters, similarityReport);
		assertEquals(clusterAlfaGamma, link.getFirst());
		assertEquals(clusterDelta, link.getSecond());
	}

	@Test
	void nonSimetric() {
		TestCase alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		Cluster clusterAlfa = new Cluster(alfa);
		Cluster clusterBeta = new Cluster(beta);
		Cluster clusterGamma = new Cluster(gamma);
		SimilarityAssessment assessmentAlfaAlfa = new SimilarityAssessment(alfa, alfa, BigDecimal.ONE);
		SimilarityAssessment assessmentAlfaBeta = new SimilarityAssessment(alfa, beta, new BigDecimal("0.7"));
		SimilarityAssessment assessmentAlfaGamma = new SimilarityAssessment(alfa, gamma, new BigDecimal("0.8"));
		SimilarityAssessment assessmentBetaAlfa = new SimilarityAssessment(beta, alfa, new BigDecimal("0.9"));
		SimilarityAssessment assessmentBetaBeta = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaGamma = new SimilarityAssessment(beta, gamma, new BigDecimal("0.8"));
		SimilarityAssessment assessmentGammaAlfa = new SimilarityAssessment(gamma, alfa, new BigDecimal("0.8"));
		SimilarityAssessment assessmentGammaBeta = new SimilarityAssessment(gamma, beta, new BigDecimal("0.8"));
		SimilarityAssessment assessmentGammaGamma = new SimilarityAssessment(gamma, gamma, BigDecimal.ONE);
		SimilarityReport similarityReport = new SimilarityReport(Arrays.asList(assessmentAlfaAlfa, assessmentAlfaBeta, assessmentAlfaGamma, assessmentBetaAlfa, assessmentBetaBeta, assessmentBetaGamma, assessmentGammaAlfa, assessmentGammaBeta, assessmentGammaGamma));
		ClustersToMerge clusters = new ClustersToMerge(Arrays.asList(clusterAlfa, clusterBeta, clusterGamma));
		Link link = linkage.link(clusters, similarityReport);
		assertEquals(clusterBeta, link.getFirst());
		assertEquals(clusterAlfa, link.getSecond());
	}

	@Test
	void nonSimetricReversed() {
		TestCase alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		Cluster clusterAlfa = new Cluster(alfa);
		Cluster clusterBeta = new Cluster(beta);
		Cluster clusterGamma = new Cluster(gamma);
		SimilarityAssessment assessmentAlfaAlfa = new SimilarityAssessment(alfa, alfa, BigDecimal.ONE);
		SimilarityAssessment assessmentAlfaBeta = new SimilarityAssessment(alfa, beta, new BigDecimal("0.7"));
		SimilarityAssessment assessmentAlfaGamma = new SimilarityAssessment(alfa, gamma, new BigDecimal("0.8"));
		SimilarityAssessment assessmentBetaAlfa = new SimilarityAssessment(beta, alfa, new BigDecimal("0.9"));
		SimilarityAssessment assessmentBetaBeta = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaGamma = new SimilarityAssessment(beta, gamma, new BigDecimal("0.8"));
		SimilarityAssessment assessmentGammaAlfa = new SimilarityAssessment(gamma, alfa, new BigDecimal("0.8"));
		SimilarityAssessment assessmentGammaBeta = new SimilarityAssessment(gamma, beta, new BigDecimal("0.8"));
		SimilarityAssessment assessmentGammaGamma = new SimilarityAssessment(gamma, gamma, BigDecimal.ONE);
		SimilarityReport similarityReport = new SimilarityReport(Arrays.asList(assessmentAlfaAlfa, assessmentAlfaBeta, assessmentAlfaGamma, assessmentBetaAlfa, assessmentBetaBeta, assessmentBetaGamma, assessmentGammaAlfa, assessmentGammaBeta, assessmentGammaGamma));
		ClustersToMerge clusters = new ClustersToMerge(Arrays.asList(clusterGamma, clusterBeta, clusterAlfa));
		Link link = linkage.link(clusters, similarityReport);
		assertEquals(clusterBeta, link.getFirst());
		assertEquals(clusterAlfa, link.getSecond());
	}

	@Test
	void multiplePossibilities() {
		TestCase alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		Cluster clusterAlfa = new Cluster(alfa);
		Cluster clusterBeta = new Cluster(beta);
		Cluster clusterGamma = new Cluster(gamma);
		SimilarityAssessment assessmentAlfaAlfa = new SimilarityAssessment(alfa, alfa, BigDecimal.ONE);
		SimilarityAssessment assessmentAlfaBeta = new SimilarityAssessment(alfa, beta, new BigDecimal("0.5"));
		SimilarityAssessment assessmentAlfaGamma = new SimilarityAssessment(alfa, gamma, new BigDecimal("0.5"));
		SimilarityAssessment assessmentBetaAlfa = new SimilarityAssessment(beta, alfa, new BigDecimal("0.5"));
		SimilarityAssessment assessmentBetaBeta = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaGamma = new SimilarityAssessment(beta, gamma, new BigDecimal("0.5"));
		SimilarityAssessment assessmentGammaAlfa = new SimilarityAssessment(gamma, alfa, new BigDecimal("0.5"));
		SimilarityAssessment assessmentGammaBeta = new SimilarityAssessment(gamma, beta, new BigDecimal("0.5"));
		SimilarityAssessment assessmentGammaGamma = new SimilarityAssessment(gamma, gamma, BigDecimal.ONE);
		SimilarityReport similarityReport = new SimilarityReport(Arrays.asList(assessmentAlfaAlfa, assessmentAlfaBeta, assessmentAlfaGamma, assessmentBetaAlfa, assessmentBetaBeta, assessmentBetaGamma, assessmentGammaAlfa, assessmentGammaBeta, assessmentGammaGamma));
		ClustersToMerge clusters = new ClustersToMerge(Arrays.asList(clusterAlfa, clusterBeta, clusterGamma));
		assertThrows(TiebreakException.class, () -> {
			linkage.link(clusters, similarityReport);
		});
	}

}
