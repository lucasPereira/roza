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

	@Test
	void zeroFixtures() throws Exception {
		Statement assertion = new Statement("assertEquals(0, 0);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(), Arrays.asList(assertion));
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

		assertEquals(4, report.getAssessments().size());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testCaseA, report.getAssessments().get(0).getSource());
		assertEquals(testCaseA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(1).getScore());
		assertEquals(testCaseB, report.getAssessments().get(1).getSource());
		assertEquals(testCaseB, report.getAssessments().get(1).getTarget());
		assertEquals(new BigDecimal("0.5"), report.getAssessments().get(2).getScore());
		assertEquals(testCaseA, report.getAssessments().get(2).getSource());
		assertEquals(testCaseB, report.getAssessments().get(2).getTarget());
		assertEquals(new BigDecimal("0.5"), report.getAssessments().get(3).getScore());
		assertEquals(testCaseB, report.getAssessments().get(3).getSource());
		assertEquals(testCaseA, report.getAssessments().get(3).getTarget());
	}

	@Test
	void commonStartSequenceDifferentAssertions() throws Exception {
		Statement fixture1 = new Statement("sut('a');");
		Statement fixture2 = new Statement("sut('b');");
		Statement fixture3 = new Statement("sut('c');");
		Statement assertion1 = new Statement("assertEquals('a', 'a');");
		Statement assertion2 = new Statement("assertEquals('b', 'b');");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture2), Arrays.asList(assertion1));
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture3), Arrays.asList(assertion2));
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport);

		assertEquals(4, report.getAssessments().size());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testCaseA, report.getAssessments().get(0).getSource());
		assertEquals(testCaseA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(1).getScore());
		assertEquals(testCaseB, report.getAssessments().get(1).getSource());
		assertEquals(testCaseB, report.getAssessments().get(1).getTarget());
		assertEquals(new BigDecimal("0.5"), report.getAssessments().get(2).getScore());
		assertEquals(testCaseA, report.getAssessments().get(2).getSource());
		assertEquals(testCaseB, report.getAssessments().get(2).getTarget());
		assertEquals(new BigDecimal("0.5"), report.getAssessments().get(3).getScore());
		assertEquals(testCaseB, report.getAssessments().get(3).getSource());
		assertEquals(testCaseA, report.getAssessments().get(3).getTarget());
	}

	@Test
	void commonShortContigousStartSequence() throws Exception {
		Statement fixture1 = new Statement("sut('a');");
		Statement fixture2 = new Statement("sut('b');");
		Statement fixture3 = new Statement("sut('c');");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture2), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture3), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport);

		assertEquals(4, report.getAssessments().size());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testCaseA, report.getAssessments().get(0).getSource());
		assertEquals(testCaseA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(1).getScore());
		assertEquals(testCaseB, report.getAssessments().get(1).getSource());
		assertEquals(testCaseB, report.getAssessments().get(1).getTarget());
		assertEquals(new BigDecimal("0.5"), report.getAssessments().get(2).getScore());
		assertEquals(testCaseA, report.getAssessments().get(2).getSource());
		assertEquals(testCaseB, report.getAssessments().get(2).getTarget());
		assertEquals(new BigDecimal("0.5"), report.getAssessments().get(3).getScore());
		assertEquals(testCaseB, report.getAssessments().get(3).getSource());
		assertEquals(testCaseA, report.getAssessments().get(3).getTarget());
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

		assertEquals(4, report.getAssessments().size());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testCaseA, report.getAssessments().get(0).getSource());
		assertEquals(testCaseA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(1).getScore());
		assertEquals(testCaseB, report.getAssessments().get(1).getSource());
		assertEquals(testCaseB, report.getAssessments().get(1).getTarget());
		assertEquals(new BigDecimal("0.5"), report.getAssessments().get(2).getScore());
		assertEquals(testCaseA, report.getAssessments().get(2).getSource());
		assertEquals(testCaseB, report.getAssessments().get(2).getTarget());
		assertEquals(new BigDecimal("0.5"), report.getAssessments().get(3).getScore());
		assertEquals(testCaseB, report.getAssessments().get(3).getSource());
		assertEquals(testCaseA, report.getAssessments().get(3).getTarget());
	}

	@Test
	void commonNonContigousStartSequence() throws Exception {
		Statement fixture1 = new Statement("sut('a');");
		Statement fixture2 = new Statement("sut('b');");
		Statement fixture3 = new Statement("sut('c');");
		Statement fixture4 = new Statement("sut('d');");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture2, fixture4), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture3, fixture4), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport);

		assertEquals(4, report.getAssessments().size());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testCaseA, report.getAssessments().get(0).getSource());
		assertEquals(testCaseA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(1).getScore());
		assertEquals(testCaseB, report.getAssessments().get(1).getSource());
		assertEquals(testCaseB, report.getAssessments().get(1).getTarget());
		assertEquals(new BigDecimal("0.3333333"), report.getAssessments().get(2).getScore());
		assertEquals(testCaseA, report.getAssessments().get(2).getSource());
		assertEquals(testCaseB, report.getAssessments().get(2).getTarget());
		assertEquals(new BigDecimal("0.3333333"), report.getAssessments().get(3).getScore());
		assertEquals(testCaseB, report.getAssessments().get(3).getSource());
		assertEquals(testCaseA, report.getAssessments().get(3).getTarget());
	}

	@Test
	void commonShortAsymmetricStartSequence() throws Exception {
		Statement fixture1 = new Statement("sut('a');");
		Statement fixture2 = new Statement("sut('b');");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport);

		assertEquals(4, report.getAssessments().size());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testCaseA, report.getAssessments().get(0).getSource());
		assertEquals(testCaseA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(1).getScore());
		assertEquals(testCaseB, report.getAssessments().get(1).getSource());
		assertEquals(testCaseB, report.getAssessments().get(1).getTarget());
		assertEquals(new BigDecimal("0.6666667"), report.getAssessments().get(2).getScore());
		assertEquals(testCaseA, report.getAssessments().get(2).getSource());
		assertEquals(testCaseB, report.getAssessments().get(2).getTarget());
		assertEquals(new BigDecimal("0.6666667"), report.getAssessments().get(3).getScore());
		assertEquals(testCaseB, report.getAssessments().get(3).getSource());
		assertEquals(testCaseA, report.getAssessments().get(3).getTarget());
	}

	@Test
	void commonLongAsymmetricStartSequence() throws Exception {
		Statement fixture1 = new Statement("sut('a');");
		Statement fixture2 = new Statement("sut('b');");
		Statement fixture3 = new Statement("sut('c');");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2, fixture3), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = measurer.measure(materializationReport);

		assertEquals(4, report.getAssessments().size());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testCaseA, report.getAssessments().get(0).getSource());
		assertEquals(testCaseA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(1).getScore());
		assertEquals(testCaseB, report.getAssessments().get(1).getSource());
		assertEquals(testCaseB, report.getAssessments().get(1).getTarget());
		assertEquals(new BigDecimal("0.5"), report.getAssessments().get(2).getScore());
		assertEquals(testCaseA, report.getAssessments().get(2).getSource());
		assertEquals(testCaseB, report.getAssessments().get(2).getTarget());
		assertEquals(new BigDecimal("0.5"), report.getAssessments().get(3).getScore());
		assertEquals(testCaseB, report.getAssessments().get(3).getSource());
		assertEquals(testCaseA, report.getAssessments().get(3).getTarget());
	}

	@Test
	void commonAsymmetricStartSequenceWithoutRemainings() throws Exception {
		Statement fixtureA = new Statement("sut('a');");
		Statement fixtureB = new Statement("sut('b');");
		Statement fixtureC = new Statement("sut('c');");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixtureA), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixtureA, fixtureB), Arrays.asList());
		TestCase testCaseC = new TestCase("testC", Arrays.asList(fixtureA, fixtureB, fixtureC), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB, testCaseC));
		SimilarityReport report = measurer.measure(materializationReport);

		assertEquals(9, report.getAssessments().size());
	}

	@Test
	void commonAsymmetricStartSequenceWithFixedRemainings() throws Exception {
		Statement fixtureA = new Statement("sut('a');");
		Statement fixtureB = new Statement("sut('b');");
		Statement fixtureC = new Statement("sut('c');");
		Statement fixtureD = new Statement("sut('d');");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixtureA, fixtureD), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixtureA, fixtureB, fixtureD), Arrays.asList());
		TestCase testCaseC = new TestCase("testC", Arrays.asList(fixtureA, fixtureB, fixtureC, fixtureD), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB, testCaseC));
		SimilarityReport report = measurer.measure(materializationReport);

		assertEquals(9, report.getAssessments().size());
	}

	@Test
	void commonAsymmetricStartSequenceWithProportionalRemainings() throws Exception {
		Statement fixtureA = new Statement("sut('a');");
		Statement fixtureB = new Statement("sut('b');");
		Statement fixtureC = new Statement("sut('c');");
		Statement fixtureD = new Statement("sut('d');");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixtureA, fixtureD), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixtureA, fixtureB, fixtureD, fixtureD), Arrays.asList());
		TestCase testCaseC = new TestCase("testC", Arrays.asList(fixtureA, fixtureB, fixtureC, fixtureD, fixtureD, fixtureD), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB, testCaseC));
		SimilarityReport report = measurer.measure(materializationReport);

		assertEquals(9, report.getAssessments().size());

	}

	@Test
	void commonAsymmetricStartSequenceWithInverselyProportionalRemainings() throws Exception {
		Statement fixtureA = new Statement("sut('a');");
		Statement fixtureB = new Statement("sut('b');");
		Statement fixtureC = new Statement("sut('c');");
		Statement fixtureD = new Statement("sut('d');");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixtureA, fixtureD, fixtureD, fixtureD), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixtureA, fixtureB, fixtureD, fixtureD), Arrays.asList());
		TestCase testCaseC = new TestCase("testC", Arrays.asList(fixtureA, fixtureB, fixtureC, fixtureD), Arrays.asList());
		MaterializationReport materializationReport = materializer.materialize(Arrays.asList(testCaseA, testCaseB, testCaseC));
		SimilarityReport report = measurer.measure(materializationReport);

		assertEquals(9, report.getAssessments().size());
	}

}
