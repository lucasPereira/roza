package br.ufsc.ine.leb.roza.core.modern.decomposition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.parsing.CodeAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.Field;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureKind;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.HelperMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestCodeViolation;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.ViolationScope;

class DefaultTestCaseDecomposerTest {

	private TestCaseDecomposer decomposer;

	@BeforeEach
	void setup() {
		decomposer = new DefaultTestCaseDecomposer();
	}

	@Test
	void shouldDecomposeTestWithoutFieldsAndFixtures() {
		ParsedTestClasses parsedTestClasses = parsedTestClasses(testClass(List.of(), List.of(), List.of(testMethod("shouldWork", "assertTrue(true);"))));

		DecomposedTestCases decomposed = decomposer.decompose(parsedTestClasses);

		assertEquals(1, decomposed.testCases().size());
		assertEquals("shouldWork", decomposed.testCases().get(0).name());
		assertEquals(List.of("assertTrue(true);"), statements(decomposed.testCases().get(0)));
	}

	@Test
	void shouldDeclareFieldWithoutInitializationBeforeTestBody() {
		ParsedTestClasses parsedTestClasses = parsedTestClasses(testClass(List.of(field("Sut", "sut")), List.of(), List.of(testMethod("test", "assertTrue(true);"))));

		DecomposedTestCases decomposed = decomposer.decompose(parsedTestClasses);

		assertEquals(List.of("Sut sut;", "assertTrue(true);"), statements(decomposed.testCases().get(0)));
	}

	@Test
	void shouldDeclareFieldWithInitializationBeforeTestBody() {
		ParsedTestClasses parsedTestClasses = parsedTestClasses(testClass(List.of(field("Sut", "sut", "new Sut()")), List.of(), List.of(testMethod("test", "assertTrue(true);"))));

		DecomposedTestCases decomposed = decomposer.decompose(parsedTestClasses);

		assertEquals(List.of("Sut sut = new Sut();", "assertTrue(true);"), statements(decomposed.testCases().get(0)));
	}

	@Test
	void shouldConcatenateFieldsBeforeFixtureAndTestBody() {
		ParsedTestClasses parsedTestClasses = parsedTestClasses(testClass(
				List.of(field("Sut", "sut")),
				List.of(before("sut = new Sut();", "sut.save(1);")),
				List.of(testMethod("test", "assertEquals(1, sut.count());"))));

		DecomposedTestCases decomposed = decomposer.decompose(parsedTestClasses);

		assertEquals(List.of("Sut sut = new Sut();", "sut.save(1);", "assertEquals(1, sut.count());"), statements(decomposed.testCases().get(0)));
	}

	@Test
	void shouldTurnFixtureFieldAssignmentsIntoInitializedDeclarations() {
		ParsedTestClasses parsedTestClasses = parsedTestClasses(testClass(
				List.of(field("TestCase", "alpha"), field("TestCase", "beta"), field("TestCase", "gamma")),
				List.of(before(
						"alpha = new TestCase(\"alpha\", List.of(), List.of());",
						"beta = new TestCase(\"beta\", List.of(), List.of());",
						"gamma = new TestCase(\"gamma\", List.of(), List.of());")),
				List.of(testMethod("test", "assertTrue(true);"))));

		DecomposedTestCases decomposed = decomposer.decompose(parsedTestClasses);

		assertEquals(
				List.of(
						"TestCase alpha = new TestCase(\"alpha\", List.of(), List.of());",
						"TestCase beta = new TestCase(\"beta\", List.of(), List.of());",
						"TestCase gamma = new TestCase(\"gamma\", List.of(), List.of());",
						"assertTrue(true);"),
				statements(decomposed.testCases().get(0)));
	}

	@Test
	void shouldCreateOneTestCasePerTestMethod() {
		ParsedTestClasses parsedTestClasses = parsedTestClasses(testClass(
				List.of(field("Sut", "sut")),
				List.of(before("sut = new Sut();")),
				List.of(testMethod("first", "assertTrue(true);"), testMethod("second", "assertTrue(false);"))));

		DecomposedTestCases decomposed = decomposer.decompose(parsedTestClasses);

		assertEquals(2, decomposed.testCases().size());
		assertEquals("first", decomposed.testCases().get(0).name());
		assertEquals(List.of("Sut sut = new Sut();", "assertTrue(true);"), statements(decomposed.testCases().get(0)));
		assertEquals("second", decomposed.testCases().get(1).name());
		assertEquals(List.of("Sut sut = new Sut();", "assertTrue(false);"), statements(decomposed.testCases().get(1)));
	}

