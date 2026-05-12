package br.ufsc.ine.leb.roza.core.modern.clustering;

public interface StopCriterion {

	boolean shouldStop(StopCriterionContext context);
}
