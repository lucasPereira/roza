package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import br.ufsc.ine.leb.roza.exceptions.InvalidThresholdException;

public class ComposedCriteria implements ThresholdCriteria {

	private List<ThresholdCriteria> thresholds;

	public ComposedCriteria(ThresholdCriteria... threshold) {
		this.thresholds = Arrays.asList(threshold);
		if (threshold.length < 2) {
			throw new InvalidThresholdException();
		}
	}

	@Override
	public Boolean shoudlStop(Integer nextLevel, Combination combinationToNext, BigDecimal evaluationToNext) {
		for (ThresholdCriteria threshold : thresholds) {
			if (threshold.shoudlStop(nextLevel, combinationToNext, evaluationToNext)) {
				return true;
			}
		}
		return false;
	}

}
