package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;

class AlwaysStopCriteria implements ThresholdCriteria {

	@Override
	public Boolean shoudlStop(Integer nextLevel, Combination combinationToNext, BigDecimal evaluationToNext) {
		return true;
	}

}
