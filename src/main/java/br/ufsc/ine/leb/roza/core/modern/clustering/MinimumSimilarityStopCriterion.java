package br.ufsc.ine.leb.roza.core.modern.clustering;

public final class MinimumSimilarityStopCriterion implements StopCriterion {

	private final double minimumSimilarity;

	public MinimumSimilarityStopCriterion(double minimumSimilarity) {
		if (minimumSimilarity < 0.0 || minimumSimilarity > 1.0) {
			throw new IllegalArgumentException("Minimum similarity must be between 0.0 and 1.0.");
		}
		this.minimumSimilarity = minimumSimilarity;
	}

	@Override
	public boolean shouldStop(StopCriterionContext context) {
		return context.candidate().similarity() <= minimumSimilarity;
	}
}
