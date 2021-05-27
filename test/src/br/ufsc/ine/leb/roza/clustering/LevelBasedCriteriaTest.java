package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;

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
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		gammaCluster = new Cluster(gamma);
		alphaBetaCluster = alphaCluster.merge(betaCluster);
	}

	@Test
	void nextLevelIsOneAndMaximumLevelIsZero() throws Exception {
		ThresholdCriteria threshold = new LevelBasedCriteria(0);
		assertTrue(threshold.shoudlStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsOneAndMaximumLevelIsOne() throws Exception {
		ThresholdCriteria threshold = new LevelBasedCriteria(1);
		assertFalse(threshold.shoudlStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsTwoAndMaximumLevelIsOne() throws Exception {
		ThresholdCriteria threshold = new LevelBasedCriteria(1);
		assertTrue(threshold.shoudlStop(2, new Combination(alphaBetaCluster, gammaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsTwoAndMaximumLevelIsTwo() throws Exception {
		ThresholdCriteria threshold = new LevelBasedCriteria(2);
		assertFalse(threshold.shoudlStop(2, new Combination(alphaBetaCluster, gammaCluster), BigDecimal.ONE));
	}

	@Test
	void cantStopAtNegativeLevel() throws Exception {
		assertThrows(InvalidThresholdException.class, () -> {
			new LevelBasedCriteria(-1);
		});
	}

}
