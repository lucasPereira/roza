package br.ufsc.ine.leb.roza.core.modern.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

class SetupExtractionPotentialTestCaseSimilarityMeasurerTest {

	private final TestCaseSimilarityMeasurer measurer = new SetupExtractionPotentialTestCaseSimilarityMeasurer();

	@Test
	void shouldMeasureAbsoluteSharedSetupPrefixSize() {
		DecomposedTestCases testCases = testCases(
				testCase("source", statement("a();"), statement("b();"), statement("c();"), assertion("assertTrue(true);")),
				testCase("target", statement("a();"), statement("b();"), statement("c();"), assertion("assertFalse(false);")),
				testCase("short", statement("a();"), assertion("assertTrue(true);")));

		TestCaseSimilarityMatrix matrix = measurer.measure(testCases);

		assertEquals(3.0, matrix.similarity(0, 1));
		assertEquals(1.0, matrix.similarity(0, 2));
		assertEquals(1.0, matrix.similarity(2, 0));
	}

	@Test
	void shouldStopProjectionAtFirstAssertion() {
		DecomposedTestCases testCases = testCases(
				testCase("source", statement("setup();"), assertion("assertTrue(true);"), statement("sharedAfter();")),
				testCase("target", statement("setup();"), assertion("assertFalse(false);"), statement("sharedAfter();")));

		TestCaseSimilarityMatrix matrix = measurer.measure(testCases);

		assertEquals(1.0, matrix.similarity(0, 1));
	}

	@Test
	void shouldReturnZeroForEmptySetupProjections() {
		DecomposedTestCases testCases = testCases(
				testCase("source", assertion("assertTrue(true);")),
				testCase("target", assertion("assertFalse(false);")));

		TestCaseSimilarityMatrix matrix = measurer.measure(testCases);

		assertEquals(0.0, matrix.similarity(0, 1));
		assertEquals(0.0, matrix.similarity(0, 0));
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
