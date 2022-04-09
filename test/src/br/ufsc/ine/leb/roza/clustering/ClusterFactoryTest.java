package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.extraction.TestCase;
import br.ufsc.ine.leb.roza.measurement.SimilarityReport;
import br.ufsc.ine.leb.roza.measurement.SimilarityReportBuilder;

class ClusterFactoryTest {

	private SimilarityReport report;
	private ClusterFactory factory;
	private Cluster alphaCluster;
	private Cluster betaCluster;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		report = new SimilarityReportBuilder(true).add(alpha).add(beta).complete().build();
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
