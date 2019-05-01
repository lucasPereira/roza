package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimilarityReportTest {

	private SimilarityAssessment assessmentAA;
	private SimilarityAssessment assessmentAB;
	private SimilarityAssessment assessmentAC;
	private SimilarityAssessment assessmentBA;
	private SimilarityAssessment assessmentBB;
	private SimilarityAssessment assessmentBC;
	private SimilarityAssessment assessmentCA;
	private SimilarityAssessment assessmentCB;
	private SimilarityAssessment assessmentCC;

	@BeforeEach
	void setup() {
		TestCase testCaseA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(), Arrays.asList());
		TestCase testCaseC = new TestCase("testC", Arrays.asList(), Arrays.asList());
		assessmentAA = new SimilarityAssessment(testCaseA, testCaseA, BigDecimal.ONE);
		assessmentAB = new SimilarityAssessment(testCaseA, testCaseB, new BigDecimal("0.2"));
		assessmentAC = new SimilarityAssessment(testCaseA, testCaseC, new BigDecimal("0.9"));
		assessmentBA = new SimilarityAssessment(testCaseB, testCaseA, new BigDecimal("0.3"));
		assessmentBB = new SimilarityAssessment(testCaseB, testCaseB, BigDecimal.ONE);
		assessmentBC = new SimilarityAssessment(testCaseB, testCaseC, new BigDecimal("0.5"));
		assessmentCA = new SimilarityAssessment(testCaseC, testCaseA, new BigDecimal("0.8"));
		assessmentCB = new SimilarityAssessment(testCaseC, testCaseB, new BigDecimal("0.5"));
		assessmentCC = new SimilarityAssessment(testCaseC, testCaseC, BigDecimal.ONE);
	}

	@Test
	void create() throws Exception {
		SimilarityReport report = new SimilarityReport(Arrays.asList(assessmentAA, assessmentAB));
		assertEquals(2, report.getAssessments().size());
		assertEquals(assessmentAA, report.getAssessments().get(0));
		assertEquals(assessmentAB, report.getAssessments().get(1));
	}

	@Test
	void removeReflexiveAssessments() throws Exception {
		SimilarityReport report = new SimilarityReport(Arrays.asList(assessmentAA, assessmentAB));
		report.removeReflexiveAssessments();
		assertEquals(1, report.getAssessments().size());
		assertEquals(assessmentAB, report.getAssessments().get(0));
	}

	@Test
	void order() throws Exception {
		SimilarityReport report = new SimilarityReport(Arrays.asList(assessmentBB, assessmentAA, assessmentAB, assessmentAC, assessmentBA, assessmentBC, assessmentCA, assessmentCB, assessmentCC));
		assertEquals(9, report.getAssessments().size());
		assertEquals(assessmentAA, report.getAssessments().get(0));
		assertEquals(assessmentBB, report.getAssessments().get(1));
		assertEquals(assessmentCC, report.getAssessments().get(2));
		assertEquals(assessmentAC, report.getAssessments().get(3));
		assertEquals(assessmentCA, report.getAssessments().get(4));
		assertEquals(assessmentBC, report.getAssessments().get(5));
		assertEquals(assessmentCB, report.getAssessments().get(6));
		assertEquals(assessmentBA, report.getAssessments().get(7));
		assertEquals(assessmentAB, report.getAssessments().get(8));
	}

}
