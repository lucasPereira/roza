package br.ufsc.ine.leb.roza.core.modern.analytics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.parsing.CodeAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureKind;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

class TestClassDuplicationAnalyzerTest {

	@Test
	void shouldCountSurplusDuplicationAcrossClasses() {
		TestClass first = testClass("First", setup("sut = new Sut();"), test("alpha", statement("sut.open();"), assertion("assertTrue(sut.isOpen());")));
		TestClass second = testClass("Second", setup("sut = new Sut();"), test("beta", statement("sut.close();"), assertion("assertFalse(sut.isOpen());")));

		TestClassDuplicationAnalyzer.DuplicationMetrics metrics = TestClassDuplicationAnalyzer.analyze(List.of(first, second));

		assertEquals(1, metrics.uniqueDuplicatedStatements());
		assertEquals(1, metrics.duplicatedStatements());
	}

	@Test
	void shouldExcludeAssertionsFromDuplicationCount() {
		CodeStatement sharedAssertion = assertion("assertTrue(sut.isOpen());");
		TestClass first = testClass("First", List.of(), test("alpha", sharedAssertion));
		TestClass second = testClass("Second", List.of(), test("beta", sharedAssertion));

		TestClassDuplicationAnalyzer.DuplicationMetrics metrics = TestClassDuplicationAnalyzer.analyze(List.of(first, second));

		assertEquals(0, metrics.uniqueDuplicatedStatements());
		assertEquals(0, metrics.duplicatedStatements());
	}

	@Test
	void shouldCountDuplicationWithinTestBodies() {
		TestClass testClass = testClass(
				"Example",
				List.of(),
				test("alpha", statement("sut = new Sut();"), assertion("assertTrue(sut.isOpen());")),
				test("beta", statement("sut = new Sut();"), assertion("assertFalse(sut.isOpen());")));

		TestClassDuplicationAnalyzer.DuplicationMetrics metrics = TestClassDuplicationAnalyzer.analyze(List.of(testClass));

		assertEquals(1, metrics.uniqueDuplicatedStatements());
		assertEquals(1, metrics.duplicatedStatements());
	}

	@Test
	void shouldCountTotalStatements() {
		TestClass testClass = testClass(
				"Example",
				setup("sut = new Sut();"),
				test("alpha", statement("sut.open();"), assertion("assertTrue(sut.isOpen());")),
				test("beta", assertion("assertFalse(sut.isOpen());")));

		TestClassDuplicationAnalyzer.DuplicationMetrics metrics = TestClassDuplicationAnalyzer.analyze(List.of(testClass));

		assertEquals(2, metrics.totalStatements());
	}

	@Test
	void shouldReportNoDuplicationForUniqueStatements() {
		TestClass testClass = testClass(
				"Example",
				setup("sut = new Sut();"),
				test("alpha", assertion("assertTrue(sut.isOpen());")),
				test("beta", assertion("assertFalse(sut.isOpen());")));

		TestClassDuplicationAnalyzer.DuplicationMetrics metrics = TestClassDuplicationAnalyzer.analyze(List.of(testClass));

		assertEquals(0, metrics.uniqueDuplicatedStatements());
		assertEquals(0, metrics.duplicatedStatements());
	}

	private TestClass testClass(String name, List<FixtureMethod> fixtures, TestMethod... testMethods) {
		return new TestClass(name, List.of(), fixtures, List.of(), List.of(testMethods));
	}

	private List<FixtureMethod> setup(String... statements) {
		return List.of(new FixtureMethod(FixtureKind.BEFORE, "setup", List.of(), new CodeBlock(java.util.Arrays.stream(statements).map(TestClassDuplicationAnalyzerTest::statement).toList())));
	}

	private TestMethod test(String name, CodeStatement... statements) {
		return new TestMethod(name, List.of(new CodeAnnotation("Test", "@Test")), List.of(), new CodeBlock(List.of(statements)));
	}

	private static CodeStatement statement(String text) {
		return new CodeStatement(text, text);
	}

	private static CodeStatement assertion(String text) {
		return new CodeStatement(text, text, true);
	}
}
