package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TestCase;

public class FirstLinkageTest {

	private Linkage linkage;

	@BeforeEach
	void setup() {
		linkage = new FirstLinkage();
	}

	@Test
	void twoSingleElementClusters() throws Exception {
		TestCase alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		Cluster clusterAlfa = new Cluster(alfa);
		Cluster clusterBeta = new Cluster(beta);
		Link link = linkage.link(new ClustersToMerge(Arrays.asList(clusterAlfa, clusterBeta)), null);
		assertEquals(clusterAlfa, link.getFirst());
		assertEquals(clusterBeta, link.getSecond());
	}

	@Test
	void threeSingleElementClusters() throws Exception {
		TestCase alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		Cluster clusterAlfa = new Cluster(alfa);
		Cluster clusterBeta = new Cluster(beta);
		Cluster clusterGamma = new Cluster(gamma);
		Link link = linkage.link(new ClustersToMerge(Arrays.asList(clusterAlfa, clusterBeta, clusterGamma)), null);
		assertEquals(clusterAlfa, link.getFirst());
		assertEquals(clusterBeta, link.getSecond());
	}

	@Test
	void oneTwoElementsClusterAndOneSingleElementCluster() throws Exception {
		TestCase alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		Cluster clusterAlfa = new Cluster(alfa);
		Cluster clusterBeta = new Cluster(beta);
		Cluster clusterGamma = new Cluster(gamma);
		Cluster clusterAlfaBeta = clusterAlfa.merge(clusterBeta);
		Link link = linkage.link(new ClustersToMerge(Arrays.asList(clusterAlfaBeta, clusterGamma)), null);
		assertEquals(clusterAlfaBeta, link.getFirst());
		assertEquals(clusterGamma, link.getSecond());
	}

}
