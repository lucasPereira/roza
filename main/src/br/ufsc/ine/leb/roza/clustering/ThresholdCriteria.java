package br.ufsc.ine.leb.roza.clustering;

import java.util.List;

public interface ThresholdCriteria {

	Boolean shoudlStop(List<Level> levels);

}