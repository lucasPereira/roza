package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.exceptions.InvalidThresholdException;

public class TestsPerClassCriteria implements ThresholdCriteria {

	private final Integer threshold;

	public TestsPerClassCriteria(Integer threshold) {
		if (threshold < 1) {
			throw new InvalidThresholdException();
		}
		this.threshold = threshold;
	}

	@Override
	public Boolean shouldStop(Integer nextLevel, Combination combinationToNext, BigDecimal evaluationToNext) {
		int nextSize = combinationToNext.getFirst().getTestCases().size() + combinationToNext.getSecond().getTestCases().size();
		return nextSize > threshold;
	}

	@Override
	public String toString() {
		return String.format("%s(%d)", getClass().getSimpleName(), threshold);
	}

}
