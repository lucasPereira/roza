package br.ufsc.ine.leb.roza.core.modern.measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.Field;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;

class ContiguousCommonStatementsSimilarityMeasurerTest {

	@Test
	void shouldScoreAOneStatementRunInTheMiddle() {
		TestCaseSimilarityMatrix matrix = new ContiguousCommonStatementsSimilarityMeasurer(1).measure(testCases(
				testCase("alpha", statement("createUser();"), statement("login();"), assertion("assertTrue(true);")),
				testCase("beta", statement("deleteUser();"), statement("login();"), assertion("assertFalse(false);"))));

		assertEquals(2.0 / 4.0, matrix.similarity(0, 1), 0.000001);
	}

	@Test
	void shouldStopWhenASecondLiveOutAppears() {
		TestCaseSimilarityMatrix matrix = new ContiguousCommonStatementsSimilarityMeasurer(1).measure(testCases(
				testCase("alpha", statement("Sut first = new Sut();"), statement("Sut second = new Sut();"), assertion("assertEquals(first, second);")),
				testCase("beta", statement("Sut first = new Sut();"), statement("Sut second = new Sut();"), assertion("assertNotSame(first, second);"))));

		assertEquals(2.0 / 4.0, matrix.similarity(0, 1), 0.000001);
	}

	@Test
	void shouldIgnoreARunWhenLiveInTypesDiffer() {
		TestClass sutSource = new TestClass("SutTest", List.of(new Field(List.of(), "Sut", "sut", java.util.Optional.empty())), List.of(), List.of(), List.of());
		TestClass otherSource = new TestClass("OtherTest", List.of(new Field(List.of(), "Other", "sut", java.util.Optional.empty())), List.of(), List.of(), List.of());
		TestCaseSimilarityMatrix matrix = new ContiguousCommonStatementsSimilarityMeasurer(1).measure(new DecomposedTestCases(List.of(
				new TestCase("alpha", new CodeBlock(List.of(statement("sut.save(1);"), statement("sut.save(2);"))), sutSource, List.of()),
				new TestCase("beta", new CodeBlock(List.of(statement("sut.save(1);"), statement("sut.save(2);"))), otherSource, List.of()))));

		assertEquals(0.0, matrix.similarity(0, 1));
	}

	@Test
	void shouldDropAOneStatementMatchWhenMinimumLengthIsTwo() {
		TestCaseSimilarityMatrix matrix = new ContiguousCommonStatementsSimilarityMeasurer(2).measure(testCases(
				testCase("alpha", statement("createUser();"), statement("login();"), assertion("assertTrue(true);")),
				testCase("beta", statement("deleteUser();"), statement("login();"), assertion("assertFalse(false);"))));

		assertEquals(0.0, matrix.similarity(0, 1));
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
