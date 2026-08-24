package br.ufsc.ine.leb.roza.core.modern.refactoring;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
