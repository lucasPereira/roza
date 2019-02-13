package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SimilarityReportTest {

	@Test
	void create() throws Exception {
		TestCase testCaseA = new TestCase("test1", Arrays.asList(), Arrays.asList());
		TestCase testCaseB = new TestCase("test1", Arrays.asList(), Arrays.asList());
		SimilarityAssessment assessment = new SimilarityAssessment(testCaseA, testCaseB, BigDecimal.ZERO);
		SimilarityReport report = new SimilarityReport(Arrays.asList(assessment));

		assertEquals(1, report.getAssessments().size());
		assertEquals(assessment, report.getAssessments().get(0));
	}

}
