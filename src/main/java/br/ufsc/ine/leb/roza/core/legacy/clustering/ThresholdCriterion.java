package br.ufsc.ine.leb.roza.core.legacy.clustering;

import java.math.BigDecimal;

public interface ThresholdCriterion {

	Boolean shouldStop(Integer nextLevel, Combination combinationToNext, BigDecimal evaluationToNext);

}
