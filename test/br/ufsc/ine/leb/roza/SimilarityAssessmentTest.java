package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.exceptions.InvalidEvaluationValueException;

public class SimilarityAssessmentTest {

	private TestCase testCaseA;
	private TestCase testCaseB;

	@BeforeEach
	void setup() {
		testCaseA = new TestCase("test1", Arrays.asList(), Arrays.asList());
		testCaseB = new TestCase("test2", Arrays.asList(), Arrays.asList());
	}

	@Test
	void evaluationZero() throws Exception {
		SimilarityAssessment evaluation = new SimilarityAssessment(testCaseA, testCaseB, BigDecimal.ZERO);
		assertEquals(BigDecimal.ZERO, evaluation.getEvaluation());
		assertEquals(testCaseA, evaluation.getSource());
		assertEquals(testCaseB, evaluation.getTarget());
	}

	@Test
	void evaluationOne() throws Exception {
		SimilarityAssessment evaluation = new SimilarityAssessment(testCaseA, testCaseB, BigDecimal.ONE);
		assertEquals(BigDecimal.ONE, evaluation.getEvaluation());
		assertEquals(testCaseA, evaluation.getSource());
		assertEquals(testCaseB, evaluation.getTarget());
	}

	@Test
	void evaluationLessThanZero() throws Exception {
		assertThrows(InvalidEvaluationValueException.class, () -> {
			new SimilarityAssessment(testCaseA, testCaseB, new BigDecimal(-0.5));
		});
	}

	@Test
	void evaluationGreaterThanOne() throws Exception {
		assertThrows(InvalidEvaluationValueException.class, () -> {
			new SimilarityAssessment(testCaseA, testCaseB, new BigDecimal(1.5));
		});
	}

}
