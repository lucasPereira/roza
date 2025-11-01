package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.exceptions.InvalidThresholdException;

public class SimilarityBasedCriteria implements ThresholdCriteria {

	private final BigDecimal threshold;

	public SimilarityBasedCriteria(BigDecimal threshold) {
		if (BigDecimal.ZERO.compareTo(threshold) > 0 || BigDecimal.ONE.compareTo(threshold) < 0) {
			throw new InvalidThresholdException();
		}
		this.threshold = threshold;
	}

	@Override
	public Boolean shouldStop(Integer nextLevel, Combination combinationToNext, BigDecimal evaluationToNext) {
		return threshold.compareTo(evaluationToNext) >= 0;
	}

	@Override
	public String toString() {
		return String.format("%s(%s)", getClass().getSimpleName(), threshold);
	}

}
