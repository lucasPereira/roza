package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.List;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.exceptions.InvalidThresholdException;

public class TestsPerClassCriteria implements ThresholdCriteria {

	private Integer threshold;

	public TestsPerClassCriteria(Integer threshold) {
		if (threshold < 1) {
			throw new InvalidThresholdException();
		}
		this.threshold = threshold;
	}

	@Override
	public Boolean shoudlStop(List<Level> levels) {
		if (levels.isEmpty()) {
			return false;
		}
		for (Level level : levels) {
			for (Cluster cluster : level.getClusters()) {
				if (cluster.getTestCases().size() >= threshold) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("%s(%d)", getClass().getSimpleName(), threshold);
	}

}
