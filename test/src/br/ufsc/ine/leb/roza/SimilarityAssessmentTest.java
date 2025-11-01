package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.exceptions.InvalidSimilarityScoreException;

class SimilarityAssessmentTest {

	private TestCase testCaseA;
	private TestCase testCaseB;

	@BeforeEach
	void setup() {
		testCaseA = new TestCase("test1", List.of(), List.of());
		testCaseB = new TestCase("test2", List.of(), List.of());
	}

	@Test
	void minimumScore() {
		SimilarityAssessment assessment = new SimilarityAssessment(testCaseA, testCaseB, BigDecimal.ZERO);
		assertEquals(BigDecimal.ZERO, assessment.getScore());
		assertEquals(testCaseA, assessment.getSource());
		assertEquals(testCaseB, assessment.getTarget());
	}

	@Test
	void maximumScore() {
		SimilarityAssessment assessment = new SimilarityAssessment(testCaseA, testCaseB, BigDecimal.ONE);
		assertEquals(BigDecimal.ONE, assessment.getScore());
		assertEquals(testCaseA, assessment.getSource());
		assertEquals(testCaseB, assessment.getTarget());
	}

	@Test
	void scoreLessThanZero() {
		assertThrows(InvalidSimilarityScoreException.class, () -> new SimilarityAssessment(testCaseA, testCaseB, BigDecimal.valueOf(-0.5)));
	}

	@Test
	void scoreGreaterThanOne() {
		assertThrows(InvalidSimilarityScoreException.class, () -> new SimilarityAssessment(testCaseA, testCaseB, new BigDecimal("1.5")));
	}

}
