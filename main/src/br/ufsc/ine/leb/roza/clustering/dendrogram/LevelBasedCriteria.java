package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.List;

import br.ufsc.ine.leb.roza.exceptions.InvalidThresholdException;

class LevelBasedCriteria implements ThresholdCriteria {

	private Integer threshold;

	public LevelBasedCriteria(Integer threshold) {
		if (threshold < 0) {
			throw new InvalidThresholdException();
		}
		this.threshold = threshold;
	}

	@Override
	public Boolean shoudlStop(List<Level> levels) {
		return levels.size() >= threshold;
	}

}