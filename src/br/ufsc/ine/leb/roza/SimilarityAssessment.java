package br.ufsc.ine.leb.roza;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.exceptions.InvalidSimilarityScoreException;

public class SimilarityAssessment<T> {

	private T source;
	private T target;
	private BigDecimal score;

	public SimilarityAssessment(T source, T target, BigDecimal score) {
		this.source = source;
		this.target = target;
		this.score = score;
		assertSimilariyScoreIsValid();
	}

	private void assertSimilariyScoreIsValid() {
		Boolean isLessThanZero = score.compareTo(BigDecimal.ZERO) < 0;
		Boolean isGreatherThantOne = score.compareTo(BigDecimal.ONE) > 0;
		if (isLessThanZero || isGreatherThantOne) {
			throw new InvalidSimilarityScoreException();
		}
	}

	public BigDecimal getScore() {
		return score;
	}

	public T getSource() {
		return source;
	}

	public T getTarget() {
		return target;
	}

}
