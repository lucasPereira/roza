package br.ufsc.ine.leb.roza.core.modern.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;

class TestCaseSimilarityMatrixTest {

	@Test
	void shouldInitializeDiagonalWithOneAndOtherSimilaritiesWithZero() {
		TestCaseSimilarityMatrix matrix = new TestCaseSimilarityMatrix(List.of(testCase("first"), testCase("second")));

		assertEquals(2, matrix.size());
		assertEquals(1.0, matrix.similarity(0, 0));
		assertEquals(0.0, matrix.similarity(0, 1));
		assertEquals(0.0, matrix.similarity(1, 0));
		assertEquals(1.0, matrix.similarity(1, 1));
	}

	@Test
	void shouldSetSimilarityByIndex() {
		TestCaseSimilarityMatrix matrix = new TestCaseSimilarityMatrix(List.of(testCase("first"), testCase("second")));

		matrix.setSimilarity(0, 1, 0.5);

		assertEquals(0.5, matrix.similarity(0, 1));
		assertEquals(0.0, matrix.similarity(1, 0));
	}

	private TestCase testCase(String name) {
		return new TestCase(name, new CodeBlock(List.of()));
	}
}
