package br.ufsc.ine.leb.roza.core.modern.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

class LccssTestCaseSimilarityMeasurerTest {

	private TestCaseSimilarityMeasurer measurer;

	@BeforeEach
	void setup() {
		measurer = new LccssTestCaseSimilarityMeasurer();
	}

	@Test
	void shouldMeasureCommonContiguousPrefix() {
		DecomposedTestCases testCases = testCases(
				testCase("source", statement("a();"), statement("b();"), statement("c();")),
				testCase("target", statement("a();"), statement("b();"), statement("d();")));

		TestCaseSimilarityMatrix matrix = measurer.measure(testCases);

		assertEquals(2.0 / 3.0, matrix.similarity(0, 1), 0.000001);
	}

	@Test
	void shouldMeasureFixtureDeclarationPrefixBeforeDifferentTestBodies() {
		DecomposedTestCases testCases = testCases(
				testCase(
						"cluster",
						statement("TestCase alpha = new TestCase(\"alpha\", List.of(), List.of());"),
						statement("TestCase beta = new TestCase(\"beta\", List.of(), List.of());"),
						statement("TestCase gamma = new TestCase(\"gamma\", List.of(), List.of());"),
						statement("Cluster alphaCluster = new Cluster(alpha);")),
				testCase(
						"threeTestsStopingInLevelTwoBecauseItHasntClustersToMerge",
						statement("TestCase alpha = new TestCase(\"alpha\", List.of(), List.of());"),
						statement("TestCase beta = new TestCase(\"beta\", List.of(), List.of());"),
						statement("TestCase gamma = new TestCase(\"gamma\", List.of(), List.of());"),
						statement("SimilarityReport report = new SimilarityReportBuilder(true).add(alpha, beta, dotFive).add(gamma).complete().build();")));

		TestCaseSimilarityMatrix matrix = measurer.measure(testCases);

		assertEquals(0.75, matrix.similarity(0, 1), 0.000001);
	}

	@Test
	void shouldStopProjectionAtFirstAssertion() {
		DecomposedTestCases testCases = testCases(
				testCase("source", statement("setup();"), assertion("assertTrue(true);"), statement("after();")),
				testCase("target", statement("setup();"), assertion("assertFalse(false);"), statement("differentAfter();")));

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

	@Test
	void shouldMeasureEveryDirectedPair() {
		DecomposedTestCases testCases = testCases(
				testCase("first", statement("a();")),
				testCase("second", statement("b();")),
				testCase("third", statement("a();")));

		TestCaseSimilarityMatrix matrix = measurer.measure(testCases);

		assertEquals(0.0, matrix.similarity(0, 1));
		assertEquals(1.0, matrix.similarity(0, 2));
		assertEquals(0.0, matrix.similarity(1, 0));
		assertEquals(0.0, matrix.similarity(1, 2));
		assertEquals(1.0, matrix.similarity(2, 0));
		assertEquals(0.0, matrix.similarity(2, 1));
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
