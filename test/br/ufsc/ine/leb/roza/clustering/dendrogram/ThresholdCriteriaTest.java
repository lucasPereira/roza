package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ThresholdCriteriaTest {

	private Level level;
	private List<Level> levels;

	@BeforeEach
	void seutp() {
		levels = new LinkedList<>();
		level = new Level(new LowerIdLinkage(), new HashSet<>());
	}

	@Test
	void alwaysStopCriteria() throws Exception {
		ThresholdCriteria threshold = new AlwaysStopCriteria();
		assertTrue(threshold.shoudlStop(levels));
	}

	@Test
	void fixedInterationsCriteriaLevel0StopAt0() throws Exception {
		ThresholdCriteria threshold = new FixedInterationsCriteria(0);
		assertTrue(threshold.shoudlStop(levels));
	}

	@Test
	void fixedInterationsCriteriaLevel0StopAt1() throws Exception {
		ThresholdCriteria threshold = new FixedInterationsCriteria(1);
		assertFalse(threshold.shoudlStop(levels));
	}

	@Test
	void fixedInterationsCriteriaLevel1StopAt1() throws Exception {
		ThresholdCriteria threshold = new FixedInterationsCriteria(1);
		levels.add(level);
		assertTrue(threshold.shoudlStop(levels));
	}

	@Test
	void fixedInterationsCriteriaLevel2StopAt1() throws Exception {
		ThresholdCriteria threshold = new FixedInterationsCriteria(1);
		levels.add(level);
		levels.add(level);
		assertTrue(threshold.shoudlStop(levels));
	}

}
