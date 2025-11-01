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

public class ComposedCriteriaTest {

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
		assertThrows(InvalidThresholdException.class, ComposedCriteria::new);
	}

	@Test
	public void shouldHaveMoreThanOneElement() {
		assertThrows(InvalidThresholdException.class, () -> new ComposedCriteria(new LevelBasedCriteria(1)));
	}

	@Test
	void shouldNotStopInAny() {
		ThresholdCriteria threshold = new ComposedCriteria(new LevelBasedCriteria(1), new TestsPerClassCriteria(2));
		assertFalse(threshold.shouldStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInFirstLevelBased() {
		ThresholdCriteria threshold = new ComposedCriteria(new LevelBasedCriteria(0), new TestsPerClassCriteria(2));
		assertTrue(threshold.shouldStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInSecondTestsPerClass() {
		ThresholdCriteria threshold = new ComposedCriteria(new LevelBasedCriteria(1), new TestsPerClassCriteria(1));
		assertTrue(threshold.shouldStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInFirstTestsPerClassBased() {
		ThresholdCriteria threshold = new ComposedCriteria(new TestsPerClassCriteria(1), new LevelBasedCriteria(1));
		assertTrue(threshold.shouldStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInSecondLevelBased() {
		ThresholdCriteria threshold = new ComposedCriteria(new TestsPerClassCriteria(2), new LevelBasedCriteria(0));
		assertTrue(threshold.shouldStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInBoth() {
		ThresholdCriteria threshold = new ComposedCriteria(new TestsPerClassCriteria(1), new LevelBasedCriteria(0));
		assertTrue(threshold.shouldStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

}
