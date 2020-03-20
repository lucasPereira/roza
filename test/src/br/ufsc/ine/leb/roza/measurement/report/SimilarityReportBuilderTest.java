package br.ufsc.ine.leb.roza.measurement.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.measurement.report.AssessmentTestCaseNameComparator;

public class SimilarityReportBuilderTest {

	private TestCase testA;
	private TestCase testB;
	private BigDecimal zeroPointFive;
	private BigDecimal zeroPointSix;

	@BeforeEach
	void setup() {
		testA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		testB = new TestCase("testB", Arrays.asList(), Arrays.asList());
		zeroPointFive = new BigDecimal("0.5");
		zeroPointSix = new BigDecimal("0.6");
	}

	@Test
	void empty() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder().build();
		assertEquals(0, report.getAssessments().size());
	}

	@Test
	void addOneTest() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder().add(testA).build();
		assertEquals(1, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
	}

	@Test
	void addOneTestTwice() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder().add(testA).add(testA).build();
		assertEquals(1, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
	}

	@Test
	void addTheSameTestWithoutScore() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder().add(testA, testA).build();
		assertEquals(1, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
	}

	@Test
	void addTheSameTestWithSymmetricScore() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder().add(testA, testA, zeroPointFive).build();
		assertEquals(1, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
	}

	@Test
	void addTheSameTestWithAssymmetricScore() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder().add(testA, zeroPointSix, testA, zeroPointFive).build();
		assertEquals(1, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
	}

	@Test
	void addTwoDistinctTestsWithoutScore() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder().add(testA, testB).build().sort(new AssessmentTestCaseNameComparator());
		assertEquals(4, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testA, report.getAssessments().get(1).getSource());
		assertEquals(testB, report.getAssessments().get(1).getTarget());
		assertEquals(BigDecimal.ZERO, report.getAssessments().get(1).getScore());
		assertEquals(testB, report.getAssessments().get(2).getSource());
		assertEquals(testA, report.getAssessments().get(2).getTarget());
		assertEquals(BigDecimal.ZERO, report.getAssessments().get(2).getScore());
		assertEquals(testB, report.getAssessments().get(3).getSource());
		assertEquals(testB, report.getAssessments().get(3).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(3).getScore());
	}

	@Test
	void addTestAndAddTwoDistinctTestsWithoutScore() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder().add(testA).add(testA, testB).build().sort(new AssessmentTestCaseNameComparator());
		assertEquals(4, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testA, report.getAssessments().get(1).getSource());
		assertEquals(testB, report.getAssessments().get(1).getTarget());
		assertEquals(BigDecimal.ZERO, report.getAssessments().get(1).getScore());
		assertEquals(testB, report.getAssessments().get(2).getSource());
		assertEquals(testA, report.getAssessments().get(2).getTarget());
		assertEquals(BigDecimal.ZERO, report.getAssessments().get(2).getScore());
		assertEquals(testB, report.getAssessments().get(3).getSource());
		assertEquals(testB, report.getAssessments().get(3).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(3).getScore());
	}

	@Test
	void addTwoDistinctTestsWithoutScoreAndAddTest() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder().add(testA, testB).add(testB).build().sort(new AssessmentTestCaseNameComparator());
		assertEquals(4, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testA, report.getAssessments().get(1).getSource());
		assertEquals(testB, report.getAssessments().get(1).getTarget());
		assertEquals(BigDecimal.ZERO, report.getAssessments().get(1).getScore());
		assertEquals(testB, report.getAssessments().get(2).getSource());
		assertEquals(testA, report.getAssessments().get(2).getTarget());
		assertEquals(BigDecimal.ZERO, report.getAssessments().get(2).getScore());
		assertEquals(testB, report.getAssessments().get(3).getSource());
		assertEquals(testB, report.getAssessments().get(3).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(3).getScore());
	}

	@Test
	void addTwoDistinctTestsIndividually() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder().add(testA).add(testB).build().sort(new AssessmentTestCaseNameComparator());
		assertEquals(4, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testA, report.getAssessments().get(1).getSource());
		assertEquals(testB, report.getAssessments().get(1).getTarget());
		assertEquals(BigDecimal.ZERO, report.getAssessments().get(1).getScore());
		assertEquals(testB, report.getAssessments().get(2).getSource());
		assertEquals(testA, report.getAssessments().get(2).getTarget());
		assertEquals(BigDecimal.ZERO, report.getAssessments().get(2).getScore());
		assertEquals(testB, report.getAssessments().get(3).getSource());
		assertEquals(testB, report.getAssessments().get(3).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(3).getScore());
	}

	@Test
	void addTwoDistinctTestsWithSymmetricScore() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder().add(testA, testB, zeroPointFive).build().sort(new AssessmentTestCaseNameComparator());
		assertEquals(4, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testA, report.getAssessments().get(1).getSource());
		assertEquals(testB, report.getAssessments().get(1).getTarget());
		assertEquals(zeroPointFive, report.getAssessments().get(1).getScore());
		assertEquals(testB, report.getAssessments().get(2).getSource());
		assertEquals(testA, report.getAssessments().get(2).getTarget());
		assertEquals(zeroPointFive, report.getAssessments().get(2).getScore());
		assertEquals(testB, report.getAssessments().get(3).getSource());
		assertEquals(testB, report.getAssessments().get(3).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(3).getScore());
	}

	@Test
	void addTwoDistinctTestsWithAssymmetricScore() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder().add(testA, zeroPointFive, testB, zeroPointSix).build().sort(new AssessmentTestCaseNameComparator());
		assertEquals(4, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testA, report.getAssessments().get(1).getSource());
		assertEquals(testB, report.getAssessments().get(1).getTarget());
		assertEquals(zeroPointFive, report.getAssessments().get(1).getScore());
		assertEquals(testB, report.getAssessments().get(2).getSource());
		assertEquals(testA, report.getAssessments().get(2).getTarget());
		assertEquals(zeroPointSix, report.getAssessments().get(2).getScore());
		assertEquals(testB, report.getAssessments().get(3).getSource());
		assertEquals(testB, report.getAssessments().get(3).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(3).getScore());
	}

	@Test
	void addTwoDistinctTestsWithAssymmetricScoreReversed() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder().add(testB, zeroPointFive, testA, zeroPointSix).build().sort(new AssessmentTestCaseNameComparator());
		assertEquals(4, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testA, report.getAssessments().get(1).getSource());
		assertEquals(testB, report.getAssessments().get(1).getTarget());
		assertEquals(zeroPointSix, report.getAssessments().get(1).getScore());
		assertEquals(testB, report.getAssessments().get(2).getSource());
		assertEquals(testA, report.getAssessments().get(2).getTarget());
		assertEquals(zeroPointFive, report.getAssessments().get(2).getScore());
		assertEquals(testB, report.getAssessments().get(3).getSource());
		assertEquals(testB, report.getAssessments().get(3).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(3).getScore());
	}

	@Test
	void addTwoDistinctTestsWithSymmetricScoreReplacedByNoScore() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder().add(testA, testB, zeroPointFive).add(testA, testB).build().sort(new AssessmentTestCaseNameComparator());
		assertEquals(4, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testA, report.getAssessments().get(1).getSource());
		assertEquals(testB, report.getAssessments().get(1).getTarget());
		assertEquals(BigDecimal.ZERO, report.getAssessments().get(1).getScore());
		assertEquals(testB, report.getAssessments().get(2).getSource());
		assertEquals(testA, report.getAssessments().get(2).getTarget());
		assertEquals(BigDecimal.ZERO, report.getAssessments().get(2).getScore());
		assertEquals(testB, report.getAssessments().get(3).getSource());
		assertEquals(testB, report.getAssessments().get(3).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(3).getScore());
	}

	@Test
	void addTwoDistinctTestsWithAssymmetricScoreReplacedBySymmetricScore() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder().add(testA, zeroPointFive, testB, zeroPointSix).add(testA, testB, zeroPointFive).build().sort(new AssessmentTestCaseNameComparator());
		assertEquals(4, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testA, report.getAssessments().get(1).getSource());
		assertEquals(testB, report.getAssessments().get(1).getTarget());
		assertEquals(zeroPointFive, report.getAssessments().get(1).getScore());
		assertEquals(testB, report.getAssessments().get(2).getSource());
		assertEquals(testA, report.getAssessments().get(2).getTarget());
		assertEquals(zeroPointFive, report.getAssessments().get(2).getScore());
		assertEquals(testB, report.getAssessments().get(3).getSource());
		assertEquals(testB, report.getAssessments().get(3).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(3).getScore());
	}

}
