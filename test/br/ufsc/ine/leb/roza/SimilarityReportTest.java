package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimilarityReportTest {

	private SimilarityAssessment assessment;

	@BeforeEach
	void setup() {
		TestCase testCaseA = new TestCase("test1", Arrays.asList(), Arrays.asList());
		TestCase testCaseB = new TestCase("test1", Arrays.asList(), Arrays.asList());
		assessment = new SimilarityAssessment(testCaseA, testCaseB, BigDecimal.ZERO);
	}

	@Test
	void create() throws Exception {
		SimilarityReport report = new SimilarityReport(Arrays.asList(assessment));
		assertEquals(1, report.getAssessments().size());
		assertEquals(assessment, report.getAssessments().get(0));
	}

}
