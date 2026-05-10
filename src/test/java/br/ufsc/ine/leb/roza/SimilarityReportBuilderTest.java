package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.exceptions.MissingAssessmentException;
import br.ufsc.ine.leb.roza.exceptions.PotentialErrorProneOperationException;
import br.ufsc.ine.leb.roza.utils.comparator.SimilarityAssessmentComparatorBySourceAndTargetNames;

class SimilarityReportBuilderTest {

	private TestCase testA;
	private TestCase testB;
	private BigDecimal dotFive;
	private BigDecimal dotSix;
	private SimilarityReportBuilder symmetric;
	private SimilarityReportBuilder asymmetric;

	@BeforeEach
	void setup() {
		testA = new TestCase("testA", List.of(), List.of());
		testB = new TestCase("testB", List.of(), List.of());
		dotFive = new BigDecimal("0.5");
		dotSix = new BigDecimal("0.6");
		symmetric = new SimilarityReportBuilder(true);
		asymmetric = new SimilarityReportBuilder(false);
	}

	@Test
	void emptySymmetric() {
		SimilarityReport report = symmetric.build();
		assertEquals(0, report.getAssessments().size());
	}

	@Test
	void emptyAsymmetric() {
		SimilarityReport report = asymmetric.build();
		assertEquals(0, report.getAssessments().size());
	}

	@Test
	void addOneTestSymmetric() {
		SimilarityReport report = symmetric.add(testA).build();
		assertEquals(1, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
	}

	@Test
	void addOneTestAsymmetric() {
		SimilarityReport report = asymmetric.add(testA).build();
		assertEquals(1, report.getAssessments().size());
		assertEquals(testA, report.getAssessments().get(0).getSource());
		assertEquals(testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
	}

	@Test
	void addTwoTestsSymmetric() {
		SimilarityReport report = symmetric.add(testA).add(testB).complete().build().sort(new SimilarityAssessmentComparatorBySourceAndTargetNames());
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
	void addTwoTestsAsymmetric() {
		SimilarityReport report = asymmetric.add(testA).add(testB).complete().build().sort(new SimilarityAssessmentComparatorBySourceAndTargetNames());
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
	void addPairOfTwoTestsSymmetric() {
		SimilarityReport report = symmetric.add(testA, testB, dotFive).build().sort(new SimilarityAssessmentComparatorBySourceAndTargetNames());
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
	void addPairOfTwoTestsAsymmetric() {
		SimilarityReport report = asymmetric.add(testA, testB, dotFive).add(testB, testA, dotSix).build().sort(new SimilarityAssessmentComparatorBySourceAndTargetNames());
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
	void addTestTwice() {
		assertThrows(PotentialErrorProneOperationException.class, () -> symmetric.add(testA).add(testA));
		assertThrows(PotentialErrorProneOperationException.class, () -> asymmetric.add(testA).add(testA));
	}

	@Test
	void addTestWithItself() {
		assertThrows(PotentialErrorProneOperationException.class, () -> symmetric.add(testA, testA, dotFive));
		assertThrows(PotentialErrorProneOperationException.class, () -> asymmetric.add(testA, testA, dotFive));
	}

	@Test
	void addTestAndPairWithItseflAndAnotherTest() {
		assertThrows(PotentialErrorProneOperationException.class, () -> symmetric.add(testA).add(testA, testB, dotFive));
		assertThrows(PotentialErrorProneOperationException.class, () -> symmetric.add(testA, testB, dotFive).add(testB));
		assertThrows(PotentialErrorProneOperationException.class, () -> asymmetric.add(testA).add(testA, testB, dotFive));
		assertThrows(PotentialErrorProneOperationException.class, () -> asymmetric.add(testA, testB, dotFive).add(testB));
	}

	@Test
	void addSamePairTwice() {
		assertThrows(PotentialErrorProneOperationException.class, () -> symmetric.add(testA, testB, dotFive).add(testA, testB, dotSix));
		assertThrows(PotentialErrorProneOperationException.class, () -> symmetric.add(testA, testB, dotFive).add(testB, testA, dotSix));
		assertThrows(PotentialErrorProneOperationException.class, () -> asymmetric.add(testA, testB, dotFive).add(testA, testB, dotSix));
	}

	@Test
	void addTwoTestsSymmetricWithoutComplete() {
		assertThrows(MissingAssessmentException.class, () -> symmetric.add(testA).add(testB).build());
	}

	@Test
	void addTwoTestsAsymmetricWithoutComplete() {
		assertThrows(MissingAssessmentException.class, () -> asymmetric.add(testA).add(testB).build());
	}

}
