package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.exceptions.InvalidThresholdException;
import br.ufsc.ine.leb.roza.extraction.TestCase;

public class ComposedCriteriaTest {

	private Cluster alphaCluster;
	private Cluster betaCluster;

	@BeforeEach
	public void seutp() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
	}

	@Test
	public void shouldNotBeEmpty() throws Exception {
		assertThrows(InvalidThresholdException.class, () -> {
			new ComposedCriteria();
		});
	}

	@Test
	public void shouldHaveMoreThanOneElement() throws Exception {
		assertThrows(InvalidThresholdException.class, () -> {
			new ComposedCriteria(new LevelBasedCriteria(1));
		});
	}

	@Test
	void shouldNotStopInAny() throws Exception {
		ThresholdCriteria threshold = new ComposedCriteria(new LevelBasedCriteria(1), new TestsPerClassCriteria(2));
		assertFalse(threshold.shoudlStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInFirstLevelBased() throws Exception {
		ThresholdCriteria threshold = new ComposedCriteria(new LevelBasedCriteria(0), new TestsPerClassCriteria(2));
		assertTrue(threshold.shoudlStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInSecondTestsPerClass() throws Exception {
		ThresholdCriteria threshold = new ComposedCriteria(new LevelBasedCriteria(1), new TestsPerClassCriteria(1));
		assertTrue(threshold.shoudlStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInFirstTestsPerClassBased() throws Exception {
		ThresholdCriteria threshold = new ComposedCriteria(new TestsPerClassCriteria(1), new LevelBasedCriteria(1));
		assertTrue(threshold.shoudlStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInSecondLevelBased() throws Exception {
		ThresholdCriteria threshold = new ComposedCriteria(new TestsPerClassCriteria(2), new LevelBasedCriteria(0));
		assertTrue(threshold.shoudlStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInBoth() throws Exception {
		ThresholdCriteria threshold = new ComposedCriteria(new TestsPerClassCriteria(1), new LevelBasedCriteria(0));
		assertTrue(threshold.shoudlStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

}
