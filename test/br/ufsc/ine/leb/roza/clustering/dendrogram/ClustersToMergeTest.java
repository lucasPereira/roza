package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.exceptions.NoClustersToMergeException;

public class ClustersToMergeTest {

	private Cluster clusterAlfa;
	private Cluster clusterBeta;

	@BeforeEach
	void setup() {
		TestCase alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		clusterAlfa = new Cluster(alfa);
		clusterBeta = new Cluster(beta);
	}

	@Test
	void zeroElements() throws Exception {
		assertThrows(NoClustersToMergeException.class, () -> {
			new ClustersToMerge(Arrays.asList());
		});
	}

	@Test
	void oneElement() throws Exception {
		assertThrows(NoClustersToMergeException.class, () -> {
			new ClustersToMerge(Arrays.asList(clusterAlfa));
		});
	}

	@Test
	void twoElements() throws Exception {
		ClustersToMerge clusters = new ClustersToMerge(Arrays.asList(clusterAlfa, clusterBeta));
		assertEquals(2, clusters.getClusters().size());
		assertEquals(clusterAlfa, clusters.getClusters().get(0));
		assertEquals(clusterBeta, clusters.getClusters().get(1));
	}

}
