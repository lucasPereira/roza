package br.ufsc.ine.leb.roza.core.modern.decomposition;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.parsing.CodeAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestCodeViolation;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.ViolationScope;

class TestCodeEligibilityTest {

	@Test
	void shouldCountUnsupportedTestLikeMethodsAsTestsWithViolations() {
		TestClass testClass = new TestClass(
				"Example",
				"example.tests",
				List.of(),
				null,
				List.of(),
				List.of(),
				List.of(),
				List.of(
						testMethod("first"),
						testMethod("second")));
		ParsedTestClasses parsedTestClasses = new ParsedTestClasses(
				List.of(testClass),
				List.of(new TestCodeViolation(
						ViolationScope.TEST_METHOD,
						"example.tests.Example",
						"parameterized",
						"Test method annotation: ParameterizedTest")));

		TestCodeEligibilitySummary summary = new TestCodeEligibilitySummary(parsedTestClasses);

		assertEquals(3, summary.totalTestCount());
		assertEquals(2, summary.acceptedTestCount());
		assertEquals(1, summary.testsWithViolationsCount());
		assertEquals(1, summary.methodLevelViolationTestCount());
		assertEquals(3, summary.totalTestCount(testClass));
		assertEquals(1, summary.testsWithViolationsCount(testClass));
	}

	@Test
	void shouldCountExcludedParsedTestsWithMethodViolations() {
		TestClass testClass = new TestClass(
				"Example",
				"example.tests",
				List.of(),
				null,
				List.of(),
				List.of(),
				List.of(),
				List.of(testMethod("first"), testMethod("second")));
		ParsedTestClasses parsedTestClasses = new ParsedTestClasses(
				List.of(testClass),
				List.of(new TestCodeViolation(
						ViolationScope.TEST_METHOD,
						"example.tests.Example",
						"second",
						"Test method with parameters: second")));

		TestCodeEligibilitySummary summary = new TestCodeEligibilitySummary(parsedTestClasses);

		assertEquals(2, summary.totalTestCount());
		assertEquals(1, summary.acceptedTestCount());
		assertEquals(1, summary.testsWithViolationsCount());
		assertEquals(1, summary.methodLevelViolationTestCount());
		assertEquals(1, summary.methodLevelViolationTestCount(testClass));
	}

	@Test
	void shouldExcludeAllTestsFromClassWithClassLevelViolation() {
		TestClass testClass = new TestClass(
				"Example",
				"example.tests",
				List.of(),
				null,
				List.of(),
				List.of(),
				List.of(),
				List.of(testMethod("first"), testMethod("second")));
		ParsedTestClasses parsedTestClasses = new ParsedTestClasses(
				List.of(testClass),
				List.of(new TestCodeViolation(ViolationScope.TEST_CLASS, "example.tests.Example", "Helper method: helper")));

		TestCodeEligibilitySummary summary = new TestCodeEligibilitySummary(parsedTestClasses);

		assertEquals(2, summary.totalTestCount());
		assertEquals(0, summary.acceptedTestCount());
		assertEquals(2, summary.testsWithViolationsCount());
		assertEquals(0, summary.methodLevelViolationTestCount());
	}

	@Test
	void shouldCombineClassViolationsWithUnsupportedTestLikeMethodsWithoutDoubleCounting() {
		TestClass testClass = new TestClass(
				"Example",
				"example.tests",
				List.of(),
				null,
				List.of(),
				List.of(),
				List.of(),
				List.of(testMethod("first"), testMethod("second")));
		ParsedTestClasses parsedTestClasses = new ParsedTestClasses(
				List.of(testClass),
				List.of(
						new TestCodeViolation(ViolationScope.TEST_CLASS, "example.tests.Example", "Helper method: helper"),
						new TestCodeViolation(
								ViolationScope.TEST_METHOD,
								"example.tests.Example",
								"parameterized",
								"Test method annotation: ParameterizedTest")));

		TestCodeEligibilitySummary summary = new TestCodeEligibilitySummary(parsedTestClasses);

		assertEquals(3, summary.totalTestCount());
		assertEquals(0, summary.acceptedTestCount());
		assertEquals(3, summary.testsWithViolationsCount());
		assertEquals(1, summary.methodLevelViolationTestCount());
	}

	@Test
	void shouldAcceptAllTestsWhenIgnoringViolations() {
		TestClass testClass = new TestClass(
				"Example",
				"example.tests",
				List.of(),
				null,
				List.of(),
				List.of(),
				List.of(),
				List.of(testMethod("first"), testMethod("second")));
		List<TestCodeViolation> violations = List.of(
				new TestCodeViolation(ViolationScope.TEST_CLASS, "example.tests.Example", "Helper method: helper"),
				new TestCodeViolation(ViolationScope.TEST_METHOD, "example.tests.Example", "second", "Test method with parameters: second"));

		TestCodeEligibility eligibility = TestCodeEligibility.of(violations, true);

		assertEquals(true, eligibility.accepts(testClass));
		assertEquals(true, eligibility.accepts(testClass, testClass.testMethods().get(0)));
		assertEquals(true, eligibility.accepts(testClass, testClass.testMethods().get(1)));
	}

	private TestMethod testMethod(String name) {
		return new TestMethod(name, List.of(new CodeAnnotation("Test", "@Test")), List.of(), new CodeBlock(List.of(statement("assertTrue(true);"))));
	}

	private CodeStatement statement(String text) {
		return new CodeStatement(text, text);
	}
}
