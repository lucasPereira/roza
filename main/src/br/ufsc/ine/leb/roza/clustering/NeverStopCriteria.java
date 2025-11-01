package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;

public class NeverStopCriteria implements ThresholdCriteria {

	@Override
	public Boolean shouldStop(Integer nextLevel, Combination combinationToNext, BigDecimal evaluationToNext) {
		return false;
	}

}
