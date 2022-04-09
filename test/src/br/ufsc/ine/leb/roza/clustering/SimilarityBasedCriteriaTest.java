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

class SimilarityBasedCriteriaTest {

	private BigDecimal dotOne;
	private BigDecimal dotFour;
	private BigDecimal dotFive;
	private BigDecimal dotSix;
	private BigDecimal dotNine;
	private Combination combination;
	private Integer level;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		combination = new Combination(alphaCluster, betaCluster);
		dotOne = new BigDecimal("0.1");
		dotFour = new BigDecimal("0.4");
		dotFive = new BigDecimal("0.5");
		dotSix = new BigDecimal("0.6");
		dotNine = new BigDecimal("0.9");
		level = 1;
	}

	@Test
	void zeroSimilarity() throws Exception {
		assertTrue(new SimilarityBasedCriteria(BigDecimal.ZERO).shoudlStop(level, combination, BigDecimal.ZERO));
		assertTrue(new SimilarityBasedCriteria(dotOne).shoudlStop(level, combination, BigDecimal.ZERO));
	}

	@Test
	void oneSimilarity() throws Exception {
		assertFalse(new SimilarityBasedCriteria(dotNine).shoudlStop(level, combination, BigDecimal.ONE));
		assertTrue(new SimilarityBasedCriteria(BigDecimal.ONE).shoudlStop(level, combination, BigDecimal.ONE));
	}

	@Test
	void zeroPointFiveSimilarity() throws Exception {
		assertFalse(new SimilarityBasedCriteria(dotFour).shoudlStop(level, combination, dotFive));
		assertTrue(new SimilarityBasedCriteria(dotFive).shoudlStop(level, combination, dotFive));
		assertTrue(new SimilarityBasedCriteria(dotSix).shoudlStop(level, combination, dotFive));
	}

	@Test
	void outOfRangeThresholds() throws Exception {
		assertThrows(InvalidThresholdException.class, () -> {
			new SimilarityBasedCriteria(new BigDecimal("-0.1"));
		});
		assertThrows(InvalidThresholdException.class, () -> {
			new SimilarityBasedCriteria(new BigDecimal("1.1"));
		});
	}

}
