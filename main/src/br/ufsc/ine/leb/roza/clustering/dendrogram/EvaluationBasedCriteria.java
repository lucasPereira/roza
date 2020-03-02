package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.math.BigDecimal;
import java.util.List;

class EvaluationBasedCriteria implements ThresholdCriteria {

	private BigDecimal stop;

	public EvaluationBasedCriteria(BigDecimal stop) {
		this.stop = stop;
	}

	@Override
	public Boolean shoudlStop(List<Level> levels) {
		if (levels.isEmpty()) {
			return true;
		}
		BigDecimal evaluationToNextLevel = levels.get(levels.size() - 1).getEvaluationToNextLevel();
		return stop.compareTo(evaluationToNextLevel) >= 0;
	}

}
