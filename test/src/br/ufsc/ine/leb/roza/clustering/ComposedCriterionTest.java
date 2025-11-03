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

public class ComposedCriterionTest {

	private Cluster alphaCluster;
	private Cluster betaCluster;

	@BeforeEach
	public void seutp() {
		TestCase alpha = new TestCase("alpha", List.of(), List.of());
		TestCase beta = new TestCase("beta", List.of(), List.of());
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
	}

	@Test
	public void shouldNotBeEmpty() {
		assertThrows(InvalidThresholdException.class, ComposedCriterion::new);
	}

	@Test
	public void shouldHaveMoreThanOneElement() {
		assertThrows(InvalidThresholdException.class, () -> new ComposedCriterion(new LevelBasedCriterion(1)));
	}

	@Test
	void shouldNotStopInAny() {
		ThresholdCriterion threshold = new ComposedCriterion(new LevelBasedCriterion(1), new TestsPerClassCriterion(2));
		assertFalse(threshold.shouldStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInFirstLevelBased() {
		ThresholdCriterion threshold = new ComposedCriterion(new LevelBasedCriterion(0), new TestsPerClassCriterion(2));
		assertTrue(threshold.shouldStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInSecondTestsPerClass() {
		ThresholdCriterion threshold = new ComposedCriterion(new LevelBasedCriterion(1), new TestsPerClassCriterion(1));
		assertTrue(threshold.shouldStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInFirstTestsPerClassBased() {
		ThresholdCriterion threshold = new ComposedCriterion(new TestsPerClassCriterion(1), new LevelBasedCriterion(1));
		assertTrue(threshold.shouldStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInSecondLevelBased() {
		ThresholdCriterion threshold = new ComposedCriterion(new TestsPerClassCriterion(2), new LevelBasedCriterion(0));
		assertTrue(threshold.shouldStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInBoth() {
		ThresholdCriterion threshold = new ComposedCriterion(new TestsPerClassCriterion(1), new LevelBasedCriterion(0));
		assertTrue(threshold.shouldStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

}
