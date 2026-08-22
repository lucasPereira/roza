package br.ufsc.ine.leb.roza.core.modern.clustering;

public final class TargetClusterCountStopCriterion implements StopCriterion {

	public static final int DEFAULT = 1;

	private final int targetClusterCount;

	public TargetClusterCountStopCriterion(int targetClusterCount) {
		if (targetClusterCount < 1) {
			throw new IllegalArgumentException("Target cluster count must be at least 1.");
		}
		this.targetClusterCount = targetClusterCount;
	}

	@Override
	public boolean shouldStop(StopCriterionContext context) {
		return context.currentClusters().size() <= targetClusterCount;
	}
}
