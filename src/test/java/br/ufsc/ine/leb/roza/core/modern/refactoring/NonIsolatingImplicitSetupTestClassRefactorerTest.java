package br.ufsc.ine.leb.roza.core.modern.refactoring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseCluster;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseClusters;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.Field;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureKind;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.HelperMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.SetupAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

class NonIsolatingImplicitSetupTestClassRefactorerTest {

	@Test
	void shouldKeepSingletonLeftoversTogetherInTheOriginalClass() {
		TestClass source = source(
				"Example",
				field("sut"),
				fixture("sut = new Sut();"),
				originalTest("alpha", "sut.save(1);", "assertEquals(1, sut.count());"),
				originalTest("beta", "sut.save(2);", "assertEquals(2, sut.count());"));
		TestCase alpha = decomposed("alpha", source, "Sut sut = new Sut();", "sut.save(1);", "assertEquals(1, sut.count());");
		TestCase beta = decomposed("beta", source, "Sut sut = new Sut();", "sut.save(2);", "assertEquals(2, sut.count());");

		List<TestClass> generated = refactor(
				new TestCaseCluster(0, alpha),
				new TestCaseCluster(1, beta)).testClasses();

		assertEquals(1, generated.size());
		TestClass residual = generated.get(0);
		assertEquals("Example", residual.name());
		assertEquals(Optional.of("example.tests"), residual.packageName());
		assertEquals(List.of("private Sut sut;"), fields(residual));
		assertEquals(List.of("sut = new Sut();"), statements(residual.fixtures().get(0)));
		assertEquals(List.of("alpha", "beta"), methodNames(residual));
		assertEquals(List.of("sut.save(1);", "assertEquals(1, sut.count());"), statements(residual.testMethods().get(0)));
		assertEquals(List.of("sut.save(2);", "assertEquals(2, sut.count());"), statements(residual.testMethods().get(1)));
	}

	@Test
	void shouldExtractSharedClustersAndLeaveRemainingTestsInTheOriginalClass() {
		TestClass source = source(
				"Example",
				field("sut"),
				fixture("sut = new Sut();"),
				originalTest("alpha", "sut.save(1);", "assertEquals(1, sut.count());"),
				originalTest("beta", "sut.save(1);", "assertTrue(sut.exists());"),
				originalTest("gamma", "sut.delete(1);", "assertFalse(sut.exists());"));
		TestCase alpha = decomposed("alpha", source, "Sut sut = new Sut();", "sut.save(1);", "assertEquals(1, sut.count());");
		TestCase beta = decomposed("beta", source, "Sut sut = new Sut();", "sut.save(1);", "assertTrue(sut.exists());");
		TestCase gamma = decomposed("gamma", source, "Sut sut = new Sut();", "sut.delete(1);", "assertFalse(sut.exists());");

		List<TestClass> generated = refactor(
				new TestCaseCluster(0, alpha).merge(new TestCaseCluster(1, beta)),
				new TestCaseCluster(2, gamma)).testClasses();

		assertEquals(List.of("Example", "TestClass1"), generated.stream().map(TestClass::name).collect(Collectors.toList()));
		TestClass residual = generated.get(0);
		assertEquals(List.of("gamma"), methodNames(residual));
		assertEquals(List.of("sut = new Sut();"), statements(residual.fixtures().get(0)));
		assertEquals(List.of("sut.delete(1);", "assertFalse(sut.exists());"), statements(residual.testMethods().get(0)));
		TestClass extracted = generated.get(1);
		assertEquals(List.of("sut = new Sut();", "sut.save(1);"), statements(extracted.fixtures().get(0)));
		assertEquals(List.of("alpha", "beta"), methodNames(extracted));
	}

	@Test
	void shouldKeepLeftoversFromDifferentOriginalClassesApart() {
		TestClass firstSource = source(
				"FirstExample",
				field("sut"),
				fixture("sut = new Sut();"),
				originalTest("alpha", "sut.save(1);", "assertTrue(sut.exists());"));
		TestClass secondSource = source(
				"SecondExample",
				field("other"),
				fixture("other = new Sut();"),
				originalTest("beta", "other.save(2);", "assertTrue(other.exists());"));
		TestCase alpha = decomposed("alpha", firstSource, "Sut sut = new Sut();", "sut.save(1);", "assertTrue(sut.exists());");
		TestCase beta = decomposed("beta", secondSource, "Sut other = new Sut();", "other.save(2);", "assertTrue(other.exists());");

		List<TestClass> generated = refactor(
				new TestCaseCluster(0, alpha),
				new TestCaseCluster(1, beta)).testClasses();

		assertEquals(List.of("FirstExample", "SecondExample"), generated.stream().map(TestClass::name).collect(Collectors.toList()));
		assertEquals(List.of("alpha"), methodNames(generated.get(0)));
		assertEquals(List.of("beta"), methodNames(generated.get(1)));
	}

