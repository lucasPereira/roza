package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;

public class NeverStopCriterion implements ThresholdCriterion {

	@Override
	public Boolean shouldStop(Integer nextLevel, Combination combinationToNext, BigDecimal evaluationToNext) {
		return false;
	}

}
