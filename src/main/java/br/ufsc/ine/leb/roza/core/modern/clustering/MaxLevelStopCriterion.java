package br.ufsc.ine.leb.roza.core.modern.clustering;

public final class MaxLevelStopCriterion implements StopCriterion {

	public static final int DEFAULT = 1;

	private final int maxLevel;

	public MaxLevelStopCriterion(int maxLevel) {
		if (maxLevel < DEFAULT) {
			throw new IllegalArgumentException("Maximum merge level must be at least " + DEFAULT + ".");
		}
		this.maxLevel = maxLevel;
	}

	@Override
	public boolean shouldStop(StopCriterionContext context) {
		return context.nextLevel() >= maxLevel;
	}
}
