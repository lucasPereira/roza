package br.ufsc.ine.leb.roza.clustering.dendrogram;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ThresholdCriteriaTest {

	private List<Level> levels;

	@BeforeEach
	void seutp() {
		levels = new LinkedList<>();
	}

	@Test
	void alwaysStopCriteria() throws Exception {
		ThresholdCriteria threshold = new AlwaysStopCriteria();
		assertTrue(threshold.shoudlStop(levels));
	}

	@Test
	void neverStopCriteria() throws Exception {
		ThresholdCriteria threshold = new NeverStopCriteria();
		assertFalse(threshold.shoudlStop(levels));
	}

}
