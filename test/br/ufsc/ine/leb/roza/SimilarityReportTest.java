package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimilarityReportTest {

	private SimilarityAssessment assessmentAWithA;
	private SimilarityAssessment assessmentAWithB;

	@BeforeEach
	void setup() {
		TestCase testCaseA = new TestCase("test1", Arrays.asList(), Arrays.asList());
		TestCase testCaseB = new TestCase("test1", Arrays.asList(), Arrays.asList());
		assessmentAWithA = new SimilarityAssessment(testCaseA, testCaseA, BigDecimal.ONE);
		assessmentAWithB = new SimilarityAssessment(testCaseA, testCaseB, BigDecimal.ZERO);
	}

	@Test
	void create() throws Exception {
		SimilarityReport report = new SimilarityReport(Arrays.asList(assessmentAWithA, assessmentAWithB));
		assertEquals(2, report.getAssessments().size());
		assertEquals(assessmentAWithA, report.getAssessments().get(0));
		assertEquals(assessmentAWithB, report.getAssessments().get(1));
	}

	@Test
	void removeReflexiveAssessments() throws Exception {
		SimilarityReport report = new SimilarityReport(Arrays.asList(assessmentAWithA, assessmentAWithB));
		report.removeReflexiveAssessments();
		assertEquals(1, report.getAssessments().size());
		assertEquals(assessmentAWithB, report.getAssessments().get(0));
	}

}
