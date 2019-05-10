package br.ufsc.ine.leb.roza.measurer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.materializer.OneTestCasePerClassTestCaseMaterializer;
import br.ufsc.ine.leb.roza.materializer.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.utils.FolderUtils;

public class LccssSimilarityMeasurerTest {

	private TestCaseMaterializer materializer;
	private SimilarityMeasurer measurer;

	@BeforeEach
	void setup() {
		new FolderUtils("execution/materializer").createEmptyFolder();
		new FolderUtils("execution/measurer").createEmptyFolder();
		materializer = new OneTestCasePerClassTestCaseMaterializer("execution/materializer");
		measurer = new LccssSimilarityMeasurer();
	}

	@Test
	void zeroTestCases() throws Exception {
		SimilarityReport report = measurer.measure(materializer.materialize(Arrays.asList()));
		assertEquals(0, report.getAssessments().size());
	}

	@Test
	void oneTestCase() throws Exception {
		Statement fixture = new Statement("sut(0);");
		Statement assertion = new Statement("assertEquals(0, 0);");
		TestCase testCase = new TestCase("test", Arrays.asList(fixture), Arrays.asList(assertion));
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCase));
		SimilarityReport report = measurer.measure(materializationReport);

		assertEquals(1, report.getAssessments().size());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testCase, report.getAssessments().get(0).getSource());
		assertEquals(testCase, report.getAssessments().get(0).getTarget());
	}

	@Test
	void twoIdenticalTestCasesWithTheSameName() throws Exception {
		Statement fixture = new Statement("sut(0);");
		Statement assertion = new Statement("assertEquals(0, 0);");
		TestCase testCaseA = new TestCase("test", Arrays.asList(fixture), Arrays.asList(assertion));
		TestCase testCaseB = new TestCase("test", Arrays.asList(fixture), Arrays.asList(assertion));
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport);

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

	@Test
	void twoIdenticalTestCasesWithDifferentNames() throws Exception {
		Statement fixture = new Statement("sut(0);");
		Statement assertion = new Statement("assertEquals(0, 0);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture), Arrays.asList(assertion));
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture), Arrays.asList(assertion));
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport);

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

	@Test
	void twoDistinctTestCases() throws Exception {
		Statement fixture = new Statement("new Sut(0).sut();");
		Statement assertion = new Statement("assertEquals(0, 0);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(), Arrays.asList(assertion));
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport);

		assertEquals(4, report.getAssessments().size());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testCaseA, report.getAssessments().get(0).getSource());
		assertEquals(testCaseA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(1).getScore());
		assertEquals(testCaseB, report.getAssessments().get(1).getSource());
		assertEquals(testCaseB, report.getAssessments().get(1).getTarget());
		assertEquals(BigDecimal.ZERO, report.getAssessments().get(2).getScore());
		assertEquals(testCaseA, report.getAssessments().get(2).getSource());
		assertEquals(testCaseB, report.getAssessments().get(2).getTarget());
		assertEquals(BigDecimal.ZERO, report.getAssessments().get(3).getScore());
		assertEquals(testCaseB, report.getAssessments().get(3).getSource());
		assertEquals(testCaseA, report.getAssessments().get(3).getTarget());
	}

}
