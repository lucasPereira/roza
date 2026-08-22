package br.ufsc.ine.leb.roza.core.modern.analytics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.DefaultTestCaseDecomposer;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.Field;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureKind;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestCodeViolation;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.ViolationScope;

class SetupCodeProjectionTest {

	@Test
	void shouldProjectOnlySetupAndArrangeCode() {
		TestClass testClass = new TestClass(
				"Example",
				List.of(new Field(List.of("private"), "Sut", "sut", Optional.of(statement("new Sut()")))),
				List.of(
						new FixtureMethod(FixtureKind.BEFORE, "setup", List.of(), block(statement("sut.start();"))),
						new FixtureMethod(FixtureKind.AFTER, "tearDown", List.of(), block(statement("sut.stop();")))),
				List.of(),
				List.of(new TestMethod(
						"test",
						List.of(testAnnotation()),
						block(statement("sut.prepare();"), assertion("assertTrue(sut.ready());"), statement("sut.afterAssertion();")))));

		assertEquals(
				List.of("sut = new Sut();", "sut.start();", "sut.prepare();"),
				SetupCodeProjection.statements(List.of(testClass)));
	}

	@Test
	void shouldKeepTheValidMethodWhenAnotherMethodHasAViolation() {
		TestMethod valid = new TestMethod(
				"valid",
				List.of(testAnnotation()),
				block(statement("sut.prepare();"), assertion("assertTrue(sut.ready());")));
		TestMethod rejected = new TestMethod(
				"rejected",
				List.of(testAnnotation()),
				block(statement("sut.rejectedSetup();"), assertion("assertTrue(sut.ready());")));
		TestClass testClass = new TestClass(
				"Example",
				List.of(new Field(List.of("private"), "Sut", "sut", Optional.of(statement("new Sut()")))),
				List.of(new FixtureMethod(FixtureKind.BEFORE, "setup", List.of(), block(statement("sut.start();")))),
				List.of(),
				List.of(valid, rejected));
		ParsedTestClasses parsed = new ParsedTestClasses(
				List.of(testClass),
				List.of(new TestCodeViolation(ViolationScope.TEST_METHOD, "Example", "rejected", "unsupported")));
		DecomposedTestCases accepted = new DefaultTestCaseDecomposer().decompose(parsed);

		TestClassMetrics metrics = TestClassMetricsCalculator.forEligibleSetupCode(parsed, accepted);

		assertEquals(1, accepted.testCases().size());
		assertEquals(1, metrics.testClasses());
		assertEquals(1, metrics.testMethods());
		assertEquals(3, metrics.totalStatements());
		assertEquals(
				List.of("sut = new Sut();", "sut.start();", "sut.prepare();"),
				SetupCodeProjection.statements(AcceptedTestClassProjection.project(parsed)));
	}

	private static CodeAnnotation testAnnotation() {
		return new CodeAnnotation("Test", "@Test");
	}

	private static CodeBlock block(CodeStatement... statements) {
		return new CodeBlock(List.of(statements));
	}

	private static CodeStatement statement(String text) {
		return new CodeStatement(text, text);
	}

	private static CodeStatement assertion(String text) {
		return new CodeStatement(text, text, true);
	}
}
