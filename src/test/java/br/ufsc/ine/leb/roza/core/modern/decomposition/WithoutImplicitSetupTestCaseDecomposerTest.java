package br.ufsc.ine.leb.roza.core.modern.decomposition;

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
import br.ufsc.ine.leb.roza.core.modern.parsing.HelperMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

class WithoutImplicitSetupTestCaseDecomposerTest {

	@Test
	void shouldKeepOnlyTheOriginalTestMethodBody() {
		ParsedTestClasses parsed = new ParsedTestClasses(List.of(new TestClass(
				"Example",
				List.of(new Field(List.of(), "Sut", "sut", Optional.empty())),
				List.of(new FixtureMethod(FixtureKind.BEFORE, "setup", List.of(new CodeAnnotation("Before", "@Before")), block("sut = new Sut();"))),
				List.<HelperMethod>of(),
				List.of(new TestMethod("test", List.of(new CodeAnnotation("Test", "@Test")), block("sut.save(1);", "assertEquals(1, sut.count());"))))));

		DecomposedTestCases decomposed = new WithoutImplicitSetupTestCaseDecomposer().decompose(parsed);

		assertEquals(List.of("sut.save(1);", "assertEquals(1, sut.count());"), statements(decomposed.testCases().get(0)));
	}

	private CodeBlock block(String... statements) {
		return new CodeBlock(List.of(statements).stream().map(statement -> new CodeStatement(statement, statement)).collect(Collectors.toList()));
	}

	private List<String> statements(TestCase testCase) {
		return testCase.body().statements().stream().map(CodeStatement::normalizedText).collect(Collectors.toList());
	}
}
