package br.ufsc.ine.leb.roza.measurement.metric;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.extraction.TestCase;
import br.ufsc.ine.leb.roza.materialization.Junit4WithAssertionsTestCaseMaterializer;
import br.ufsc.ine.leb.roza.materialization.MaterializationReport;
import br.ufsc.ine.leb.roza.materialization.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.measurement.LccssSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.SimilarityAssessment;
import br.ufsc.ine.leb.roza.measurement.SimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.SimilarityReport;
import br.ufsc.ine.leb.roza.parsing.RozaStatement;
import br.ufsc.ine.leb.roza.utils.FolderUtils;
import br.ufsc.ine.leb.roza.utils.comparator.SimilarityAssessmentComparatorByScoreSourceNameAndTargetName;

class LccssSimilarityMetricTest {

	private BigDecimal oneOfTwo;
	private BigDecimal oneOfThree;
	private BigDecimal twoOfThree;
	private BigDecimal fourOfFive;
	private BigDecimal oneOfFour;
	private BigDecimal fourOfSeven;
	private BigDecimal twoOfFive;

	private TestCaseMaterializer materializer;
	private SimilarityMeasurer measurer;

	@BeforeEach
	void setup() {
		new FolderUtils("main/exec/materializer").createEmptyFolder();
		new FolderUtils("main/exec/measurer").createEmptyFolder();
		materializer = new Junit4WithAssertionsTestCaseMaterializer("main/exec/materializer");
		measurer = new LccssSimilarityMeasurer();
		oneOfTwo = new BigDecimal(1).divide(new BigDecimal(2), MathContext.DECIMAL32);
		oneOfThree = new BigDecimal(1).divide(new BigDecimal(3), MathContext.DECIMAL32);
		twoOfThree = new BigDecimal(2).divide(new BigDecimal(3), MathContext.DECIMAL32);
		fourOfFive = new BigDecimal(4).divide(new BigDecimal(5), MathContext.DECIMAL32);
		oneOfFour = new BigDecimal(1).divide(new BigDecimal(4), MathContext.DECIMAL32);
		fourOfSeven = new BigDecimal(4).divide(new BigDecimal(7), MathContext.DECIMAL32);
		twoOfFive = new BigDecimal(2).divide(new BigDecimal(5), MathContext.DECIMAL32);
	}

