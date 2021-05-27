package br.ufsc.ine.leb.roza.clustering;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.SimilarityReportBuilder;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.exceptions.ClusteringLevelGenerationException;
import br.ufsc.ine.leb.roza.exceptions.TiebreakException;

class DendogramTestCaseClustererTest {

	private TestCase alpha;
	private TestCase beta;
	private TestCase gamma;
	private BigDecimal dotFive;
	private BigDecimal dotOne;
	private Cluster clusterAlpha;
	private Cluster clusterBeta;
	private Cluster clusterGamma;
	private Cluster clusterAlphaBeta;
	private Cluster clusterAlphaBetaGamma;

	@BeforeEach
	void setup() {
		dotOne = new BigDecimal("0.1");
		dotFive = new BigDecimal("0.5");
		alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		clusterAlpha = new Cluster(alpha);
		clusterBeta = new Cluster(beta);
		clusterGamma = new Cluster(gamma);
		clusterAlphaBeta = clusterAlpha.merge(clusterBeta);
		clusterAlphaBetaGamma = clusterAlphaBeta.merge(clusterGamma);
	}

	@Test
	void zeroTests() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new NeverStopCriteria();
		DendogramTestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		List<Level> levels = clusterer.generateLevels(report);
		Set<Cluster> clusters = clusterer.cluster(report);

		assertEquals(1, levels.size());

		Level zero = levels.get(0);
		assertEquals(0, zero.getStep());
		assertNull(zero.getEvaluationToThisLevel());
		assertEquals(0, zero.getClusters().size());