	@Test
	void shouldPreserveAssertionMetadataFromOriginalTestBody() {
		ParsedTestClasses parsedTestClasses = parsedTestClasses(testClass(
				List.of(field("Sut", "sut")),
				List.of(before("sut = new Sut();")),
				List.of(new TestMethod("test", List.of(annotation("Test")), new CodeBlock(List.of(statement("assertTrue(true);", true)))))));

		DecomposedTestCases decomposed = decomposer.decompose(parsedTestClasses);

		List<CodeStatement> statements = decomposed.testCases().get(0).body().statements();
		assertEquals(false, statements.get(0).isAssertion());
		assertEquals(true, statements.get(1).isAssertion());
	}

	@Test
	void shouldPreserveSourceTestClassAndTestAnnotations() {
		TestClass testClass = testClass(List.of(), List.of(), List.of(testMethod("test", "assertTrue(true);")));
		ParsedTestClasses parsedTestClasses = parsedTestClasses(testClass);

		DecomposedTestCases decomposed = decomposer.decompose(parsedTestClasses);
		TestCase testCase = decomposed.testCases().get(0);

		assertSame(testClass, testCase.sourceTestClass().orElseThrow());
		assertEquals(List.of("Test"), testCase.annotations().stream().map(CodeAnnotation::name).collect(Collectors.toList()));
	}

	@Test
	void shouldSkipTestsFromClassWithViolation() {
		ParsedTestClasses parsedTestClasses = new ParsedTestClasses(
				List.of(testClass(List.of(), List.of(), List.of(testMethod("first", "assertTrue(true);"), testMethod("second", "assertFalse(false);")))),
				List.of(new TestCodeViolation(ViolationScope.TEST_CLASS, "Example", "Unsupported helper method: helper")));

		DecomposedTestCases decomposed = decomposer.decompose(parsedTestClasses);

		assertEquals(0, decomposed.testCases().size());
	}

	@Test
	void shouldSkipOnlyTestWithMethodViolation() {
		ParsedTestClasses parsedTestClasses = new ParsedTestClasses(
				List.of(testClass(List.of(), List.of(), List.of(testMethod("first", "assertTrue(true);"), testMethod("second", "assertFalse(false);")))),
				List.of(new TestCodeViolation(ViolationScope.TEST_METHOD, "Example", "second", "Unsupported test method with parameters: second")));

		DecomposedTestCases decomposed = decomposer.decompose(parsedTestClasses);

		assertEquals(1, decomposed.testCases().size());
		assertEquals("first", decomposed.testCases().get(0).name());
	}

	private ParsedTestClasses parsedTestClasses(TestClass testClass) {
		return new ParsedTestClasses(List.of(testClass));
	}

	private TestClass testClass(List<Field> fields, List<FixtureMethod> fixtures, List<TestMethod> testMethods) {
		return new TestClass("Example", fields, fixtures, List.<HelperMethod>of(), testMethods);
	}

	private Field field(String type, String name) {
		return new Field(List.of(), type, name, Optional.empty());
	}

	private Field field(String type, String name, String initialization) {
		return new Field(List.of(), type, name, Optional.of(statement(initialization)));
	}

	private FixtureMethod before(String... statements) {
		return new FixtureMethod(FixtureKind.BEFORE, "setup", List.of(annotation("Before")), block(statements));
	}

	private TestMethod testMethod(String name, String... statements) {
		return new TestMethod(name, List.of(annotation("Test")), block(statements));
	}

	private CodeAnnotation annotation(String name) {
		return new CodeAnnotation(name, "@" + name);
	}

	private CodeBlock block(String... statements) {
		return new CodeBlock(List.of(statements).stream().map(this::statement).collect(Collectors.toList()));
	}

	private CodeStatement statement(String statement) {
		return new CodeStatement(statement, statement);
	}

	private CodeStatement statement(String statement, boolean assertion) {
		return new CodeStatement(statement, statement, assertion);
	}

	private List<String> statements(TestCase testCase) {
		return testCase.body().statements().stream().map(CodeStatement::normalizedText).collect(Collectors.toList());
	}
}
