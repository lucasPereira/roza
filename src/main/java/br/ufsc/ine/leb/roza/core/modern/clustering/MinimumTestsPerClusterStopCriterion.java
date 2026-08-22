package br.ufsc.ine.leb.roza.core.modern.clustering;

public final class MinimumTestsPerClusterStopCriterion implements StopCriterion {

	public static final int DEFAULT = 1;

	private final int minimumTests;

	public MinimumTestsPerClusterStopCriterion(int minimumTests) {
		if (minimumTests < 1) {
			throw new IllegalArgumentException("Minimum tests per cluster must be at least 1.");
		}
		this.minimumTests = minimumTests;
	}

	@Override
	public boolean shouldStop(StopCriterionContext context) {
		return context.currentClusters().stream().allMatch(cluster -> cluster.size() >= minimumTests);
	}
}
