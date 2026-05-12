package br.ufsc.ine.leb.roza.core.modern.clustering;

public final class MaxLevelStopCriterion implements StopCriterion {

	private final int maxLevel;

	public MaxLevelStopCriterion(int maxLevel) {
		if (maxLevel < 0) {
			throw new IllegalArgumentException("Maximum level must not be negative.");
		}
		this.maxLevel = maxLevel;
	}

	@Override
	public boolean shouldStop(StopCriterionContext context) {
		return context.nextLevel() > maxLevel;
	}
}
