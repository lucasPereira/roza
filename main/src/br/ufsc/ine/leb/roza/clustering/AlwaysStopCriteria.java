package br.ufsc.ine.leb.roza.clustering;

import java.util.List;

class AlwaysStopCriteria implements ThresholdCriteria {

	@Override
	public Boolean shoudlStop(List<Level> levels) {
		return true;
	}

}
