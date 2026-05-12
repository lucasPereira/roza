package br.ufsc.ine.leb.roza.core.modern.clustering;

import java.util.List;
import java.util.Objects;

public final class CompositeStopCriterion implements StopCriterion {

	private final List<StopCriterion> criteria;

	public CompositeStopCriterion(List<StopCriterion> criteria) {
		this.criteria = List.copyOf(Objects.requireNonNull(criteria));
	}

	@Override
	public boolean shouldStop(StopCriterionContext context) {
		for (StopCriterion criterion : criteria) {
			if (criterion.shouldStop(context)) {
				return true;
			}
		}
		return false;
	}
}
