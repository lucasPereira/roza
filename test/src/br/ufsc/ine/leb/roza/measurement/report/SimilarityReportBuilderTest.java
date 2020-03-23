package br.ufsc.ine.leb.roza.measurement.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.exceptions.MissingAssessmentException;
import br.ufsc.ine.leb.roza.exceptions.PotentialErrorProneOperationException;

public class SimilarityReportBuilderTest {

	private TestCase testA;
	private TestCase testB;
	private BigDecimal dotFive;
	private BigDecimal dotSix;
	private SimilarityReportBuilder symmetric;
	private SimilarityReportBuilder assymmetric;

	@BeforeEach
	void setup() {
		testA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		testB = new TestCase("testB", Arrays.asList(), Arrays.asList());
		dotFive = new BigDecimal("0.5");
		dotSix = new BigDecimal("0.6");
		symmetric = new SimilarityReportBuilder(true);
		assymmetric = new SimilarityReportBuilder(false);
	}

	@Test
	void emptySymmetric() throws Exception {
		SimilarityReport report = symmetric.build();
		assertEquals(0, report.getAssessments().size());
	}

	@Test
	void emptyAssymmetric() throws Exception {
		SimilarityReport report = assymmetric.build();
		assertEquals(0, report.getAssessments().size());
	}

	@Test
	void addOneTestSymmetric() throws Exception {
		SimilarityReport report = symmetric.add(testA).build();
		assertEquals(1, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
	}

	@Test
	void addOneTestAssymmetric() throws Exception {
		SimilarityReport report = assymmetric.add(testA).build();
		assertEquals(1, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
	}

	@Test
	void addTwoTestsSymmetric() throws Exception {
		SimilarityReport report = symmetric.add(testA).add(testB).complete().build().sort(new AssessmentTestCaseNameComparator());
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
	void addTwoTestsAssymmetric() throws Exception {
		SimilarityReport report = assymmetric.add(testA).add(testB).complete().build().sort(new AssessmentTestCaseNameComparator());
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
	void addPairOfTwoTestsSymmetric() throws Exception {
		SimilarityReport report = symmetric.add(testA, testB, dotFive).build().sort(new AssessmentTestCaseNameComparator());
		assertEquals(4, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testA, report.getAssessments().get(1).getSource());
		assertEquals(testB, report.getAssessments().get(1).getTarget());
		assertEquals(dotFive, report.getAssessments().get(1).getScore());
		assertEquals(testB, report.getAssessments().get(2).getSource());
		assertEquals(testA, report.getAssessments().get(2).getTarget());
		assertEquals(dotFive, report.getAssessments().get(2).getScore());
		assertEquals(testB, report.getAssessments().get(3).getSource());
		assertEquals(testB, report.getAssessments().get(3).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(3).getScore());
	}

	@Test
	void addPairOfTwoTestsAssymmetric() throws Exception {
		SimilarityReport report = assymmetric.add(testA, testB, dotFive).add(testB, testA, dotSix).build().sort(new AssessmentTestCaseNameComparator());
		assertEquals(4, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testA, report.getAssessments().get(1).getSource());
		assertEquals(testB, report.getAssessments().get(1).getTarget());
		assertEquals(dotFive, report.getAssessments().get(1).getScore());
		assertEquals(testB, report.getAssessments().get(2).getSource());
		assertEquals(testA, report.getAssessments().get(2).getTarget());
		assertEquals(dotSix, report.getAssessments().get(2).getScore());
		assertEquals(testB, report.getAssessments().get(3).getSource());
		assertEquals(testB, report.getAssessments().get(3).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(3).getScore());
	}

	@Test
	void addTestTwice() throws Exception {
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			symmetric.add(testA).add(testA);
		});
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			assymmetric.add(testA).add(testA);
		});
	}

	@Test
	void addTestWithItself() throws Exception {
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			symmetric.add(testA, testA, dotFive);
		});
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			assymmetric.add(testA, testA, dotFive);
		});
	}

	@Test
	void addTestAndPairWithItseflAndAnotherTest() throws Exception {
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			symmetric.add(testA).add(testA, testB, dotFive);
		});
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			symmetric.add(testA, testB, dotFive).add(testB);
		});
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			assymmetric.add(testA).add(testA, testB, dotFive);
		});
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			assymmetric.add(testA, testB, dotFive).add(testB);
		});
	}

	@Test
	void addSamePairTwice() throws Exception {
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			symmetric.add(testA, testB, dotFive).add(testA, testB, dotSix);
		});
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			symmetric.add(testA, testB, dotFive).add(testB, testA, dotSix);
		});
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			assymmetric.add(testA, testB, dotFive).add(testA, testB, dotSix);
		});
	}

	@Test
	void addTwoTestsSymmetricWithoutComplete() throws Exception {
		assertThrows(MissingAssessmentException.class, () -> {
			symmetric.add(testA).add(testB).build();
		});
	}

	@Test
	void addTwoTestsAssymmetricWithoutComplete() throws Exception {
		assertThrows(MissingAssessmentException.class, () -> {
			assymmetric.add(testA).add(testB).build();
		});
	}

}
