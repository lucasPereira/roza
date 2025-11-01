package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;
import java.util.List;

import br.ufsc.ine.leb.roza.exceptions.InvalidThresholdException;

public class ComposedCriteria implements ThresholdCriteria {

	private final List<ThresholdCriteria> thresholds;

	public ComposedCriteria(ThresholdCriteria... threshold) {
		this.thresholds = List.of(threshold);
		if (threshold.length < 2) {
			throw new InvalidThresholdException();
		}
	}

	@Override
	public Boolean shouldStop(Integer nextLevel, Combination combinationToNext, BigDecimal evaluationToNext) {
		for (ThresholdCriteria threshold : thresholds) {
			if (threshold.shouldStop(nextLevel, combinationToNext, evaluationToNext)) {
				return true;
			}
		}
		return false;
	}

}
