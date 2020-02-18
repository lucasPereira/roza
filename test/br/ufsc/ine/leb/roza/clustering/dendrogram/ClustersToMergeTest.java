package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Iterator;

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
		Iterator<Cluster> iterator = clusters.iterator();
		assertEquals(clusterAlfa, iterator.next());
		assertEquals(clusterBeta, iterator.next());
		assertFalse(iterator.hasNext());
	}

}
