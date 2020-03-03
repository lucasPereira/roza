package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.List;

import br.ufsc.ine.leb.roza.Cluster;

public class TestsPerClassCriteria implements ThresholdCriteria {

	private Integer threshold;

	public TestsPerClassCriteria(Integer threshold) {
		this.threshold = threshold;
	}

	@Override
	public Boolean shoudlStop(List<Level> levels) {
		if (levels.isEmpty()) {
			return false;
		}
		Level level = levels.get(0);
		for (Cluster cluster : level.getClusters()) {
			if (cluster.getTestCases().size() >= threshold) {
				return true;
			}
		}
		return false;
	}

}
