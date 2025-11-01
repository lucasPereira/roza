package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;

public class WinnerCombinationTest {

	@Test
	void test() {
		TestCase alpha = new TestCase("alpha", List.of(), List.of());
		TestCase beta = new TestCase("beta", List.of(), List.of());
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Combination alphaBetaCombination = new Combination(alphaCluster, betaCluster);
		WinnerCombination winner = new WinnerCombination(alphaBetaCombination, BigDecimal.ONE);
		assertEquals(alphaBetaCombination, winner.getCombination());
		assertEquals(BigDecimal.ONE, winner.getEvaluation());
	}

}
