package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;
import java.util.List;

import br.ufsc.ine.leb.roza.exceptions.InvalidThresholdException;

public class ComposedCriterion implements ThresholdCriterion {

	private final List<ThresholdCriterion> thresholds;

	public ComposedCriterion(ThresholdCriterion... threshold) {
		this.thresholds = List.of(threshold);
		if (threshold.length < 2) {
			throw new InvalidThresholdException();
		}
	}

	@Override
	public Boolean shouldStop(Integer nextLevel, Combination combinationToNext, BigDecimal evaluationToNext) {
		for (ThresholdCriterion threshold : thresholds) {
			if (threshold.shouldStop(nextLevel, combinationToNext, evaluationToNext)) {
				return true;
			}
		}
		return false;
	}

}
