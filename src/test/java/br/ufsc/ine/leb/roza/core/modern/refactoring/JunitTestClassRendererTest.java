package br.ufsc.ine.leb.roza.core.modern.refactoring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.parsing.CodeAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.Field;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureKind;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.SetupAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

class JunitTestClassRendererTest {

	@Test
	void shouldRenderRefactoredTestClassAsJavaCode() {
		TestClass testClass = new TestClass(
				"TestClass1",
				"example.tests",
				List.of("import org.junit.Before;", "import org.junit.Test;"),
				new SetupAnnotation(annotation("Before"), Optional.of("import org.junit.Before;")),
				List.of(new Field(List.of("private"), "Sut", "sut", Optional.empty())),
				List.of(new FixtureMethod(FixtureKind.BEFORE, "setup", List.of(annotation("Before")), block("sut = new Sut();"))),
				List.of(),
				List.of(new TestMethod("alpha", List.of(annotation("Test")), block("assertTrue(sut.exists());"))));

		String rendered = new JunitTestClassRenderer().render(testClass);

		assertEquals(String.join(
				"\n",
				"package example.tests;",
				"",
				"import org.junit.Before;",
				"import org.junit.Test;",
				"",
				"public class TestClass1 {",
				"\tprivate Sut sut;",
				"",
				"\t@Before",
				"\tpublic void setup() {",
				"\t\tsut = new Sut();",
				"\t}",
				"",
				"\t@Test",
				"\tpublic void alpha() {",
				"\t\tassertTrue(sut.exists());",
				"\t}",
				"",
				"}"),
				rendered);
	}

	@Test
	void shouldRenderThrownExceptions() {
		TestClass testClass = new TestClass(
				"TestClass1",
				List.of(),
				null,
				List.of(),
				List.of(new FixtureMethod(FixtureKind.BEFORE, "setup", List.of(annotation("Before")), List.of("UnknownHostException"), block("localHost = InetAddress.getLocalHost();"))),
				List.of(),
				List.of(new TestMethod("alpha", List.of(annotation("Test")), List.of("IOException", "InterruptedException"), block("assertTrue(true);"))));

		String rendered = new JunitTestClassRenderer().render(testClass);

		assertEquals(String.join(
				"\n",
				"public class TestClass1 {",
				"\t@Before",
				"\tpublic void setup() throws UnknownHostException {",
				"\t\tlocalHost = InetAddress.getLocalHost();",
				"\t}",
				"",
				"\t@Test",
				"\tpublic void alpha() throws IOException, InterruptedException {",
				"\t\tassertTrue(true);",
				"\t}",
				"",
				"}"),
				rendered);
	}

	private CodeAnnotation annotation(String name) {
		return new CodeAnnotation(name, "@" + name);
	}

	private CodeBlock block(String... statements) {
		return new CodeBlock(List.of(statements).stream().map(statement -> new CodeStatement(statement, statement)).collect(Collectors.toList()));
	}
}
