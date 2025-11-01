package br.ufsc.ine.leb.roza;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.exceptions.InvalidSimilarityScoreException;

public class SimilarityAssessment {

	private final TestCase source;
	private final TestCase target;
	private final BigDecimal score;

	SimilarityAssessment(TestCase source, TestCase target, BigDecimal score) {
		this.source = source;
		this.target = target;
		this.score = score;
		assertSimilarityScoreIsValid();
	}

	private void assertSimilarityScoreIsValid() {
		Boolean isLessThanZero = score.compareTo(BigDecimal.ZERO) < 0;
		Boolean isGreaterThantOne = score.compareTo(BigDecimal.ONE) > 0;
		if (isLessThanZero || isGreaterThantOne) {
			throw new InvalidSimilarityScoreException();
		}
	}

	public BigDecimal getScore() {
		return score;
	}

	public TestCase getSource() {
		return source;
	}

	public TestCase getTarget() {
		return target;
	}

	@Override
	public String toString() {
		return String.format("(%s, %s) : %s", source, target, score);
	}

}
