package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.SimilarityReportBuilder;
import br.ufsc.ine.leb.roza.TestCase;

class ClusterFactoryTest {

	private SimilarityReport report;
	private ClusterFactory factory;
	private Cluster alphaCluster;
	private Cluster betaCluster;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", List.of(), List.of());
		TestCase beta = new TestCase("beta", List.of(), List.of());
		report = new SimilarityReportBuilder(true).add(alpha).add(beta).complete().build();
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		factory = new ClusterFactory();
	}

	@Test
	void create() {
		Set<Cluster> clusters = factory.create(report);
		assertEquals(2, clusters.size());
		assertTrue(clusters.contains(alphaCluster));
		assertTrue(clusters.contains(betaCluster));
	}

}
