package br.ufsc.ine.leb.roza.core.clustering;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.core.exceptions.InvalidThresholdException;

public class LevelBasedCriterion implements ThresholdCriterion {

	private final Integer threshold;

	public LevelBasedCriterion(Integer threshold) {
		if (threshold < 0) {
			throw new InvalidThresholdException();
		}
		this.threshold = threshold;
	}

	@Override
	public Boolean shouldStop(Integer nextLevel, Combination combinationToNext, BigDecimal evaluationToNext) {
		return nextLevel > threshold;
	}

	@Override
	public String toString() {
		return String.format("%s(%d)", getClass().getSimpleName(), threshold);
	}

}
