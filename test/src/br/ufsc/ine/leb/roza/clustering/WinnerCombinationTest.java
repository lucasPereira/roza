package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;

public class WinnerCombinationTest {

	@Test
	void test() throws Exception {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Combination alphaBetaCombination = new Combination(alphaCluster, betaCluster);
		WinnerCombination winner = new WinnerCombination(alphaBetaCombination, BigDecimal.ONE);
		assertEquals(alphaBetaCombination, winner.getCombination());
		assertEquals(BigDecimal.ONE, winner.getEvaluation());
	}

}
