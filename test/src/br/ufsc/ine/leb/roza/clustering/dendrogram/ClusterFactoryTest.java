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

	private SimilarityReport report;
	private ClusterFactory factory;
	private Cluster alphaCluster;
	private Cluster betaCluster;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		SimilarityAssessment alphaAlphaAssessment = new SimilarityAssessment(alpha, alpha, BigDecimal.ONE);
		SimilarityAssessment alphaBetaAssessment = new SimilarityAssessment(alpha, beta, BigDecimal.ONE);
		SimilarityAssessment betaAlphaAssessment = new SimilarityAssessment(beta, alpha, BigDecimal.ONE);
		SimilarityAssessment betaBetaAssessment = new SimilarityAssessment(beta, beta, BigDecimal.ONE);
		report = new SimilarityReport(Arrays.asList(alphaAlphaAssessment, alphaBetaAssessment, betaAlphaAssessment, betaBetaAssessment));
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		factory = new ClusterFactory();
	}

	@Test
	void create() throws Exception {
		Set<Cluster> clusters = factory.create(report);
		assertEquals(2, clusters.size());
		assertTrue(clusters.contains(alphaCluster));
		assertTrue(clusters.contains(betaCluster));
	}

}