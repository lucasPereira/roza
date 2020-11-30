package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.List;

import br.ufsc.ine.leb.roza.exceptions.InvalidThresholdException;

public class TestsPerClassCriteria implements ThresholdCriteria {

	private Integer threshold;

	public TestsPerClassCriteria(Integer threshold) {
		if (threshold < 1) {
			throw new InvalidThresholdException();
		}
		this.threshold = threshold;
	}

	@Override
	public Boolean shoudlStop(List<Level> levels) {
		for (Level level : levels) {
			if (level.hasNextLevel()) {
				Combination next = level.getCombinationToNextLevel();
				Integer nextSize = next.getFirst().getTestCases().size() + next.getSecond().getTestCases().size();
				if (nextSize > threshold) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%s(%d)", getClass().getSimpleName(), threshold);
	}

}
