package br.ufsc.ine.leb.roza.clustering;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;

public class DendogramCompleteLinkageTest {

	@Test
	void linkSingleElementClusters() {
		TestCase alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		TestCase delta = new TestCase("delta", Arrays.asList(), Arrays.asList());
		DendogramCluster clusterAlfa = new DendogramCluster(alfa);
		DendogramCluster clusterBeta = new DendogramCluster(beta);
		DendogramCluster clusterGamma = new DendogramCluster(gamma);
		DendogramCluster clusterDelta = new DendogramCluster(delta);
		DendogramCluster clusterAlfaGamma = clusterAlfa.merge(clusterGamma);
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
		DendogramLinkageMethod linkageMethod = new DendogramCompleteLinkageMethod();
		DendogramLinkage linkage = linkageMethod.link(Arrays.asList(clusterAlfaGamma, clusterBeta, clusterDelta), similarityReport);
		assertEquals(clusterBeta, linkage.getFirst());
		assertEquals(clusterDelta, linkage.getSecond());
	}

	@Test
	void linkSingleElementClusterWithNonSingleElementCluster() {
		TestCase alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		TestCase delta = new TestCase("delta", Arrays.asList(), Arrays.asList());
		DendogramCluster clusterAlfa = new DendogramCluster(alfa);
		DendogramCluster clusterBeta = new DendogramCluster(beta);
		DendogramCluster clusterGamma = new DendogramCluster(gamma);
		DendogramCluster clusterDelta = new DendogramCluster(delta);
		DendogramCluster clusterAlfaGamma = clusterAlfa.merge(clusterGamma);
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
		DendogramLinkageMethod linkageMethod = new DendogramCompleteLinkageMethod();
		DendogramLinkage linkage = linkageMethod.link(Arrays.asList(clusterAlfaGamma, clusterBeta, clusterDelta), similarityReport);
		assertEquals(clusterAlfaGamma, linkage.getFirst());
		assertEquals(clusterDelta, linkage.getSecond());
	}

	@Test
	void nonSimetric() {
		TestCase alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		DendogramCluster clusterAlfa = new DendogramCluster(alfa);
		DendogramCluster clusterBeta = new DendogramCluster(beta);
		DendogramCluster clusterGamma = new DendogramCluster(gamma);
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
		DendogramLinkageMethod linkageMethod = new DendogramCompleteLinkageMethod();
		DendogramLinkage linkage = linkageMethod.link(Arrays.asList(clusterAlfa, clusterBeta, clusterGamma), similarityReport);
		assertEquals(clusterBeta, linkage.getFirst());
		assertEquals(clusterAlfa, linkage.getSecond());
	}

	@Test
	void multiplePossibilities() {
		fail("Implement");
	}

}
