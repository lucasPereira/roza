package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimilarityReportTest {

	private SimilarityAssessment<TestCase> assessmentAWithA;
	private SimilarityAssessment<TestCase> assessmentAWithB;

	@BeforeEach
	void setup() {
		TestCase testCaseA = new TestCase("test1", Arrays.asList(), Arrays.asList());
		TestCase testCaseB = new TestCase("test1", Arrays.asList(), Arrays.asList());
		assessmentAWithA = new SimilarityAssessment<TestCase>(testCaseA, testCaseA, BigDecimal.ONE);
		assessmentAWithB = new SimilarityAssessment<TestCase>(testCaseA, testCaseB, BigDecimal.ZERO);
	}

	@Test
	void create() throws Exception {
		SimilarityReport<TestCase> report = new SimilarityReport<TestCase>(
				Arrays.asList(assessmentAWithA, assessmentAWithB));
		assertEquals(2, report.getAssessments().size());
		assertEquals(assessmentAWithA, report.getAssessments().get(0));
		assertEquals(assessmentAWithB, report.getAssessments().get(1));
	}

	@Test
	void removeReflexiveAssessments() throws Exception {
		SimilarityReport<TestCase> report = new SimilarityReport<TestCase>(
				Arrays.asList(assessmentAWithA, assessmentAWithB));
		report.removeReflexiveAssessments();
		assertEquals(1, report.getAssessments().size());
		assertEquals(assessmentAWithB, report.getAssessments().get(0));
	}

}
