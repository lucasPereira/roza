package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;

class AlwaysStopCriterion implements ThresholdCriterion {

	@Override
	public Boolean shouldStop(Integer nextLevel, Combination combinationToNext, BigDecimal evaluationToNext) {
		return true;
	}

}
