package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.List;

public class TestsPerClassCriteria implements ThresholdCriteria {

	private Integer threshold;

	public TestsPerClassCriteria(Integer threshold) {
		this.threshold = threshold;}

	@Override
	public Boolean shoudlStop(List<Level> levels) {
		if (levels.isEmpty() || levels.get(0).getClusters().isEmpty() || levels.get(0).getClusters().iterator().next().getTestCases().size() < threshold) {
			return false;
		}
		return true;
	}

}