		assertEquals(0, clusters.size());
	}

	@Test
	void oneTestStopingInLevelOneBecauseItHasntClustersToMerge() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(alpha).build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new NeverStopCriteria();
		DendogramTestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		List<Level> levels = clusterer.generateLevels(report);
		Set<Cluster> clusters = clusterer.cluster(report);

		assertEquals(1, levels.size());

		Level zero = levels.get(0);
		assertEquals(0, zero.getStep());
		assertNull(zero.getEvaluationToThisLevel());
		assertEquals(1, zero.getClusters().size());
		assertTrue(zero.getClusters().contains(clusterAlpha));

		assertEquals(1, clusters.size());
		assertTrue(clusters.contains(clusterAlpha));
	}

	@Test
	void twoTestsStopingInLevelZeroBecauseThresholdCriteria() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(alpha).add(beta).complete().build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new AlwaysStopCriteria();
		DendogramTestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		List<Level> levels = clusterer.generateLevels(report);
		Set<Cluster> clusters = clusterer.cluster(report);

		assertEquals(1, levels.size());

		Level zero = levels.get(0);
		assertEquals(0, zero.getStep());
		assertNull(zero.getEvaluationToThisLevel());
		assertEquals(2, zero.getClusters().size());
		assertTrue(zero.getClusters().contains(clusterAlpha));
		assertTrue(zero.getClusters().contains(clusterBeta));

		assertEquals(2, clusters.size());
		assertTrue(clusters.contains(clusterAlpha));
		assertTrue(clusters.contains(clusterBeta));
	}

	@Test
	void twoTestsStopingInLevelOneBecauseItHasntClustersToMerge() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(alpha).add(beta).complete().build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new NeverStopCriteria();
		DendogramTestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		List<Level> levels = clusterer.generateLevels(report);
		Set<Cluster> clusters = clusterer.cluster(report);

		assertEquals(2, levels.size());

		Level zero = levels.get(0);
		assertEquals(0, zero.getStep());
		assertNull(zero.getEvaluationToThisLevel());
		assertEquals(2, zero.getClusters().size());
		assertTrue(zero.getClusters().contains(clusterAlpha));
		assertTrue(zero.getClusters().contains(clusterBeta));

		Level one = levels.get(1);
		assertEquals(1, one.getStep());
		assertEquals(BigDecimal.ZERO, one.getEvaluationToThisLevel());
		assertEquals(1, one.getClusters().size());
		assertTrue(one.getClusters().contains(clusterAlphaBeta));

		assertEquals(1, clusters.size());
		assertTrue(clusters.contains(clusterAlphaBeta));
	}

	@Test
	void threeTestsStopingInLevelOneBecauseThresholdCriteria() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(alpha, beta, dotFive).add(gamma).complete().build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new SimilarityBasedCriteria(dotOne);
		DendogramTestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		List<Level> levels = clusterer.generateLevels(report);
		Set<Cluster> clusters = clusterer.cluster(report);

		assertEquals(2, levels.size());

		Level zero = levels.get(0);
		assertEquals(0, zero.getStep());
		assertNull(zero.getEvaluationToThisLevel());
		assertEquals(3, zero.getClusters().size());
		assertTrue(zero.getClusters().contains(clusterAlpha));
		assertTrue(zero.getClusters().contains(clusterBeta));
		assertTrue(zero.getClusters().contains(clusterGamma));

		Level one = levels.get(1);
		assertEquals(1, one.getStep());
		assertEquals(new BigDecimal("0.5"), one.getEvaluationToThisLevel());
		assertEquals(2, one.getClusters().size());
		assertTrue(one.getClusters().contains(clusterAlphaBeta));
		assertTrue(one.getClusters().contains(clusterGamma));

		assertEquals(2, clusters.size());
		assertTrue(clusters.contains(clusterAlphaBeta));
		assertTrue(clusters.contains(clusterGamma));
	}

	@Test
	void threeTestsStopingInLevelTwoBecauseItHasntClustersToMerge() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(alpha, beta, dotFive).add(gamma).complete().build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new NeverStopCriteria();
		DendogramTestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		List<Level> levels = clusterer.generateLevels(report);
		Set<Cluster> clusters = clusterer.cluster(report);

		assertEquals(3, levels.size());

		Level zero = levels.get(0);
		assertEquals(0, zero.getStep());
		assertNull(zero.getEvaluationToThisLevel());
		assertEquals(3, zero.getClusters().size());
		assertTrue(zero.getClusters().contains(clusterAlpha));
		assertTrue(zero.getClusters().contains(clusterBeta));
		assertTrue(zero.getClusters().contains(clusterGamma));

		Level one = levels.get(1);
		assertEquals(1, one.getStep());
		assertEquals(new BigDecimal("0.5"), one.getEvaluationToThisLevel());
		assertEquals(2, one.getClusters().size());
		assertTrue(one.getClusters().contains(clusterAlphaBeta));
		assertTrue(one.getClusters().contains(clusterGamma));

		Level two = levels.get(2);
		assertEquals(2, two.getStep());
		assertEquals(BigDecimal.ZERO, two.getEvaluationToThisLevel());
		assertEquals(1, two.getClusters().size());
		assertTrue(two.getClusters().contains(clusterAlphaBetaGamma));

		assertEquals(1, clusters.size());
		assertTrue(clusters.contains(clusterAlphaBetaGamma));
	}

	@Test
	void threDistinctTestsWithTiebreak() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(alpha).add(beta).add(gamma).complete().build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new NeverStopCriteria();
		DendogramTestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		ClusteringLevelGenerationException exception = assertThrows(ClusteringLevelGenerationException.class, () -> clusterer.cluster(report));

		TiebreakException tiebreak = exception.getTibreakException();
		assertEquals(3, tiebreak.getTies().size());
		assertTrue(tiebreak.getTies().contains(new Combination(clusterAlpha, clusterBeta)));
		assertTrue(tiebreak.getTies().contains(new Combination(clusterAlpha, clusterGamma)));
		assertTrue(tiebreak.getTies().contains(new Combination(clusterBeta, clusterGamma)));

		List<Level> levels = exception.getLevels();
		assertEquals(1, levels.size());
		assertEquals(3, levels.get(0).getClusters().size());
		assertTrue(levels.get(0).getClusters().contains(clusterAlpha));
		assertTrue(levels.get(0).getClusters().contains(clusterBeta));
		assertTrue(levels.get(0).getClusters().contains(clusterGamma));
	}

}
