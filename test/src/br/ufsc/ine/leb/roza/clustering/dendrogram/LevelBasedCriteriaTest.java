package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.exceptions.InvalidThresholdException;

class LevelBasedCriteriaTest {

	private Level empty;
	private List<Level> levels;

	@BeforeEach
	void seutp() {
		levels = new LinkedList<Level>();
		empty = new Level(new SumOfIdsLinkage(), new InsecureReferee(), new HashSet<>());
	}

	@Test
	void zeroLevelsStopAtLevelZero() throws Exception {
		ThresholdCriteria threshold = new LevelBasedCriteria(0);
		assertTrue(threshold.shoudlStop(levels));
	}

	@Test
	void zeroLevelsStopAtLevelOne() throws Exception {
		ThresholdCriteria threshold = new LevelBasedCriteria(1);
		assertFalse(threshold.shoudlStop(levels));
	}

	@Test
	void oneLevelStopAtLevelOne() throws Exception {
		ThresholdCriteria threshold = new LevelBasedCriteria(1);
		levels.add(empty);
		assertTrue(threshold.shoudlStop(levels));
	}

	@Test
	void twoLevelsStopAtLevelOne() throws Exception {
		ThresholdCriteria threshold = new LevelBasedCriteria(1);
		levels.add(empty);
		levels.add(empty);
		assertTrue(threshold.shoudlStop(levels));
	}

	@Test
	void cantStopAtNegativeLevel() throws Exception {
		assertThrows(InvalidThresholdException.class, () -> {
			new LevelBasedCriteria(-1);
		});
	}

}
