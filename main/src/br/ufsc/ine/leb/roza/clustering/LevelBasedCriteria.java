package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.exceptions.InvalidThresholdException;

public class LevelBasedCriteria implements ThresholdCriteria {

	private final Integer threshold;

	public LevelBasedCriteria(Integer threshold) {
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
