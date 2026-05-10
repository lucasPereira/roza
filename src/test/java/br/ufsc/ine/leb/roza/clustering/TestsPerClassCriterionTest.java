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

class TestsPerClassCriterionTest {

	private Cluster alphaCluster;
	private Cluster betaCluster;
	private Cluster gammaCluster;
	private Cluster alphaBetaCluster;
	private Cluster gammaDeltaCluster;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", List.of(), List.of());
		TestCase beta = new TestCase("beta", List.of(), List.of());
		TestCase gamma = new TestCase("gamma", List.of(), List.of());
		TestCase delta = new TestCase("delta", List.of(), List.of());
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		gammaCluster = new Cluster(gamma);
		Cluster deltaCluster = new Cluster(delta);
		alphaBetaCluster = alphaCluster.merge(betaCluster);
		gammaDeltaCluster = gammaCluster.merge(deltaCluster);
	}

	@Test
	void nextLevelIsOneAndMaximumIsOne() {
		ThresholdCriterion criterion = new TestsPerClassCriterion(1);
		assertTrue(criterion.shouldStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsOneAndMaximuIsTwo() {
		ThresholdCriterion criterion = new TestsPerClassCriterion(2);
		assertFalse(criterion.shouldStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsTwoAndMaximumIsTwo() {
		ThresholdCriterion criterion = new TestsPerClassCriterion(2);
		assertTrue(criterion.shouldStop(2, new Combination(alphaBetaCluster, gammaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsTwoAndMaximumIsThree() {
		ThresholdCriterion criterion = new TestsPerClassCriterion(3);
		assertFalse(criterion.shouldStop(2, new Combination(alphaBetaCluster, gammaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsThreeAndMaximumIsThree() {
		ThresholdCriterion criterion = new TestsPerClassCriterion(3);
		assertTrue(criterion.shouldStop(3, new Combination(alphaBetaCluster, gammaDeltaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsThreeAndMaximumIsFour() {
		ThresholdCriterion criterion = new TestsPerClassCriterion(4);
		assertFalse(criterion.shouldStop(3, new Combination(alphaBetaCluster, gammaDeltaCluster), BigDecimal.ONE));
	}

	@Test
	void lessThanOneCriterion() {
		assertThrows(InvalidThresholdException.class, () -> new TestsPerClassCriterion(0));
	}

}
