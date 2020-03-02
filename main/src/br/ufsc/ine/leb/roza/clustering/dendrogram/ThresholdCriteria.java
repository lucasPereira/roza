package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.List;

public interface ThresholdCriteria {

	Boolean shoudlStop(List<Level> levels);

}
