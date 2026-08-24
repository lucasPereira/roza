package br.ufsc.ine.leb.roza.core.modern.refactoring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.HelperMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;

class RefactoredTestClassesTest {

	@Test
	void shouldKeepExistingHelperClassesAlongsideCreatedHelpers() {
		TestClass existing = helper("example.helpers", "ExistingHelper");
		TestClass created = helper(null, "HelperClass1");
		ParsedTestClasses parsed = new ParsedTestClasses(List.of(existing));

		RefactoredTestClasses refactored = new RefactoredTestClasses(List.of(), List.of(created)).plusExistingHelpers(parsed);

		assertEquals(List.of("HelperClass1", "ExistingHelper"), refactored.helperClasses().stream().map(TestClass::name).collect(java.util.stream.Collectors.toList()));
		assertEquals("example.helpers", refactored.helperClasses().get(1).packageName().orElse(null));
	}

	private TestClass helper(String packageName, String name) {
		return new TestClass(
				name,
				packageName,
				List.of(),
				null,
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
	}
}