	@Test
	void shouldPreserveOriginalHelpersOnResidualClasses() {
		HelperMethod helper = new HelperMethod(
				List.of("private"),
				"Sut",
				"createSut",
				List.of(),
				List.of(),
				block(statement("return new Sut();")));
		TestClass source = new TestClass(
				"Example",
				"example.tests",
				junit4Imports(),
				setup(),
				List.of(field("sut")),
				List.of(fixture("sut = createSut();")),
				List.of(helper),
				List.of(originalTest("alpha", "sut.save(1);", "assertTrue(sut.exists());")));
		TestCase alpha = decomposed("alpha", source, "Sut sut = createSut();", "sut.save(1);", "assertTrue(sut.exists());");

		TestClass residual = refactor(new TestCaseCluster(0, alpha)).testClasses().get(0);

		assertEquals(1, residual.helperMethods().size());
		assertEquals("createSut", residual.helperMethods().get(0).name());
	}

	@Test
	void shouldRejectLeftoversWithoutASourceClass() {
		TestCase orphan = new TestCase("alpha", block(assertion("assertTrue(true);")));

		assertThrows(IllegalStateException.class, () -> refactor(new TestCaseCluster(0, orphan)));
	}

	@Test
	void shouldRejectLeftoversWhoseOriginalMethodIsMissing() {
		TestClass source = source(
				"Example",
				field("sut"),
				fixture("sut = new Sut();"),
				originalTest("alpha", "sut.save(1);", "assertTrue(sut.exists());"));
		TestCase unknown = decomposed("missing", source, "Sut sut = new Sut();", "assertTrue(sut.exists());");

		assertThrows(IllegalStateException.class, () -> refactor(new TestCaseCluster(0, unknown)));
	}

	private RefactoredTestClasses refactor(TestCaseCluster... clusters) {
		return new NonIsolatingImplicitSetupTestClassRefactorer().refactor(new TestCaseClusters(List.of(clusters)));
	}

	private TestClass source(String name, Field field, FixtureMethod fixture, TestMethod... tests) {
		return new TestClass(
				name,
				"example.tests",
				junit4Imports(),
				setup(),
				List.of(field),
				List.of(fixture),
				List.of(),
				List.of(tests));
	}

	private TestCase decomposed(String name, TestClass source, String... statements) {
		List<CodeStatement> body = new java.util.ArrayList<>();
		for (int index = 0; index < statements.length; index++) {
			String text = statements[index];
			body.add(index == statements.length - 1 ? assertion(text) : statement(text));
		}
		return new TestCase(name, new CodeBlock(body), source, List.of(testAnnotation()));
	}

	private Field field(String name) {
		return new Field(List.of("private"), "Sut", name, Optional.empty());
	}

	private FixtureMethod fixture(String text) {
		return new FixtureMethod(FixtureKind.BEFORE, "setup", List.of(new CodeAnnotation("Before", "@Before")), block(statement(text)));
	}

	private TestMethod originalTest(String name, String arrange, String assertion) {
		return new TestMethod(name, List.of(testAnnotation()), block(statement(arrange), assertion(assertion)));
	}

	private List<String> junit4Imports() {
		return List.of("import org.junit.Test;", "import org.junit.Before;");
	}

	private SetupAnnotation setup() {
		return new SetupAnnotation(new CodeAnnotation("Before", "@Before"), Optional.of("import org.junit.Before;"));
	}

	private CodeAnnotation testAnnotation() {
		return new CodeAnnotation("Test", "@Test");
	}

	private CodeBlock block(CodeStatement... statements) {
		return new CodeBlock(List.of(statements));
	}

	private CodeStatement statement(String text) {
		return new CodeStatement(text, text);
	}

	private CodeStatement assertion(String text) {
		return new CodeStatement(text, text, true);
	}

	private List<String> fields(TestClass testClass) {
		return testClass.fields().stream()
				.map(field -> String.join(" ", field.modifiers()) + " " + field.type() + " " + field.name() + ";")
				.collect(Collectors.toList());
	}

	private List<String> methodNames(TestClass testClass) {
		return testClass.testMethods().stream().map(TestMethod::name).collect(Collectors.toList());
	}

	private List<String> statements(FixtureMethod fixture) {
		return fixture.body().statements().stream().map(CodeStatement::normalizedText).collect(Collectors.toList());
	}

	private List<String> statements(TestMethod testMethod) {
		return testMethod.body().statements().stream().map(CodeStatement::normalizedText).collect(Collectors.toList());
	}
}
