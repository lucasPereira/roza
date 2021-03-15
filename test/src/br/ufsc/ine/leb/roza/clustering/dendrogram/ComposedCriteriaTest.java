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

public class ComposedCriteriaTest {

	private List<Level> levels;

	@BeforeEach
	public void seutp() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Set<Cluster> clusters = new HashSet<>();
		clusters.add(alphaCluster);
		clusters.add(betaCluster);
		Level level = new Level(new SumOfIdsLinkage(), new InsecureReferee(), clusters);
		levels = Arrays.asList(level);
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
		ThresholdCriteria threshold = new ComposedCriteria(new LevelBasedCriteria(2), new TestsPerClassCriteria(2));
		assertFalse(threshold.shoudlStop(levels));
	}

	@Test
	void shouldStopInFirstLevelBased() throws Exception {
		ThresholdCriteria threshold = new ComposedCriteria(new LevelBasedCriteria(1), new TestsPerClassCriteria(2));
		assertTrue(threshold.shoudlStop(levels));
	}

	@Test
	void shouldStopInSecondTestsPerClass() throws Exception {
		ThresholdCriteria threshold = new ComposedCriteria(new LevelBasedCriteria(2), new TestsPerClassCriteria(1));
		assertTrue(threshold.shoudlStop(levels));
	}

	@Test
	void shouldStopInFirstTestsPerClassBased() throws Exception {
		ThresholdCriteria threshold = new ComposedCriteria(new TestsPerClassCriteria(1), new LevelBasedCriteria(2));
		assertTrue(threshold.shoudlStop(levels));
	}

	@Test
	void shouldStopInSecondLevelBased() throws Exception {
		ThresholdCriteria threshold = new ComposedCriteria(new TestsPerClassCriteria(2), new LevelBasedCriteria(1));
		assertTrue(threshold.shoudlStop(levels));
	}

	@Test
	void shouldStopInBoth() throws Exception {
		ThresholdCriteria threshold = new ComposedCriteria(new TestsPerClassCriteria(1), new LevelBasedCriteria(1));
		assertTrue(threshold.shoudlStop(levels));
	}

}
