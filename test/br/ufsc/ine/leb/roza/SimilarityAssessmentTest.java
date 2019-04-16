package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.exceptions.InvalidSimilarityScoreException;

public class SimilarityAssessmentTest {

	private TestCase testCaseA;
	private TestCase testCaseB;

	@BeforeEach
	void setup() {
		testCaseA = new TestCase("test1", Arrays.asList(), Arrays.asList());
		testCaseB = new TestCase("test2", Arrays.asList(), Arrays.asList());
	}

	@Test
	void minimumScore() throws Exception {
		SimilarityAssessment<TestCase> assessment = new SimilarityAssessment<TestCase>(testCaseA, testCaseB, BigDecimal.ZERO);
		assertEquals(BigDecimal.ZERO, assessment.getScore());
		assertEquals(testCaseA, assessment.getSource());
		assertEquals(testCaseB, assessment.getTarget());
	}

	@Test
	void maximumScore() throws Exception {
		SimilarityAssessment<TestCase> assessment = new SimilarityAssessment<TestCase>(testCaseA, testCaseB, BigDecimal.ONE);
		assertEquals(BigDecimal.ONE, assessment.getScore());
		assertEquals(testCaseA, assessment.getSource());
		assertEquals(testCaseB, assessment.getTarget());
	}

	@Test
	void scoreLessThanZero() throws Exception {
		assertThrows(InvalidSimilarityScoreException.class, () -> {
			new SimilarityAssessment<TestCase>(testCaseA, testCaseB, new BigDecimal(-0.5));
		});
	}

	@Test
	void scoreGreaterThanOne() throws Exception {
		assertThrows(InvalidSimilarityScoreException.class, () -> {
			new SimilarityAssessment<TestCase>(testCaseA, testCaseB, new BigDecimal(1.5));
		});
	}

}
