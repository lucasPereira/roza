package br.ufsc.ine.leb.roza.core.modern.analytics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.parsing.CodeAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.HelperMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

class SetupCodeProjectionTest {

	@Test
	void shouldCountHelperBodiesOnceAndKeepTheCallInTheTest() {
		TestClass testClass = new TestClass(
				"Example",
				List.of(),
				List.of(),
				List.of(),
				List.of(new TestMethod(
						"alpha",
						List.of(new CodeAnnotation("Test", "@Test")),
						new CodeBlock(List.of(
								new CodeStatement("DelegatedSetup1.setup1();", "DelegatedSetup1.setup1();"),
								new CodeStatement("assertTrue(true);", "assertTrue(true);", true))))));
		TestClass helperClass = new TestClass(
				"DelegatedSetup1",
				List.of(),
				List.of(),
				List.of(new HelperMethod(
						List.of("public", "static"),
						"void",
						"setup1",
						List.of(),
						List.of(),
						new CodeBlock(List.of(new CodeStatement("login();", "login();"))))),
				List.of());

		assertEquals(List.of("DelegatedSetup1.setup1();", "login();"), SetupCodeProjection.statements(List.of(testClass, helperClass)));
	}
}
