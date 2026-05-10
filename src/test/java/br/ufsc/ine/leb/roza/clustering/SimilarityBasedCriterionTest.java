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

class SimilarityBasedCriterionTest {

	private BigDecimal dotOne;
	private BigDecimal dotFour;
	private BigDecimal dotFive;
	private BigDecimal dotSix;
	private BigDecimal dotNine;
	private Combination combination;
	private Integer level;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", List.of(), List.of());
		TestCase beta = new TestCase("beta", List.of(), List.of());
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
	void zeroSimilarity() {
		assertTrue(new SimilarityBasedCriterion(BigDecimal.ZERO).shouldStop(level, combination, BigDecimal.ZERO));
		assertTrue(new SimilarityBasedCriterion(dotOne).shouldStop(level, combination, BigDecimal.ZERO));
	}

	@Test
	void oneSimilarity() {
		assertFalse(new SimilarityBasedCriterion(dotNine).shouldStop(level, combination, BigDecimal.ONE));
		assertTrue(new SimilarityBasedCriterion(BigDecimal.ONE).shouldStop(level, combination, BigDecimal.ONE));
	}

	@Test
	void zeroPointFiveSimilarity() {
		assertFalse(new SimilarityBasedCriterion(dotFour).shouldStop(level, combination, dotFive));
		assertTrue(new SimilarityBasedCriterion(dotFive).shouldStop(level, combination, dotFive));
		assertTrue(new SimilarityBasedCriterion(dotSix).shouldStop(level, combination, dotFive));
	}

	@Test
	void outOfRangeThresholds() {
		assertThrows(InvalidThresholdException.class, () -> new SimilarityBasedCriterion(new BigDecimal("-0.1")));
		assertThrows(InvalidThresholdException.class, () -> new SimilarityBasedCriterion(new BigDecimal("1.1")));
	}

}
