package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.List;

class NeverStopCriteria implements ThresholdCriteria {

	@Override
	public Boolean shoudlStop(List<Level> levels) {
		return false;
	}

}
