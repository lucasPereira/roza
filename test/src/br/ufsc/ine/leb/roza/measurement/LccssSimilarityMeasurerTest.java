package br.ufsc.ine.leb.roza.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.materialization.Junit4WithAssertionsTestCaseMaterializer;
import br.ufsc.ine.leb.roza.materialization.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.utils.FolderUtils;
import br.ufsc.ine.leb.roza.utils.comparator.SimilarityAssessmentComparatorByScoreSourceNameAndTargetName;

class LccssSimilarityMeasurerTest {

	private TestCaseMaterializer materializer;
	private SimilarityMeasurer measurer;

	@BeforeEach
	void setup() {
		new FolderUtils("main/exec/materializer").createEmptyFolder();
		new FolderUtils("main/exec/measurer").createEmptyFolder();
		materializer = new Junit4WithAssertionsTestCaseMaterializer("main/exec/materializer");
		measurer = new LccssSimilarityMeasurer();
	}

	@Test
	void zeroTestCases() {
		SimilarityReport report = measurer.measure(materializer.materialize(List.of()));
		assertEquals(0, report.getAssessments().size());
	}

	@Test
	void oneTestCase() {
		Statement fixture = new Statement("sut(0);");
		Statement assertion = new Statement("assertEquals(0, 0);");
		TestCase testCase = new TestCase("test", List.of(fixture), List.of(assertion));
		MaterializationReport materializationReport = materializer.materialize(List.of(testCase));
		SimilarityReport report = measurer.measure(materializationReport);

		assertEquals(1, report.getAssessments().size());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testCase, report.getAssessments().get(0).getSource());
		assertEquals(testCase, report.getAssessments().get(0).getTarget());
	}

	@Test
	void twoIdenticalTestCasesWithTheSameName() {
		Statement fixture = new Statement("sut(0);");
		Statement assertion = new Statement("assertEquals(0, 0);");
		TestCase testCaseA = new TestCase("test", List.of(fixture), List.of(assertion));
		TestCase testCaseB = new TestCase("test", List.of(fixture), List.of(assertion));
		MaterializationReport materializationReport = materializer.materialize(List.of(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

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
	void twoIdenticalTestCasesWithDifferentNames() {
		Statement fixture = new Statement("sut(0);");
		Statement assertion = new Statement("assertEquals(0, 0);");
		TestCase testCaseA = new TestCase("testA", List.of(fixture), List.of(assertion));
		TestCase testCaseB = new TestCase("testB", List.of(fixture), List.of(assertion));
		MaterializationReport materializationReport = materializer.materialize(List.of(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

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
	void twoDistinctTestCases() {
		Statement fixture = new Statement("new Sut(0).sut();");
		Statement assertion = new Statement("assertEquals(0, 0);");
		TestCase testCaseA = new TestCase("testA", List.of(fixture), List.of());
		TestCase testCaseB = new TestCase("testB", List.of(), List.of(assertion));
		MaterializationReport materializationReport = materializer.materialize(List.of(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

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
