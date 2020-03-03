package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;

public class TestsPerClassCriteriaTest {

	private SumOfIdsLinkage linkage;
	private InsecureReferee referee;
	private TestCase alpha;
	private TestCase beta;
	private TestCase gamma;
	private Cluster alphaCluster;
	private Cluster betaCluster;
	private Cluster gammaCluster;
	private Cluster alphaBetaCluster;
	private Cluster alphaGammaCluster;

	@BeforeEach
	void setup() {
		linkage = new SumOfIdsLinkage();
		referee = new InsecureReferee();
		alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		alphaCluster = new Cluster(alpha);
		betaCluster = new Cluster(beta);
		gammaCluster = new Cluster(gamma);
		alphaBetaCluster = alphaCluster.merge(betaCluster);
		alphaGammaCluster = alphaCluster.merge(gammaCluster);
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
	void levelOneWithOneClusterOfOneTestAndThresholdEqualToOne() throws Exception {
		Set<Cluster> clusters = new HashSet<>(Arrays.asList(alphaCluster));
		Level level = new Level(linkage, referee, clusters);
		List<Level> levels = Arrays.asList(level);
		ThresholdCriteria criteria = new TestsPerClassCriteria(1);
		assertTrue(criteria.shoudlStop(levels));
	}

	@Test
	void levelOneWithOneClusterOfOneTestAndThresholdEqualToTwo() throws Exception {
		Set<Cluster> clusters = new HashSet<>(Arrays.asList(alphaCluster));
		Level level = new Level(linkage, referee, clusters);
		List<Level> levels = Arrays.asList(level);
		ThresholdCriteria criteria = new TestsPerClassCriteria(2);
		assertFalse(criteria.shoudlStop(levels));
	}

	@Test
	void levelOneWithTwoClustersNotExceedingThreshold() throws Exception {
		Set<Cluster> clusters = new HashSet<>(Arrays.asList(alphaBetaCluster, alphaGammaCluster));
		Level level = new Level(linkage, referee, clusters);
		List<Level> levels = Arrays.asList(level);
		ThresholdCriteria criteria = new TestsPerClassCriteria(3);
		assertFalse(criteria.shoudlStop(levels));
	}

	@Test
	void levelOneWithFourClustersWithTheTwoOddClustersExceedingThreshold() throws Exception {
		Set<Cluster> clusters = new HashSet<>(Arrays.asList(betaCluster, alphaBetaCluster, gammaCluster, alphaGammaCluster));
		Level level = new Level(linkage, referee, clusters);
		List<Level> levels = Arrays.asList(level);
		ThresholdCriteria criteria = new TestsPerClassCriteria(2);
		assertTrue(criteria.shoudlStop(levels));
	}

}
