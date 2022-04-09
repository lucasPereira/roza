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

class TestsPerClassCriteriaTest {

	private Cluster alphaCluster;
	private Cluster betaCluster;
	private Cluster gammaCluster;
	private Cluster deltaCluster;
	private Cluster alphaBetaCluster;
	private Cluster gammaDeltaCluster;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		TestCase delta = new TestCase("delta", Arrays.asList(), Arrays.asList());
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		gammaCluster = new Cluster(gamma);
		deltaCluster = new Cluster(delta);
		alphaBetaCluster = alphaCluster.merge(betaCluster);
		gammaDeltaCluster = gammaCluster.merge(deltaCluster);
	}

	@Test
	void nextLevelIsOneAndMaximumIsOne() throws Exception {
		ThresholdCriteria criteria = new TestsPerClassCriteria(1);
		assertTrue(criteria.shoudlStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsOneAndMaximuIsTwo() throws Exception {
		ThresholdCriteria criteria = new TestsPerClassCriteria(2);
		assertFalse(criteria.shoudlStop(1, new Combination(alphaCluster, betaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsTwoAndMaximumIsTwo() throws Exception {
		ThresholdCriteria criteria = new TestsPerClassCriteria(2);
		assertTrue(criteria.shoudlStop(2, new Combination(alphaBetaCluster, gammaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsTwoAndMaximumIsThree() throws Exception {
		ThresholdCriteria criteria = new TestsPerClassCriteria(3);
		assertFalse(criteria.shoudlStop(2, new Combination(alphaBetaCluster, gammaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsThreeAndMaximumIsThree() throws Exception {
		ThresholdCriteria criteria = new TestsPerClassCriteria(3);
		assertTrue(criteria.shoudlStop(3, new Combination(alphaBetaCluster, gammaDeltaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsThreeAndMaximumIsFour() throws Exception {
		ThresholdCriteria criteria = new TestsPerClassCriteria(4);
		assertFalse(criteria.shoudlStop(3, new Combination(alphaBetaCluster, gammaDeltaCluster), BigDecimal.ONE));
	}

	@Test
	void lessThanOneCriteria() throws Exception {
		assertThrows(InvalidThresholdException.class, () -> {
			new TestsPerClassCriteria(0);
		});
	}

}
