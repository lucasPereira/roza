package br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.SimilarityReportBuilder;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.exceptions.InvalidThresholdException;

class SimilarityBasedCriteriaTest {

	private TestCase testA;
	private TestCase testB;
	private TestCase testC;
	private BigDecimal dotOne;
	private BigDecimal dotFour;
	private BigDecimal dotFive;
	private BigDecimal dotSix;
	private BigDecimal dotNine;

	@BeforeEach
	void setup() {
		testA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		testB = new TestCase("testB", Arrays.asList(), Arrays.asList());
		testC = new TestCase("testC", Arrays.asList(), Arrays.asList());
		dotOne = new BigDecimal("0.1");
		dotFour = new BigDecimal("0.4");
		dotFive = new BigDecimal("0.5");
		dotSix = new BigDecimal("0.6");
		dotNine = new BigDecimal("0.9");
	}

	@Test
	void noLevel() throws Exception {
		List<Level> levels = Arrays.asList();
		ThresholdCriteria criteria = new SimilarityBasedCriteria(BigDecimal.ZERO);
		assertFalse(criteria.shoudlStop(levels));
	}

	@Test
	void levelOneEmpty() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).build();
		Level level = new Level(new SingleLinkage(report), new InsecureReferee(), new ClusterFactory().create(report));
		List<Level> levels = Arrays.asList(level);
		ThresholdCriteria criteria = new SimilarityBasedCriteria(BigDecimal.ZERO);
		assertFalse(criteria.shoudlStop(levels));
	}

	@Test
	void levelOneWithOneTest() throws Exception {
		SimilarityReport report =new SimilarityReportBuilder(true).add(testA).build();
		Level level = new Level(new SingleLinkage(report), new InsecureReferee(), new ClusterFactory().create(report));
		List<Level> levels = Arrays.asList(level);
		ThresholdCriteria criteria = new SimilarityBasedCriteria(BigDecimal.ZERO);
		assertFalse(criteria.shoudlStop(levels));
	}

	@Test
	void zeroSimilarity() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(testA).add(testB).complete().build();
		Level level = new Level(new SingleLinkage(report), new InsecureReferee(), new ClusterFactory().create(report));
		List<Level> levels = Arrays.asList(level);
		assertTrue(new SimilarityBasedCriteria(BigDecimal.ZERO).shoudlStop(levels));
		assertTrue(new SimilarityBasedCriteria(dotOne).shoudlStop(levels));
	}

	@Test
	void oneSimilarity() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(testA, testB, BigDecimal.ONE).build();
		Level level = new Level(new SingleLinkage(report), new InsecureReferee(), new ClusterFactory().create(report));
		List<Level> levels = Arrays.asList(level);
		assertFalse(new SimilarityBasedCriteria(dotNine).shoudlStop(levels));
		assertTrue(new SimilarityBasedCriteria(BigDecimal.ONE).shoudlStop(levels));
	}

	@Test
	void zeroPointFiveSimilarity() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(testA, testB, dotFive).build();
		Level level = new Level(new SingleLinkage(report), new InsecureReferee(), new ClusterFactory().create(report));
		List<Level> levels = Arrays.asList(level);
		assertFalse(new SimilarityBasedCriteria(dotFour).shoudlStop(levels));
		assertTrue(new SimilarityBasedCriteria(dotFive).shoudlStop(levels));
		assertTrue(new SimilarityBasedCriteria(dotSix).shoudlStop(levels));
	}

	@Test
	void zeroPointFiveSimilarityInLevelTwoSingleLinkage() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(testA, testB, dotSix).add(testA, testC, dotFour).add(testB, testC, dotFive).build();
		Level one = new Level(new SingleLinkage(report), new InsecureReferee(), new ClusterFactory().create(report));
		Level two = one.generateNextLevel();
		List<Level> levels = Arrays.asList(one, two);
		assertFalse(new SimilarityBasedCriteria(dotFour).shoudlStop(levels));
		assertTrue(new SimilarityBasedCriteria(dotFive).shoudlStop(levels));
		assertTrue(new SimilarityBasedCriteria(dotSix).shoudlStop(levels));
	}

	@Test
	void zeroPointFiveSimilarityInLevelTwoCompleteLinkage() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(testA, testB, dotSix).add(testA, testC, dotFour).add(testB, testC, dotFive).build();
		Level one = new Level(new CompleteLinkage(report), new InsecureReferee(), new ClusterFactory().create(report));
		Level two = one.generateNextLevel();
		List<Level> levels = Arrays.asList(one, two);
		assertTrue(new SimilarityBasedCriteria(dotFour).shoudlStop(levels));
		assertTrue(new SimilarityBasedCriteria(dotFive).shoudlStop(levels));
		assertTrue(new SimilarityBasedCriteria(dotSix).shoudlStop(levels));
	}

	@Test
	void zeroPointFiveSimilarityInLevelTwoSingleLinkageOrderOfLevelsReversed() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(testA, testB, dotSix).add(testA, testC, dotFour).add(testB, testC, dotFive).build();
		Level one = new Level(new SingleLinkage(report), new InsecureReferee(), new ClusterFactory().create(report));
		Level two = one.generateNextLevel();
		List<Level> levels = Arrays.asList(two, one);
		assertFalse(new SimilarityBasedCriteria(dotFour).shoudlStop(levels));
		assertTrue(new SimilarityBasedCriteria(dotFive).shoudlStop(levels));
		assertTrue(new SimilarityBasedCriteria(dotSix).shoudlStop(levels));
	}

	@Test
	void zeroPointFiveSimilarityInLevelTwoCompleteLinkageOrderOfLevelsReversed() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(testA, testB, dotSix).add(testA, testC, dotFour).add(testB, testC, dotFive).build();
		Level one = new Level(new CompleteLinkage(report), new InsecureReferee(), new ClusterFactory().create(report));
		Level two = one.generateNextLevel();
		List<Level> levels = Arrays.asList(two, one);
		assertTrue(new SimilarityBasedCriteria(dotFour).shoudlStop(levels));
		assertTrue(new SimilarityBasedCriteria(dotFive).shoudlStop(levels));
		assertTrue(new SimilarityBasedCriteria(dotSix).shoudlStop(levels));
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
