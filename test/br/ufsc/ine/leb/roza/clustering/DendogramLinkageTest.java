package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TestCase;

public class DendogramLinkageTest {

	@Test
	void create() throws Exception {
		TestCase alfa = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		DendogramCluster clusterAlfa = new DendogramCluster(alfa);
		DendogramCluster clusterBeta = new DendogramCluster(beta);
		DendogramLinkage linkage = new DendogramLinkage(clusterAlfa, clusterBeta);
		assertEquals(clusterAlfa, linkage.getFirst());
		assertEquals(clusterBeta, linkage.getSecond());
	}

}
