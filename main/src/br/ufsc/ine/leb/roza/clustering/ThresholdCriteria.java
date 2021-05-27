package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;

public interface ThresholdCriteria {

	Boolean shoudlStop(Integer nextLevel, Combination combinationToNext, BigDecimal evaluationToNext);

}
