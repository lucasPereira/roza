package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.math.BigDecimal;
import java.util.List;

import br.ufsc.ine.leb.roza.exceptions.InvalidThresholdException;

class SimilarityBasedCriteria implements ThresholdCriteria {

	private BigDecimal threshold;

	public SimilarityBasedCriteria(BigDecimal threshold) {
		if (BigDecimal.ZERO.compareTo(threshold) > 0 || BigDecimal.ONE.compareTo(threshold) < 0) {
			throw new InvalidThresholdException();
		}
		this.threshold = threshold;
	}

	@Override
	public Boolean shoudlStop(List<Level> levels) {
		if (levels.isEmpty()) {
			return false;
		}
		BigDecimal evaluationToNextLevel = levels.get(levels.size() - 1).getEvaluationToNextLevel();
		return threshold.compareTo(evaluationToNextLevel) >= 0;
	}

}
