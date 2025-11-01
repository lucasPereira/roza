package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.exceptions.InvalidThresholdException;

class LevelBasedCriteriaTest {

	private Cluster alphaCluster;
	private Cluster betaCluster;
	private Cluster gammaCluster;
	private Cluster alphaBetaCluster;

	@BeforeEach
	void seutp() {
		TestCase alpha = new TestCase("alpha", List.of(), List.of());
		TestCase beta = new TestCase("beta", List.of(), List.of());
		TestCase gamma = new TestCase("gamma", List.of(), List.of());
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		gammaCluster = new Cluster(gamma);
		alphaBetaCluster = alphaCluster.merge(betaCluster);
	}

	@Test
	void nextLevelIsOneAndMaximumLevelIsZero() {
		ThresholdCriteria threshold = new LevelBasedCriteria(0);
		assertTrue(threshold.shouldStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsOneAndMaximumLevelIsOne() {
		ThresholdCriteria threshold = new LevelBasedCriteria(1);
		assertFalse(threshold.shouldStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsTwoAndMaximumLevelIsOne() {
		ThresholdCriteria threshold = new LevelBasedCriteria(1);
		assertTrue(threshold.shouldStop(2, new Combination(alphaBetaCluster, gammaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsTwoAndMaximumLevelIsTwo() {
		ThresholdCriteria threshold = new LevelBasedCriteria(2);
		assertFalse(threshold.shouldStop(2, new Combination(alphaBetaCluster, gammaCluster), BigDecimal.ONE));
	}

	@Test
	void cantStopAtNegativeLevel() {
		assertThrows(InvalidThresholdException.class, () -> new LevelBasedCriteria(-1));
	}

}
