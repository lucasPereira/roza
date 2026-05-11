package br.ufsc.ine.leb.roza.core.modern.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

class LcsTestCaseSimilarityMeasurerTest {

	private TestCaseSimilarityMeasurer measurer;

	@BeforeEach
	void setup() {
		measurer = new LcsTestCaseSimilarityMeasurer();
	}

	@Test
	void shouldMeasureCommonSubsequence() {
		DecomposedTestCases testCases = testCases(
				testCase("source", statement("a();"), statement("b();"), statement("d();")),
				testCase("target", statement("a();"), statement("c();"), statement("d();")));

		TestCaseSimilarityMatrix matrix = measurer.measure(testCases);

		assertEquals(2.0 / 3.0, matrix.similarity(0, 1), 0.000001);
		assertEquals(2.0 / 3.0, matrix.similarity(1, 0), 0.000001);
	}

	@Test
	void shouldMeasureCommonSubsequenceEvenWhenCommonStatementsDoNotStartTheTest() {
		DecomposedTestCases testCases = testCases(
				testCase("source", statement("x();"), statement("a();"), statement("b();")),
				testCase("target", statement("y();"), statement("a();"), statement("b();")));

		TestCaseSimilarityMatrix matrix = measurer.measure(testCases);

		assertEquals(2.0 / 3.0, matrix.similarity(0, 1), 0.000001);
	}

	@Test
	void shouldStopProjectionAtFirstAssertion() {
		DecomposedTestCases testCases = testCases(
				testCase("source", statement("setup();"), assertion("assertTrue(true);"), statement("sharedAfterAssertion();")),
				testCase("target", statement("setup();"), assertion("assertFalse(false);"), statement("sharedAfterAssertion();")));

		TestCaseSimilarityMatrix matrix = measurer.measure(testCases);

		assertEquals(1.0, matrix.similarity(0, 1));
	}

	@Test
	void shouldReturnZeroWhenBothDistinctTestCasesHaveEmptyProjections() {
		DecomposedTestCases testCases = testCases(
				testCase("source", assertion("assertTrue(true);")),
				testCase("target", assertion("assertFalse(false);")));

		TestCaseSimilarityMatrix matrix = measurer.measure(testCases);

		assertEquals(0.0, matrix.similarity(0, 1));
	}

	@Test
	void shouldKeepDiagonalInitializedWithOne() {
		DecomposedTestCases testCases = testCases(
				testCase("source", statement("setup();")),
				testCase("target", statement("differentSetup();")));

		TestCaseSimilarityMatrix matrix = measurer.measure(testCases);

		assertEquals(1.0, matrix.similarity(0, 0));
		assertEquals(1.0, matrix.similarity(1, 1));
	}

	private DecomposedTestCases testCases(TestCase... testCases) {
		return new DecomposedTestCases(List.of(testCases));
	}

	private TestCase testCase(String name, CodeStatement... statements) {
		return new TestCase(name, new CodeBlock(List.of(statements)));
	}

	private CodeStatement statement(String text) {
		return new CodeStatement(text, text);
	}

	private CodeStatement assertion(String text) {
		return new CodeStatement(text, text, true);
	}
}
