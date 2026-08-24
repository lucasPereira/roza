package br.ufsc.ine.leb.roza.core.modern.refactoring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

class ImplicitSetupTestClassRefactorerTest {

	@Test
	void shouldKeepSourceFieldsAndBeforeWhenTestCasesAreOriginalMethodBodies() {
		TestClass source = source(
				"LoginTest",
				originalTest("alpha", "sut.login(\"a\");", "assertTrue(sut.isLoggedIn());"),
				originalTest("beta", "sut.login(\"b\");", "assertTrue(sut.isLoggedIn());"),
				originalTest("gamma", "sut.logout();", "assertFalse(sut.isLoggedIn());"));
		TestCase alpha = methodOnly("alpha", source, "sut.login(\"a\");", "assertTrue(sut.isLoggedIn());");
		TestCase beta = methodOnly("beta", source, "sut.login(\"b\");", "assertTrue(sut.isLoggedIn());");
		TestCase gamma = methodOnly("gamma", source, "sut.logout();", "assertFalse(sut.isLoggedIn());");

		List<TestClass> generated = new ImplicitSetupTestClassRefactorer().refactor(new TestCaseClusters(List.of(
				new TestCaseCluster(0, alpha).merge(new TestCaseCluster(1, beta)),
				new TestCaseCluster(2, gamma)))).testClasses();

		assertEquals(List.of("TestClass1", "TestClass2"), generated.stream().map(TestClass::name).collect(Collectors.toList()));
		assertEquals(List.of("private Sut sut;"), fields(generated.get(0)));
		assertEquals(List.of("sut = new Sut();"), statements(generated.get(0).fixtures().get(0)));
		assertEquals(List.of("sut.login(\"a\");", "assertTrue(sut.isLoggedIn());"), statements(generated.get(0).testMethods().get(0)));
		assertEquals(List.of("private Sut sut;"), fields(generated.get(1)));
		assertEquals(List.of("sut = new Sut();"), statements(generated.get(1).fixtures().get(0)));
		assertEquals(List.of("sut.logout();", "assertFalse(sut.isLoggedIn());"), statements(generated.get(1).testMethods().get(0)));
	}

	@Test
	void shouldMoveOriginalHelpersToASeparateHelperClassWithoutRewritingCalls() {
		HelperMethod helper = new HelperMethod(
				List.of("private"),
				"void",
				"login",
				List.of(),
				List.of(),
				new CodeBlock(List.of(new CodeStatement("sut.login();", "sut.login();"))));
		TestClass source = new TestClass(
				"LoginTest",
				"example.tests",
				List.of("import org.junit.Test;", "import org.junit.Before;"),
				new SetupAnnotation(new CodeAnnotation("Before", "@Before"), Optional.of("import org.junit.Before;")),
				List.of(new Field(List.of("private"), "Sut", "sut", Optional.empty())),
				List.of(new FixtureMethod(
						FixtureKind.BEFORE,
						"setup",
						List.of(new CodeAnnotation("Before", "@Before")),
						new CodeBlock(List.of(new CodeStatement("sut = new Sut();", "sut = new Sut();"))))),
				List.of(helper),
				List.of(
						originalTest("alpha", "login();", "assertTrue(sut.isLoggedIn());"),
						originalTest("beta", "login();", "assertTrue(sut.isLoggedIn());")));
		TestCase alpha = methodOnly("alpha", source, "login();", "assertTrue(sut.isLoggedIn());");
		TestCase beta = methodOnly("beta", source, "login();", "assertTrue(sut.isLoggedIn());");

		RefactoredTestClasses refactored = new ImplicitSetupTestClassRefactorer().refactor(new TestCaseClusters(List.of(
				new TestCaseCluster(0, alpha).merge(new TestCaseCluster(1, beta)))));

		assertTrue(refactored.testClasses().get(0).helperMethods().isEmpty());
		assertEquals(1, refactored.helperClasses().size());
		assertEquals("LoginTestHelpers", refactored.helperClasses().get(0).name());
		assertTrue(refactored.helperClasses().get(0).packageName().isEmpty());
		assertEquals("login", refactored.helperClasses().get(0).helperMethods().get(0).name());
		assertEquals("login();", refactored.testClasses().get(0).fixtures().get(0).body().statements().get(1).normalizedText());
	}

