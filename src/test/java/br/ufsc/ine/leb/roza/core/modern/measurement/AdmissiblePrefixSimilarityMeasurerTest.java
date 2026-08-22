package br.ufsc.ine.leb.roza.core.modern.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

class AdmissiblePrefixSimilarityMeasurerTest {

	@Test
	void shouldMeasureAdmissibleReorderingSymmetrically() {
		DecomposedTestCases testCases = testCases(
				testCase(
						"source",
						statement("a();"),
						statement("b();"),
						statement("C c = new C();"),
						statement("consume(c);")),
				testCase(
						"target",
						statement("C c = new C();"),
						statement("a();"),
						statement("b();"),
						statement("consume(c);")));

		TestCaseSimilarityMatrix gap = new GreedyAdmissiblePrefixSimilarityMeasurer().measure(testCases);
		TestCaseSimilarityMatrix map = new MaxAdmissiblePrefixSimilarityMeasurer().measure(testCases);

		assertEquals(1.0, gap.similarity(0, 1));
		assertEquals(gap.similarity(0, 1), gap.similarity(1, 0));
		assertEquals(1.0, map.similarity(0, 1));
		assertEquals(map.similarity(0, 1), map.similarity(1, 0));
	}

	@Test
	void shouldKeepLccssLessThanOrEqualToGapAndGapLessThanOrEqualToMap() {
		DecomposedTestCases testCases = testCases(
				testCase("first", statement("a();"), statement("B b = new B();"), statement("use(b);")),
				testCase("second", statement("B b = new B();"), statement("a();"), statement("use(b);")),
				testCase("third", statement("a();"), statement("c();")));
		TestCaseSimilarityMatrix lccss = new LccssTestCaseSimilarityMeasurer().measure(testCases);
		TestCaseSimilarityMatrix gap = new GreedyAdmissiblePrefixSimilarityMeasurer().measure(testCases);
		TestCaseSimilarityMatrix map = new MaxAdmissiblePrefixSimilarityMeasurer().measure(testCases);

		for (int source = 0; source < testCases.testCases().size(); source++) {
			for (int target = 0; target < testCases.testCases().size(); target++) {
				assertTrue(lccss.similarity(source, target) <= gap.similarity(source, target));
				assertTrue(gap.similarity(source, target) <= map.similarity(source, target));
			}
		}
	}

	@Test
	void shouldRejectNonPositiveMapNodeLimit() {
		org.junit.jupiter.api.Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> new MaxAdmissiblePrefixSimilarityMeasurer(0));
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
}
