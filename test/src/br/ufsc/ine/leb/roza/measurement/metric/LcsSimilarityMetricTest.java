package br.ufsc.ine.leb.roza.measurement.metric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.materialization.Junit4WithAssertionsTestCaseMaterializer;
import br.ufsc.ine.leb.roza.materialization.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.measurement.LcsSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.SimilarityMeasurer;
import br.ufsc.ine.leb.roza.utils.FolderUtils;
import br.ufsc.ine.leb.roza.utils.comparator.SimilarityAssessmentComparatorByScoreSourceNameAndTargetName;

class LcsSimilarityMetricTest {

	private BigDecimal oneOfTwo;
	private BigDecimal twoOfThree;
	private BigDecimal fourOfFive;
	private BigDecimal sixOfSeven;
	private BigDecimal threeOfFour;

	private TestCaseMaterializer materializer;
	private SimilarityMeasurer measurer;

	@BeforeEach
	void setup() {
		new FolderUtils("main/exec/materializer").createEmptyFolder();
		new FolderUtils("main/exec/measurer").createEmptyFolder();
		materializer = new Junit4WithAssertionsTestCaseMaterializer("main/exec/materializer");
		measurer = new LcsSimilarityMeasurer();
		oneOfTwo = new BigDecimal(1).divide(new BigDecimal(2), MathContext.DECIMAL32);
		twoOfThree = new BigDecimal(2).divide(new BigDecimal(3), MathContext.DECIMAL32);
		fourOfFive = new BigDecimal(4).divide(new BigDecimal(5), MathContext.DECIMAL32);
		sixOfSeven = new BigDecimal(6).divide(new BigDecimal(7), MathContext.DECIMAL32);
		threeOfFour = new BigDecimal(3).divide(new BigDecimal(4), MathContext.DECIMAL32);
	}

