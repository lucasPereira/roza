package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.List;

class FixedInterationsCriteria implements ThresholdCriteria {

	private Integer threshold;

	public FixedInterationsCriteria(Integer threshold) {
		this.threshold = threshold;
	}

	@Override
	public Boolean shoudlStop(List<Level> levels) {
		return levels.size() >= threshold;
	}

}