	@Test
	void zeroFixtures() throws Exception {
		RozaStatement assertion = new RozaStatement("assertEquals(0, 0);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(), Arrays.asList(assertion));
		TestCase testCaseB = new TestCase("testB", Arrays.asList(), Arrays.asList(assertion));
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
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
	void commonStartSequenceSameAssertions() throws Exception {
		RozaStatement fixture1 = new RozaStatement("sut(1);");
		RozaStatement fixture2 = new RozaStatement("sut(2);");
		RozaStatement fixture3 = new RozaStatement("sut(3);");
		RozaStatement assertion = new RozaStatement("assertEquals(0, 0);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture2), Arrays.asList(assertion));
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture3), Arrays.asList(assertion));
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
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
	void commonStartSequenceDifferentAssertions() throws Exception {
		RozaStatement fixture1 = new RozaStatement("sut(1);");
		RozaStatement fixture2 = new RozaStatement("sut(2);");
		RozaStatement fixture3 = new RozaStatement("sut(3);");
		RozaStatement assertion1 = new RozaStatement("assertEquals(1, 1);");
		RozaStatement assertion2 = new RozaStatement("assertEquals(2, 2);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture2), Arrays.asList(assertion1));
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture3), Arrays.asList(assertion2));
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
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
	void commonShortContigousStartSequence() throws Exception {
		RozaStatement fixture1 = new RozaStatement("sut(1);");
		RozaStatement fixture2 = new RozaStatement("sut(2);");
		RozaStatement fixture3 = new RozaStatement("sut(3);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture2), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture3), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
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
	void commonLongContigousStartSequence() throws Exception {
		RozaStatement fixture1 = new RozaStatement("sut(1);");
		RozaStatement fixture2 = new RozaStatement("sut(2);");
		RozaStatement fixture3 = new RozaStatement("sut(3);");
		RozaStatement fixture4 = new RozaStatement("sut(4);");
		RozaStatement fixture5 = new RozaStatement("sut(5);");
		RozaStatement fixture6 = new RozaStatement("sut(6);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture2, fixture3, fixture5), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2, fixture4, fixture6), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
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
	void commonNonContigousSequenceAfterStart() throws Exception {
		RozaStatement fixture1 = new RozaStatement("sut(1);");
		RozaStatement fixture2 = new RozaStatement("sut(2);");
		RozaStatement fixture3 = new RozaStatement("sut(3);");
		RozaStatement fixture4 = new RozaStatement("sut(4);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture2, fixture4), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture3, fixture4), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
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
		assertEquals(oneOfThree, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(oneOfThree, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonNonContigousSequenceSinceStart() throws Exception {
		RozaStatement fixture1 = new RozaStatement("sut(1);");
		RozaStatement fixture2 = new RozaStatement("sut(2);");
		RozaStatement fixture3 = new RozaStatement("sut(3);");
		RozaStatement fixture4 = new RozaStatement("sut(4);");
		RozaStatement fixture5 = new RozaStatement("sut(5);");
		RozaStatement fixture6 = new RozaStatement("sut(6);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture5, fixture1, fixture2, fixture4), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture6, fixture1, fixture3, fixture4), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
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
	void commonShortAsymmetricStartSequence() throws Exception {
		RozaStatement fixture1 = new RozaStatement("sut(1);");
		RozaStatement fixture2 = new RozaStatement("sut(2);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
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
	void commonLongAsymmetricStartSequence() throws Exception {
		RozaStatement fixture1 = new RozaStatement("sut(1);");
		RozaStatement fixture2 = new RozaStatement("sut(2);");
		RozaStatement fixture3 = new RozaStatement("sut(3);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2, fixture3), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
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
	void commonAsymmetricStartSequenceWithoutRemainings() throws Exception {
		RozaStatement fixture1 = new RozaStatement("sut(1);");
		RozaStatement fixture2 = new RozaStatement("sut(2);");
		RozaStatement fixture3 = new RozaStatement("sut(3);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2), Arrays.asList());
		TestCase testCaseC = new TestCase("testC", Arrays.asList(fixture1, fixture2, fixture3), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB, testCaseC));
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
	void commonAsymmetricStartSequenceWithFixedRemainings() throws Exception {
		RozaStatement fixture1 = new RozaStatement("sut(1);");
		RozaStatement fixture2 = new RozaStatement("sut(2);");
		RozaStatement fixture3 = new RozaStatement("sut(3);");
		RozaStatement fixture4 = new RozaStatement("sut(4);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture4), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2, fixture4), Arrays.asList());
		TestCase testCaseC = new TestCase("testC", Arrays.asList(fixture1, fixture2, fixture3, fixture4), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB, testCaseC));
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
		assertEquals(fourOfSeven, assessmentBC.getScore());
		assertEquals(testCaseB, assessmentBC.getSource());
		assertEquals(testCaseC, assessmentBC.getTarget());
		assertEquals(fourOfSeven, assessmentCB.getScore());
		assertEquals(testCaseC, assessmentCB.getSource());
		assertEquals(testCaseB, assessmentCB.getTarget());
		assertEquals(twoOfFive, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(twoOfFive, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
		assertEquals(oneOfThree, assessmentAC.getScore());
		assertEquals(testCaseA, assessmentAC.getSource());
		assertEquals(testCaseC, assessmentAC.getTarget());
		assertEquals(oneOfThree, assessmentCA.getScore());
		assertEquals(testCaseC, assessmentCA.getSource());
		assertEquals(testCaseA, assessmentCA.getTarget());
	}

	@Test
	void commonAsymmetricStartSequenceWithProportionalRemainings() throws Exception {
		RozaStatement fixture1 = new RozaStatement("sut(1);");
		RozaStatement fixture2 = new RozaStatement("sut(2);");
		RozaStatement fixture3 = new RozaStatement("sut(3);");
		RozaStatement fixture4 = new RozaStatement("sut(4);");
		RozaStatement fixture5 = new RozaStatement("sut(5);");
		RozaStatement fixture6 = new RozaStatement("sut(6);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture4), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2, fixture4, fixture5), Arrays.asList());
		TestCase testCaseC = new TestCase("testC", Arrays.asList(fixture1, fixture2, fixture3, fixture4, fixture5, fixture6), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB, testCaseC));
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
		assertEquals(twoOfFive, assessmentBC.getScore());
		assertEquals(testCaseB, assessmentBC.getSource());
		assertEquals(testCaseC, assessmentBC.getTarget());
		assertEquals(twoOfFive, assessmentCB.getScore());
		assertEquals(testCaseC, assessmentCB.getSource());
		assertEquals(testCaseB, assessmentCB.getTarget());
		assertEquals(oneOfThree, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(oneOfThree, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
		assertEquals(oneOfFour, assessmentAC.getScore());
		assertEquals(testCaseA, assessmentAC.getSource());
		assertEquals(testCaseC, assessmentAC.getTarget());
		assertEquals(oneOfFour, assessmentCA.getScore());
		assertEquals(testCaseC, assessmentCA.getSource());
		assertEquals(testCaseA, assessmentCA.getTarget());
	}

	@Test
	void commonAsymmetricStartSequenceWithInverselyProportionalRemainings() throws Exception {
		RozaStatement fixture1 = new RozaStatement("sut(1);");
		RozaStatement fixture2 = new RozaStatement("sut(2);");
		RozaStatement fixture3 = new RozaStatement("sut(3);");
		RozaStatement fixture4 = new RozaStatement("sut(4);");
		RozaStatement fixture5 = new RozaStatement("sut(5);");
		RozaStatement fixture6 = new RozaStatement("sut(6);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture4, fixture5, fixture6), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2, fixture4, fixture5), Arrays.asList());
		TestCase testCaseC = new TestCase("testC", Arrays.asList(fixture1, fixture2, fixture3, fixture4), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB, testCaseC));
		SimilarityReport report = measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentCC = iterator.next();
		SimilarityAssessment assessmentBC = iterator.next();
		SimilarityAssessment assessmentCB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentAC = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();
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
		assertEquals(oneOfTwo, assessmentBC.getScore());
		assertEquals(testCaseB, assessmentBC.getSource());
		assertEquals(testCaseC, assessmentBC.getTarget());
		assertEquals(oneOfTwo, assessmentCB.getScore());
		assertEquals(testCaseC, assessmentCB.getSource());
		assertEquals(testCaseB, assessmentCB.getTarget());
		assertEquals(oneOfFour, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(oneOfFour, assessmentBA.getScore());
		assertEquals(testCaseA, assessmentAC.getSource());
		assertEquals(testCaseC, assessmentAC.getTarget());
		assertEquals(oneOfFour, assessmentAC.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
		assertEquals(oneOfFour, assessmentCA.getScore());
		assertEquals(testCaseC, assessmentCA.getSource());
		assertEquals(testCaseA, assessmentCA.getTarget());
	}

}
