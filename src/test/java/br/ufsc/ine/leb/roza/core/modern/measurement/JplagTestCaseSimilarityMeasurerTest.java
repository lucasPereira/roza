package br.ufsc.ine.leb.roza.core.modern.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

class JplagTestCaseSimilarityMeasurerTest {

	private TestCaseSimilarityMeasurer measurer;

	@BeforeEach
	void setup() {
		measurer = new JplagTestCaseSimilarityMeasurer(new JplagMeasurementConfiguration(1));
	}

	@Test
	void shouldMeasureIdenticalProjectedSetupWithJplag() {
		assumeJplagJarIsAvailable();
		DecomposedTestCases testCases = testCases(
				testCase("source", statement("setup();"), statement("shared();"), assertion("assertEquals(1, value());")),
				testCase("target", statement("setup();"), statement("shared();"), assertion("assertEquals(2, value());")));

		TestCaseSimilarityMatrix matrix = measurer.measure(testCases);

		assertEquals(1.0, matrix.similarity(0, 1), 0.000001);
		assertEquals(1.0, matrix.similarity(1, 0), 0.000001);
	}

	@Test
	void shouldReturnEmptyMatrixForZeroTestCases() {
		TestCaseSimilarityMatrix matrix = measurer.measure(testCases());

		assertEquals(0, matrix.size());
	}

	@Test
	void shouldKeepDiagonalInitializedWithOneForOneTestCase() {
		TestCaseSimilarityMatrix matrix = measurer.measure(testCases(testCase("source", statement("setup();"))));

		assertEquals(1, matrix.size());
		assertEquals(1.0, matrix.similarity(0, 0), 0.000001);
	}

	private void assumeJplagJarIsAvailable() {
		assumeTrue(Files.isRegularFile(Path.of("external-tools/jplag/jplag-2.11.9.jar")));
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
