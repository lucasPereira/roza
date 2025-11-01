package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;

class ThresholdCriteriaTest {

	private Combination combination;
	private Integer level;

	@BeforeEach
	void seutp() {
		TestCase alpha = new TestCase("alpha", List.of(), List.of());
		TestCase beta = new TestCase("beta", List.of(), List.of());
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		combination = new Combination(alphaCluster, betaCluster);
		level = 1;
	}

	@Test
	void alwaysStopCriteria() {
		ThresholdCriteria threshold = new AlwaysStopCriteria();
		assertTrue(threshold.shouldStop(level, combination, BigDecimal.ONE));
	}

	@Test
	void neverStopCriteria() {
		ThresholdCriteria threshold = new NeverStopCriteria();
		assertFalse(threshold.shouldStop(level, combination, BigDecimal.ONE));
	}

}
