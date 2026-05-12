package br.ufsc.ine.leb.roza.core.modern.clustering;

public final class MaxTestsPerClusterStopCriterion implements StopCriterion {

	private final int maxTests;

	public MaxTestsPerClusterStopCriterion(int maxTests) {
		if (maxTests < 1) {
			throw new IllegalArgumentException("Maximum tests per cluster must be at least 1.");
		}
		this.maxTests = maxTests;
	}

	@Override
	public boolean shouldStop(StopCriterionContext context) {
		return context.candidate().mergedCluster().size() > maxTests;
	}
}