	@Test
	void zeroFixtures() {
		Statement assertion = new Statement("assertEquals(0, 0);");
		TestCase testCaseA = new TestCase("testA", List.of(), List.of(assertion));
		TestCase testCaseB = new TestCase("testB", List.of(), List.of(assertion));
		MaterializationReport materializationReport = materializer.materialize(List.of(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();

		assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(BigDecimal.ZERO, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(BigDecimal.ZERO, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonStartSequenceSameAssertions() {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement assertion = new Statement("assertEquals(0, 0);");
		TestCase testCaseA = new TestCase("testA", List.of(fixture1, fixture2), List.of(assertion));
		TestCase testCaseB = new TestCase("testB", List.of(fixture1, fixture3), List.of(assertion));
		MaterializationReport materializationReport = materializer.materialize(List.of(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();

		assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(oneOfTwo, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(oneOfTwo, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonStartSequenceDifferentAssertions() {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement assertion1 = new Statement("assertEquals(1, 1);");
		Statement assertion2 = new Statement("assertEquals(2, 2);");
		TestCase testCaseA = new TestCase("testA", List.of(fixture1, fixture2), List.of(assertion1));
		TestCase testCaseB = new TestCase("testB", List.of(fixture1, fixture3), List.of(assertion2));
		MaterializationReport materializationReport = materializer.materialize(List.of(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();

		assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(oneOfTwo, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(oneOfTwo, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonShortContigousStartSequence() {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		TestCase testCaseA = new TestCase("testA", List.of(fixture1, fixture2), List.of());
		TestCase testCaseB = new TestCase("testB", List.of(fixture1, fixture3), List.of());
		MaterializationReport materializationReport = materializer.materialize(List.of(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();

		assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(oneOfTwo, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(oneOfTwo, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonLongContigousStartSequence() {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement fixture4 = new Statement("sut(4);");
		Statement fixture5 = new Statement("sut(5);");
		Statement fixture6 = new Statement("sut(6);");
		TestCase testCaseA = new TestCase("testA", List.of(fixture1, fixture2, fixture3, fixture5), List.of());
		TestCase testCaseB = new TestCase("testB", List.of(fixture1, fixture2, fixture4, fixture6), List.of());
		MaterializationReport materializationReport = materializer.materialize(List.of(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();

		assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(oneOfTwo, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(oneOfTwo, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonNonContigousSequenceAfterStart() {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement fixture4 = new Statement("sut(4);");
		TestCase testCaseA = new TestCase("testA", List.of(fixture1, fixture2, fixture4), List.of());
		TestCase testCaseB = new TestCase("testB", List.of(fixture1, fixture3, fixture4), List.of());
		MaterializationReport materializationReport = materializer.materialize(List.of(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();

		assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(twoOfThree, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(twoOfThree, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonNonContigousSequenceSinceStart() {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement fixture4 = new Statement("sut(4);");
		Statement fixture5 = new Statement("sut(5);");
		Statement fixture6 = new Statement("sut(6);");
		TestCase testCaseA = new TestCase("testA", List.of(fixture5, fixture1, fixture2, fixture4), List.of());
		TestCase testCaseB = new TestCase("testB", List.of(fixture6, fixture1, fixture3, fixture4), List.of());
		MaterializationReport materializationReport = materializer.materialize(List.of(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();

		assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(oneOfTwo, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(oneOfTwo, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonShortAsymmetricStartSequence() {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		TestCase testCaseA = new TestCase("testA", List.of(fixture1), List.of());
		TestCase testCaseB = new TestCase("testB", List.of(fixture1, fixture2), List.of());
		MaterializationReport materializationReport = materializer.materialize(List.of(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();

		assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(twoOfThree, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(twoOfThree, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonLongAsymmetricStartSequence() {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		TestCase testCaseA = new TestCase("testA", List.of(fixture1), List.of());
		TestCase testCaseB = new TestCase("testB", List.of(fixture1, fixture2, fixture3), List.of());
		MaterializationReport materializationReport = materializer.materialize(List.of(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();

		assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(oneOfTwo, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(oneOfTwo, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonAsymmetricStartSequenceWithoutRemainings() {
		Statement fixtureA = new Statement("sut(1);");
		Statement fixtureB = new Statement("sut(2);");
		Statement fixtureC = new Statement("sut(3);");
		TestCase testCaseA = new TestCase("testA", List.of(fixtureA), List.of());
		TestCase testCaseB = new TestCase("testB", List.of(fixtureA, fixtureB), List.of());
		TestCase testCaseC = new TestCase("testC", List.of(fixtureA, fixtureB, fixtureC), List.of());
		MaterializationReport materializationReport = materializer.materialize(List.of(testCaseA, testCaseB, testCaseC));
		SimilarityReport report = measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentCC = iterator.next();
		SimilarityAssessment assessmentBC = iterator.next();
		SimilarityAssessment assessmentCB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();
		SimilarityAssessment assessmentAC = iterator.next();
		SimilarityAssessment assessmentCA = iterator.next();

		assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseC, assessmentCC.getSource());
		assertEquals(testCaseC, assessmentCC.getTarget());
		assertEquals(fourOfFive, assessmentBC.getScore());
		assertEquals(testCaseB, assessmentBC.getSource());
		assertEquals(testCaseC, assessmentBC.getTarget());
		assertEquals(fourOfFive, assessmentCB.getScore());
		assertEquals(testCaseC, assessmentCB.getSource());
		assertEquals(testCaseB, assessmentCB.getTarget());
		assertEquals(twoOfThree, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(twoOfThree, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
		assertEquals(oneOfTwo, assessmentAC.getScore());
		assertEquals(testCaseA, assessmentAC.getSource());
		assertEquals(testCaseC, assessmentAC.getTarget());
		assertEquals(oneOfTwo, assessmentCA.getScore());
		assertEquals(testCaseC, assessmentCA.getSource());
		assertEquals(testCaseA, assessmentCA.getTarget());
	}

	@Test
	void commonAsymmetricStartSequenceWithFixedRemainings() {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement fixture4 = new Statement("sut(4);");
		TestCase testCaseA = new TestCase("testA", List.of(fixture1, fixture4), List.of());
		TestCase testCaseB = new TestCase("testB", List.of(fixture1, fixture2, fixture4), List.of());
		TestCase testCaseC = new TestCase("testC", List.of(fixture1, fixture2, fixture3, fixture4), List.of());
		MaterializationReport materializationReport = materializer.materialize(List.of(testCaseA, testCaseB, testCaseC));
		SimilarityReport report = measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentCC = iterator.next();
		SimilarityAssessment assessmentBC = iterator.next();
		SimilarityAssessment assessmentCB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();
		SimilarityAssessment assessmentAC = iterator.next();
		SimilarityAssessment assessmentCA = iterator.next();

		assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseC, assessmentCC.getSource());
		assertEquals(testCaseC, assessmentCC.getTarget());
		assertEquals(sixOfSeven, assessmentBC.getScore());
		assertEquals(testCaseB, assessmentBC.getSource());
		assertEquals(testCaseC, assessmentBC.getTarget());
		assertEquals(sixOfSeven, assessmentCB.getScore());
		assertEquals(testCaseC, assessmentCB.getSource());
		assertEquals(testCaseB, assessmentCB.getTarget());
		assertEquals(fourOfFive, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(fourOfFive, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
		assertEquals(twoOfThree, assessmentAC.getScore());
		assertEquals(testCaseA, assessmentAC.getSource());
		assertEquals(testCaseC, assessmentAC.getTarget());
		assertEquals(twoOfThree, assessmentCA.getScore());
		assertEquals(testCaseC, assessmentCA.getSource());
		assertEquals(testCaseA, assessmentCA.getTarget());
	}

	@Test
	void commonAsymmetricStartSequenceWithProportionalRemainings() {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement fixture4 = new Statement("sut(4);");
		Statement fixture5 = new Statement("sut(5);");
		Statement fixture6 = new Statement("sut(6);");
		TestCase testCaseA = new TestCase("testA", List.of(fixture1, fixture4), List.of());
		TestCase testCaseB = new TestCase("testB", List.of(fixture1, fixture2, fixture4, fixture5), List.of());
		TestCase testCaseC = new TestCase("testC", List.of(fixture1, fixture2, fixture3, fixture4, fixture5, fixture6), List.of());
		MaterializationReport materializationReport = materializer.materialize(List.of(testCaseA, testCaseB, testCaseC));
		SimilarityReport report = measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentCC = iterator.next();
		SimilarityAssessment assessmentBC = iterator.next();
		SimilarityAssessment assessmentCB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();
		SimilarityAssessment assessmentAC = iterator.next();
		SimilarityAssessment assessmentCA = iterator.next();

		assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseC, assessmentCC.getSource());
		assertEquals(testCaseC, assessmentCC.getTarget());
		assertEquals(fourOfFive, assessmentBC.getScore());
		assertEquals(testCaseB, assessmentBC.getSource());
		assertEquals(testCaseC, assessmentBC.getTarget());
		assertEquals(fourOfFive, assessmentCB.getScore());
		assertEquals(testCaseC, assessmentCB.getSource());
		assertEquals(testCaseB, assessmentCB.getTarget());
		assertEquals(twoOfThree, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(twoOfThree, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
		assertEquals(oneOfTwo, assessmentAC.getScore());
		assertEquals(testCaseA, assessmentAC.getSource());
		assertEquals(testCaseC, assessmentAC.getTarget());
		assertEquals(oneOfTwo, assessmentCA.getScore());
		assertEquals(testCaseC, assessmentCA.getSource());
		assertEquals(testCaseA, assessmentCA.getTarget());
	}

	@Test
	void commonAsymmetricStartSequenceWithInverselyProportionalRemainings() {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement fixture4 = new Statement("sut(4);");
		Statement fixture5 = new Statement("sut(5);");
		Statement fixture6 = new Statement("sut(6);");
		TestCase testCaseA = new TestCase("testA", List.of(fixture1, fixture4, fixture5, fixture6), List.of());
		TestCase testCaseB = new TestCase("testB", List.of(fixture1, fixture2, fixture4, fixture5), List.of());
		TestCase testCaseC = new TestCase("testC", List.of(fixture1, fixture2, fixture3, fixture4), List.of());
		MaterializationReport materializationReport = materializer.materialize(List.of(testCaseA, testCaseB, testCaseC));
		SimilarityReport report = measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentCC = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();
		SimilarityAssessment assessmentBC = iterator.next();
		SimilarityAssessment assessmentCB = iterator.next();
		SimilarityAssessment assessmentAC = iterator.next();
		SimilarityAssessment assessmentCA = iterator.next();

		assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseC, assessmentCC.getSource());
		assertEquals(testCaseC, assessmentCC.getTarget());
		assertEquals(threeOfFour, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(threeOfFour, assessmentBA.getScore());
		assertEquals(testCaseA, assessmentAC.getSource());
		assertEquals(testCaseC, assessmentAC.getTarget());
		assertEquals(threeOfFour, assessmentBC.getScore());
		assertEquals(testCaseB, assessmentBC.getSource());
		assertEquals(testCaseC, assessmentBC.getTarget());
		assertEquals(threeOfFour, assessmentCB.getScore());
		assertEquals(testCaseC, assessmentCB.getSource());
		assertEquals(testCaseB, assessmentCB.getTarget());
		assertEquals(oneOfTwo, assessmentAC.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
		assertEquals(oneOfTwo, assessmentCA.getScore());
		assertEquals(testCaseC, assessmentCA.getSource());
		assertEquals(testCaseA, assessmentCA.getTarget());
	}

}
