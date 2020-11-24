package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.exceptions.InvalidThresholdException;

class TestsPerClassCriteriaTest {

	private SumOfIdsLinkage linkage;
	private InsecureReferee referee;
	private TestCase alpha;
	private TestCase beta;
	private TestCase gamma;
	private TestCase delta;
	private Cluster alphaCluster;
	private Cluster betaCluster;
	private Cluster gammaCluster;
	private Cluster deltaCluster;
	private Cluster alphaBetaCluster;
	private Cluster gammaDeltaCluster;

	@BeforeEach
	void setup() {
		linkage = new SumOfIdsLinkage();
		referee = new InsecureReferee();
		alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		delta = new TestCase("delta", Arrays.asList(), Arrays.asList());
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		gammaCluster = new Cluster(gamma);
		deltaCluster = new Cluster(delta);
		alphaBetaCluster = alphaCluster.merge(betaCluster);
		gammaDeltaCluster = gammaCluster.merge(deltaCluster);
	}

	@Test
	void noLevel() throws Exception {
		List<Level> levels = Arrays.asList();
		ThresholdCriteria criteria = new TestsPerClassCriteria(1);
		assertFalse(criteria.shoudlStop(levels));
	}

	@Test
	void levelOneEmpty() throws Exception {
		Set<Cluster> clusters = new HashSet<>();
		Level level = new Level(linkage, referee, clusters);
		List<Level> levels = Arrays.asList(level);
		ThresholdCriteria criteria = new TestsPerClassCriteria(1);
		assertFalse(criteria.shoudlStop(levels));
	}

	@Test
	void levelOneExceedingThreshold() throws Exception {
		Set<Cluster> clusters = new HashSet<>(Arrays.asList(alphaCluster, betaCluster, gammaCluster, deltaCluster));
		Level level = new Level(linkage, referee, clusters);
		List<Level> levels = Arrays.asList(level);
		ThresholdCriteria criteria = new TestsPerClassCriteria(1);
		assertTrue(criteria.shoudlStop(levels));
	}

	@Test
	void levelOneNotExceedingThreshold() throws Exception {
		Set<Cluster> clusters = new HashSet<>(Arrays.asList(alphaCluster, betaCluster, gammaCluster, deltaCluster));
		Level level = new Level(linkage, referee, clusters);
		List<Level> levels = Arrays.asList(level);
		ThresholdCriteria criteria = new TestsPerClassCriteria(2);
		assertFalse(criteria.shoudlStop(levels));
	}

	@Test
	void levelTwoExceedingThreshold() throws Exception {
		Level one = new Level(linkage, referee, new HashSet<>(Arrays.asList(alphaCluster, betaCluster, gammaCluster)));
		Level two = new Level(linkage, referee, new HashSet<>(Arrays.asList(alphaBetaCluster, gammaCluster)));
		List<Level> levels = Arrays.asList(one, two);
		ThresholdCriteria criteria = new TestsPerClassCriteria(2);
		assertTrue(criteria.shoudlStop(levels));
	}

	@Test
	void levelTwoNotExceedingThreshold() throws Exception {
		Level one = new Level(linkage, referee, new HashSet<>(Arrays.asList(alphaCluster, betaCluster, gammaCluster)));
		Level two = new Level(linkage, referee, new HashSet<>(Arrays.asList(alphaBetaCluster, gammaCluster)));
		List<Level> levels = Arrays.asList(one, two);
		ThresholdCriteria criteria = new TestsPerClassCriteria(3);
		assertFalse(criteria.shoudlStop(levels));
	}

	@Test
	void levelTwoExceedingThresholdLevelsReversed() throws Exception {
		Level one = new Level(linkage, referee, new HashSet<>(Arrays.asList(alphaCluster, betaCluster, gammaCluster)));
		Level two = new Level(linkage, referee, new HashSet<>(Arrays.asList(alphaBetaCluster, gammaCluster)));
		List<Level> levels = Arrays.asList(two, one);
		ThresholdCriteria criteria = new TestsPerClassCriteria(2);
		assertTrue(criteria.shoudlStop(levels));
	}

	@Test
	void levelThreeExceedingThreshold() throws Exception {
		Level one = new Level(linkage, referee, new HashSet<>(Arrays.asList(alphaCluster, betaCluster, gammaCluster, deltaCluster)));
		Level two = new Level(linkage, referee, new HashSet<>(Arrays.asList(alphaBetaCluster, gammaCluster, deltaCluster)));
		Level three = new Level(linkage, referee, new HashSet<>(Arrays.asList(alphaBetaCluster, gammaDeltaCluster)));
		List<Level> levels = Arrays.asList(one, two, three);
		ThresholdCriteria criteria = new TestsPerClassCriteria(3);
		assertTrue(criteria.shoudlStop(levels));
	}

	@Test
	void levelThreeNotExceedingThreshold() throws Exception {
		Level one = new Level(linkage, referee, new HashSet<>(Arrays.asList(alphaCluster, betaCluster, gammaCluster, deltaCluster)));
		Level two = new Level(linkage, referee, new HashSet<>(Arrays.asList(alphaBetaCluster, gammaCluster, deltaCluster)));
		Level three = new Level(linkage, referee, new HashSet<>(Arrays.asList(alphaBetaCluster, gammaDeltaCluster)));
		List<Level> levels = Arrays.asList(one, two, three);
		ThresholdCriteria criteria = new TestsPerClassCriteria(4);
		assertFalse(criteria.shoudlStop(levels));
	}

	@Test
	void lessThanOneCriteria() throws Exception {
		assertThrows(InvalidThresholdException.class, () -> {
			new TestsPerClassCriteria(0);
		});
	}

}
