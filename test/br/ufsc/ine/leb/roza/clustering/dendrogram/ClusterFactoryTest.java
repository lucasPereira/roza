package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;

public class ClusterFactoryTest {

	private SimilarityReport similarityReport;
	private ClusterFactory factory;
	private Cluster alphaCluster;
	private Cluster betaCluster;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		SimilarityAssessment assessmentAlphaAlpha = new SimilarityAssessment(alpha, alpha, BigDecimal.ONE);
		SimilarityAssessment assessmentAlphaBeta = new SimilarityAssessment(alpha, beta, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaAlpha = new SimilarityAssessment(beta, alpha, BigDecimal.ONE);
		SimilarityAssessment assessmentBetaBeta = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		similarityReport = new SimilarityReport(Arrays.asList(assessmentAlphaAlpha, assessmentAlphaBeta, assessmentBetaAlpha, assessmentBetaBeta));
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		factory = new ClusterFactory();
	}

	@Test
	void create() throws Exception {
		Set<Cluster> clusters = factory.create(similarityReport);
		assertEquals(2, clusters.size());
		assertTrue(clusters.contains(alphaCluster));
		assertTrue(clusters.contains(betaCluster));
	}

}
