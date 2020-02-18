package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.clustering.dendrogram.Cluster;
import br.ufsc.ine.leb.roza.clustering.dendrogram.Link;

public class LinkTest {

	@Test
	void create() throws Exception {
		TestCase alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		Cluster clusterAlfa = new Cluster(alfa);
		Cluster clusterBeta = new Cluster(beta);
		Link link = new Link(clusterAlfa, clusterBeta);
		assertEquals(clusterAlfa, link.getFirst());
		assertEquals(clusterBeta, link.getSecond());
	}

}
