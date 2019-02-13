package br.ufsc.ine.leb.roza.measurer;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.measurer.JplagSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurer.SimilarityMeasurer;

public class JplagSimilarityMeasurerTest {

	@Test
	void zeroTestCases() throws Exception {
		SimilarityMeasurer measurer = new JplagSimilarityMeasurer();
		SimilarityReport report = measurer.measure(Arrays.asList());

		assertEquals(0, report.getAssessments().size());
	}

	@Test
	void oneTestCase() throws Exception {
		Statement fixture = new Statement("sut(0);");
		Statement assertion = new Statement("assertEquals(0, 0)");
		TestCase testCase = new TestCase("test", Arrays.asList(fixture), Arrays.asList(assertion));
		SimilarityMeasurer measurer = new JplagSimilarityMeasurer();
		SimilarityReport report = measurer.measure(Arrays.asList(testCase));

		assertEquals(1, report.getAssessments().size());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testCase, report.getAssessments().get(0).getSource());
		assertEquals(testCase, report.getAssessments().get(0).getTarget());
	}

	@Test
	void twoIdenticalTestCases() throws Exception {
		Statement fixture = new Statement("sut(0);");
		Statement assertion = new Statement("assertEquals(0, 0)");
		TestCase testCaseA = new TestCase("test", Arrays.asList(fixture), Arrays.asList(assertion));
		TestCase testCaseB = new TestCase("test", Arrays.asList(fixture), Arrays.asList(assertion));
		SimilarityMeasurer measurer = new JplagSimilarityMeasurer();
		SimilarityReport report = measurer.measure(Arrays.asList(testCaseA, testCaseB));

		assertEquals(4, report.getAssessments().size());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testCaseA, report.getAssessments().get(0).getSource());
		assertEquals(testCaseA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(1).getScore());
		assertEquals(testCaseA, report.getAssessments().get(1).getSource());
		assertEquals(testCaseB, report.getAssessments().get(1).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(2).getScore());
		assertEquals(testCaseB, report.getAssessments().get(2).getSource());
		assertEquals(testCaseA, report.getAssessments().get(2).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(3).getScore());
		assertEquals(testCaseB, report.getAssessments().get(3).getSource());
		assertEquals(testCaseB, report.getAssessments().get(3).getTarget());
	}

}