	@Test
	void shouldKeepHelperCallSitesWhenGeneratedClassesUseDecomposedBodies() {
		HelperMethod helper = new HelperMethod(
				List.of("private"),
				"Sut",
				"createSut",
				List.of(),
				List.of(),
				new CodeBlock(List.of(new CodeStatement("return new Sut();", "return new Sut();"))));
		TestClass source = new TestClass(
				"Example",
				"example.tests",
				List.of("import org.junit.Test;", "import org.junit.Before;"),
				new SetupAnnotation(new CodeAnnotation("Before", "@Before"), Optional.of("import org.junit.Before;")),
				List.of(),
				List.of(),
				List.of(helper),
				List.of(
						originalTest("alpha", "createSut();", "assertTrue(true);"),
						originalTest("beta", "createSut();", "assertFalse(false);")));
		TestCase alpha = methodOnly("alpha", source, "Sut sut = createSut();", "assertTrue(true);");
		TestCase beta = methodOnly("beta", source, "Sut sut = createSut();", "assertFalse(false);");

		RefactoredTestClasses refactored = new ImplicitSetupTestClassRefactorer().refactor(new TestCaseClusters(List.of(
				new TestCaseCluster(0, alpha).merge(new TestCaseCluster(1, beta)))));

		assertTrue(refactored.testClasses().get(0).helperMethods().isEmpty());
		assertEquals("ExampleHelpers", refactored.helperClasses().get(0).name());
		assertEquals("createSut", refactored.helperClasses().get(0).helperMethods().get(0).name());
		assertEquals("sut = createSut();", refactored.testClasses().get(0).fixtures().get(0).body().statements().get(0).normalizedText());
	}

	private TestClass source(String name, TestMethod... tests) {
		return new TestClass(
				name,
				"example.tests",
				List.of("import org.junit.Test;", "import org.junit.Before;"),
				new SetupAnnotation(new CodeAnnotation("Before", "@Before"), Optional.of("import org.junit.Before;")),
				List.of(new Field(List.of("private"), "Sut", "sut", Optional.empty())),
				List.of(new FixtureMethod(
						FixtureKind.BEFORE,
						"setup",
						List.of(new CodeAnnotation("Before", "@Before")),
						new CodeBlock(List.of(new CodeStatement("sut = new Sut();", "sut = new Sut();"))))),
				List.of(),
				List.of(tests));
	}

	private TestCase methodOnly(String name, TestClass source, String arrange, String assertion) {
		return new TestCase(
				name,
				new CodeBlock(List.of(new CodeStatement(arrange, arrange), new CodeStatement(assertion, assertion, true))),
				source,
				List.of(new CodeAnnotation("Test", "@Test")));
	}

	private TestMethod originalTest(String name, String arrange, String assertion) {
		return new TestMethod(
				name,
				List.of(new CodeAnnotation("Test", "@Test")),
				new CodeBlock(List.of(new CodeStatement(arrange, arrange), new CodeStatement(assertion, assertion, true))));
	}

	private List<String> fields(TestClass testClass) {
		return testClass.fields().stream()
				.map(field -> String.join(" ", field.modifiers()) + " " + field.type() + " " + field.name() + ";")
				.collect(Collectors.toList());
	}

	private List<String> statements(FixtureMethod fixture) {
		return fixture.body().statements().stream().map(CodeStatement::normalizedText).collect(Collectors.toList());
	}

	private List<String> statements(TestMethod testMethod) {
		return testMethod.body().statements().stream().map(CodeStatement::normalizedText).collect(Collectors.toList());
	}
}
