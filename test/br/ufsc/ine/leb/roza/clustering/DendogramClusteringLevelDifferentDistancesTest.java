package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;

public class DendogramClusteringLevelDifferentDistancesTest {

	private TestCase alfa;
	private TestCase beta;
	private TestCase gamma;
	private DendogramCluster clusterAlfa;
	private DendogramCluster clusterBeta;
	private DendogramCluster clusterGamma;

	@BeforeEach
	void setup() {
		alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		clusterAlfa = new DendogramCluster(alfa);
		clusterBeta = new DendogramCluster(beta);
		clusterGamma = new DendogramCluster(gamma);
	}

	@Test
	void threeDifferentSimetricDistancesSingleLinkage() throws Exception {
		SimilarityAssessment assessmentAlfaAlfa = new SimilarityAssessment(alfa, alfa, BigDecimal.ONE);
		SimilarityAssessment assessmentAlfaBeta = new SimilarityAssessment(alfa, beta, new BigDecimal("0.2"));
		SimilarityAssessment assessmentAlfaGamma = new SimilarityAssessment(alfa, gamma, new BigDecimal("0.8"));
		SimilarityAssessment assessmentBetaAlfa = new SimilarityAssessment(beta, alfa, new BigDecimal("0.2"));
		SimilarityAssessment assessmentBetaBeta = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaGamma = new SimilarityAssessment(beta, gamma, new BigDecimal("0.5"));
		SimilarityAssessment assessmentGammaAlfa = new SimilarityAssessment(gamma, alfa, new BigDecimal("0.8"));
		SimilarityAssessment assessmentGammaBeta = new SimilarityAssessment(gamma, beta, new BigDecimal("0.5"));
		SimilarityAssessment assessmentGammaGamma = new SimilarityAssessment(gamma, gamma, BigDecimal.ONE);
		SimilarityReport similarityReport = new SimilarityReport(Arrays.asList(assessmentAlfaAlfa, assessmentAlfaBeta, assessmentAlfaGamma, assessmentBetaAlfa, assessmentBetaBeta, assessmentBetaGamma, assessmentGammaAlfa, assessmentGammaBeta, assessmentGammaGamma));
		DendogramClusteringLevel one = new DendogramClusteringLevel(new DendogramSingleLinkageMethod(), Arrays.asList(clusterAlfa, clusterBeta, clusterGamma), similarityReport);
		DendogramClusteringLevel two = one.generateNextLevel();

		assertEquals(2, two.getClusters().size());
		assertEquals(2, two.getClusters().get(0).getElements().size());
		assertEquals(alfa, two.getClusters().get(0).getElements().get(0));
		assertEquals(gamma, two.getClusters().get(0).getElements().get(1));
		assertEquals(1, two.getClusters().get(1).getElements().size());
		assertEquals(beta, two.getClusters().get(1).getElements().get(0));
	}

	@Test
	void threeDifferentSimetricDistancesCompleteLinkage() throws Exception {
		SimilarityAssessment assessmentAlfaAlfa = new SimilarityAssessment(alfa, alfa, BigDecimal.ONE);
		SimilarityAssessment assessmentAlfaBeta = new SimilarityAssessment(alfa, beta, new BigDecimal("0.2"));
		SimilarityAssessment assessmentAlfaGamma = new SimilarityAssessment(alfa, gamma, new BigDecimal("0.8"));
		SimilarityAssessment assessmentBetaAlfa = new SimilarityAssessment(beta, alfa, new BigDecimal("0.2"));
		SimilarityAssessment assessmentBetaBeta = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaGamma = new SimilarityAssessment(beta, gamma, new BigDecimal("0.5"));
		SimilarityAssessment assessmentGammaAlfa = new SimilarityAssessment(gamma, alfa, new BigDecimal("0.8"));
		SimilarityAssessment assessmentGammaBeta = new SimilarityAssessment(gamma, beta, new BigDecimal("0.5"));
		SimilarityAssessment assessmentGammaGamma = new SimilarityAssessment(gamma, gamma, BigDecimal.ONE);
		SimilarityReport similarityReport = new SimilarityReport(Arrays.asList(assessmentAlfaAlfa, assessmentAlfaBeta, assessmentAlfaGamma, assessmentBetaAlfa, assessmentBetaBeta, assessmentBetaGamma, assessmentGammaAlfa, assessmentGammaBeta, assessmentGammaGamma));
		DendogramClusteringLevel one = new DendogramClusteringLevel(new DendogramCompleteLinkageMethod(), Arrays.asList(clusterAlfa, clusterBeta, clusterGamma), similarityReport);
		DendogramClusteringLevel two = one.generateNextLevel();

		assertEquals(2, two.getClusters().size());
		assertEquals(2, two.getClusters().get(0).getElements().size());
		assertEquals(alfa, two.getClusters().get(0).getElements().get(0));
		assertEquals(gamma, two.getClusters().get(0).getElements().get(1));
		assertEquals(1, two.getClusters().get(1).getElements().size());
		assertEquals(beta, two.getClusters().get(1).getElements().get(0));
	}

}
