package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.clustering.dendrogram.Cluster;

public class ClusterTest {

	private TestCase alfa;
	private TestCase beta;
	private Cluster clusterAlfa;
	private Cluster clusterBeta;

	@BeforeEach
	void setup() {
		alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		clusterAlfa = new Cluster(alfa);
		clusterBeta = new Cluster(beta);
	}

	@Test
	void properties() throws Exception {
		assertEquals(1, clusterAlfa.getElements().size());
		assertEquals(alfa, clusterAlfa.getElements().get(0));
	}

	@Test
	void merge() throws Exception {
		Cluster clusterAlfaBeta = clusterAlfa.merge(clusterBeta);
		assertEquals(2, clusterAlfaBeta.getElements().size());
		assertEquals(alfa, clusterAlfaBeta.getElements().get(0));
		assertEquals(beta, clusterAlfaBeta.getElements().get(1));
	}

	@Test
	void mergeReverse() throws Exception {
		Cluster clusterBetaAlfa = clusterBeta.merge(clusterAlfa);
		assertEquals(2, clusterBetaAlfa.getElements().size());
		assertEquals(beta, clusterBetaAlfa.getElements().get(0));
		assertEquals(alfa, clusterBetaAlfa.getElements().get(1));
	}

}
