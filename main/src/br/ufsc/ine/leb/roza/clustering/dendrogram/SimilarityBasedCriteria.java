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
		for (Level level : levels) {
			if (level.hasNextLevel()) {
				BigDecimal evaluationToNextLevel = level.getEvaluationToNextLevel();
				if (threshold.compareTo(evaluationToNextLevel) >= 0) {
					return true;
				}
			}
		}
		return false;
	}

}
