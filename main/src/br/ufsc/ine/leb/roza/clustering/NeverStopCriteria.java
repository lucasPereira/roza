package br.ufsc.ine.leb.roza.clustering;

import java.util.List;

public class NeverStopCriteria implements ThresholdCriteria {

	@Override
	public Boolean shoudlStop(List<Level> levels) {
		return false;
	}

}
