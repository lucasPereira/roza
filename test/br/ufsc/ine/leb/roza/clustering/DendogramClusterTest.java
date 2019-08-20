package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TestCase;

public class DendogramClusterTest {

	@Test
	void create() throws Exception {
		TestCase test = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		DendogramCluster cluster = new DendogramCluster(test);
		assertEquals(1, cluster.getElements().size());
		assertEquals(test, cluster.getElements().get(0));
	}

	@Test
	void mergeTwoClusters() throws Exception {
		TestCase alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		DendogramCluster clusterAlfa = new DendogramCluster(alfa);
		DendogramCluster clusterBeta = new DendogramCluster(beta);
		DendogramCluster clusterAlfaBeta = clusterAlfa.merge(clusterBeta);
		DendogramCluster clusterBetaAlfa = clusterBeta.merge(clusterAlfa);

		assertEquals(2, clusterAlfaBeta.getElements().size());
		assertEquals(alfa, clusterAlfaBeta.getElements().get(0));
		assertEquals(beta, clusterAlfaBeta.getElements().get(1));

		assertEquals(2, clusterBetaAlfa.getElements().size());
		assertEquals(beta, clusterBetaAlfa.getElements().get(0));
		assertEquals(alfa, clusterBetaAlfa.getElements().get(1));
	}

}
