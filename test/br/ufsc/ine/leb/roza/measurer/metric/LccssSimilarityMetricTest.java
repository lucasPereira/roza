package br.ufsc.ine.leb.roza.measurer.metric;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.materializer.OneTestCasePerClassTestCaseMaterializer;
import br.ufsc.ine.leb.roza.materializer.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.measurer.LccssSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurer.SimilarityMeasurer;
import br.ufsc.ine.leb.roza.utils.FolderUtils;

public class LccssSimilarityMetricTest {

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
		new FolderUtils("execution/materializer").createEmptyFolder();
		new FolderUtils("execution/measurer").createEmptyFolder();
		materializer = new OneTestCasePerClassTestCaseMaterializer("execution/materializer");
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
		Statement assertion = new Statement("assertEquals(0, 0);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(), Arrays.asList(assertion));
		TestCase testCaseB = new TestCase("testB", Arrays.asList(), Arrays.asList(assertion));
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport);

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
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement assertion = new Statement("assertEquals(0, 0);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture2), Arrays.asList(assertion));
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture3), Arrays.asList(assertion));
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport);

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
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement assertion1 = new Statement("assertEquals(1, 1);");
		Statement assertion2 = new Statement("assertEquals(2, 2);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture2), Arrays.asList(assertion1));
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture3), Arrays.asList(assertion2));
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport);

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
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture2), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture3), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport);

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
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement fixture4 = new Statement("sut(4);");
		Statement fixture5 = new Statement("sut(5);");
		Statement fixture6 = new Statement("sut(6);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture2, fixture3, fixture5), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2, fixture4, fixture6), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport);

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
	void commonNonContigousStartSequence() throws Exception {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement fixture4 = new Statement("sut(4);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture2, fixture4), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture3, fixture4), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport);

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
	void commonShortAsymmetricStartSequence() throws Exception {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport);

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
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2, fixture3), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport);

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
		Statement fixtureA = new Statement("sut(1);");
		Statement fixtureB = new Statement("sut(2);");
		Statement fixtureC = new Statement("sut(3);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixtureA), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixtureA, fixtureB), Arrays.asList());
		TestCase testCaseC = new TestCase("testC", Arrays.asList(fixtureA, fixtureB, fixtureC), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB, testCaseC));
		SimilarityReport report = measurer.measure(materializationReport);

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
		Statement fixtureA = new Statement("sut(1);");
		Statement fixtureB = new Statement("sut(2);");
		Statement fixtureC = new Statement("sut(3);");
		Statement fixtureD = new Statement("sut(4);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixtureA, fixtureD), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixtureA, fixtureB, fixtureD), Arrays.asList());
		TestCase testCaseC = new TestCase("testC", Arrays.asList(fixtureA, fixtureB, fixtureC, fixtureD), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB, testCaseC));
		SimilarityReport report = measurer.measure(materializationReport);

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
		Statement fixtureA = new Statement("sut(1);");
		Statement fixtureB = new Statement("sut(2);");
		Statement fixtureC = new Statement("sut(3);");
		Statement fixtureD = new Statement("sut(4);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixtureA, fixtureD), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixtureA, fixtureB, fixtureD, fixtureD), Arrays.asList());
		TestCase testCaseC = new TestCase("testC", Arrays.asList(fixtureA, fixtureB, fixtureC, fixtureD, fixtureD, fixtureD), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB, testCaseC));
		SimilarityReport report = measurer.measure(materializationReport);

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
		Statement fixtureA = new Statement("sut(1);");
		Statement fixtureB = new Statement("sut(2);");
		Statement fixtureC = new Statement("sut(3);");
		Statement fixtureD = new Statement("sut(4);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixtureA, fixtureD, fixtureD, fixtureD), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixtureA, fixtureB, fixtureD, fixtureD), Arrays.asList());
		TestCase testCaseC = new TestCase("testC", Arrays.asList(fixtureA, fixtureB, fixtureC, fixtureD), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB, testCaseC));
		SimilarityReport report = measurer.measure(materializationReport);

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
