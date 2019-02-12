package br.ufsc.ine.leb.roza;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.exceptions.InvalidEvaluationValueException;

public class SimilarityAssessment {

	private TestCase source;
	private TestCase target;
	private BigDecimal evaluation;

	public SimilarityAssessment(TestCase source, TestCase target, BigDecimal evaluation) {
		this.source = source;
		this.target = target;
		this.evaluation = evaluation;
		assertEvaluationValueIsValid();
	}

	private void assertEvaluationValueIsValid() {
		Boolean isLessThanZero = evaluation.compareTo(BigDecimal.ZERO) < 0;
		Boolean isGreatherThantOne = evaluation.compareTo(BigDecimal.ONE) > 0;
		if (isLessThanZero || isGreatherThantOne) {
			throw new InvalidEvaluationValueException();
		}
	}

	public BigDecimal getEvaluation() {
		return evaluation;
	}

	public TestCase getSource() {
		return source;
	}

	public TestCase getTarget() {
		return target;
	}

}
