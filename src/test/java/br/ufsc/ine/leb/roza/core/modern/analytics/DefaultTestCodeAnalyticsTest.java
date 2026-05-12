package br.ufsc.ine.leb.roza.core.modern.analytics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.Field;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureKind;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.HelperMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestCodeViolation;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.ViolationScope;
import br.ufsc.ine.leb.roza.core.modern.refactoring.RefactoredTestClasses;

class DefaultTestCodeAnalyticsTest {

	@Test
	void shouldAnalyzeOriginalViolationsAndReuseWithoutViolationCountsInComparison() {
		ParsedTestClasses original = new ParsedTestClasses(
				List.of(
						testClass("AcceptedTest", 2, 1, "acceptedTest"),
						testClass("PartiallyRejectedTest", 1, 0, "acceptedMethod", "rejectedMethod"),
						testClass("RejectedTest", 1, 0, "rejectedTest")),
				List.of(
						new TestCodeViolation(ViolationScope.TEST_METHOD, "PartiallyRejectedTest", "rejectedMethod", "unsupported method"),
						new TestCodeViolation(ViolationScope.TEST_CLASS, "RejectedTest", "unsupported class")));
		DecomposedTestCases accepted = new DecomposedTestCases(List.of(testCase("acceptedTest"), testCase("acceptedMethod")));
		RefactoredTestClasses refactored = new RefactoredTestClasses(List.of(testClass("GeneratedTest", 3, 1, "first", "second")));

		TestCodeAnalyticsReport report = new DefaultTestCodeAnalytics().analyze(original, accepted, refactored);

		assertEquals(3, report.original().testClasses());
		assertEquals(1, report.original().testClassesWithoutViolations());
		assertEquals(2, report.original().testClassesWithViolations());
		assertEquals(4, report.original().testMethods());
		assertEquals(2, report.original().testMethodsWithoutViolations());
		assertEquals(2, report.original().testMethodsWithViolations());
		assertEquals(1, report.comparison().original().testClasses());
		assertEquals(2, report.comparison().original().testMethods());
		assertEquals(1, report.comparison().original().setupMethods());
		assertEquals(2, report.comparison().original().fields());
		assertEquals(1, report.comparison().refactored().testClasses());
		assertEquals(2, report.comparison().refactored().testMethods());
		assertEquals(1, report.comparison().refactored().setupMethods());
		assertEquals(3, report.comparison().refactored().fields());
	}

	private TestClass testClass(String name, int fields, int fixtures, String... tests) {
		return new TestClass(
				name,
				fieldList(fields),
				fixtureList(fixtures),
				List.<HelperMethod>of(),
				List.of(tests).stream().map(this::testMethod).collect(java.util.stream.Collectors.toList()));
	}

	private List<Field> fieldList(int size) {
		return java.util.stream.IntStream.range(0, size)
				.mapToObj(index -> new Field(List.of("private"), "String", "field" + index, Optional.empty()))
				.collect(java.util.stream.Collectors.toList());
	}

	private List<FixtureMethod> fixtureList(int size) {
		return java.util.stream.IntStream.range(0, size)
				.mapToObj(index -> new FixtureMethod(FixtureKind.BEFORE, "setup" + index, List.of(annotation("Before")), block("field" + index + " = \"value\";")))
				.collect(java.util.stream.Collectors.toList());
	}

	private TestMethod testMethod(String name) {
		return new TestMethod(name, List.of(annotation("Test")), block("assertTrue(true);"));
	}

	private TestCase testCase(String name) {
		return new TestCase(name, block("assertTrue(true);"));
	}

	private CodeAnnotation annotation(String name) {
		return new CodeAnnotation(name, "@" + name);
	}

	private CodeBlock block(String... statements) {
		return new CodeBlock(List.of(statements).stream().map(statement -> new CodeStatement(statement, statement)).collect(java.util.stream.Collectors.toList()));
	}
}
