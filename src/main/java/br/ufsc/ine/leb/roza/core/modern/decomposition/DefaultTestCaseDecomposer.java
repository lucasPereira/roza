package br.ufsc.ine.leb.roza.core.modern.decomposition;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.Field;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureKind;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

public final class DefaultTestCaseDecomposer implements TestCaseDecomposer {

	@Override
	public DecomposedTestCases decompose(ParsedTestClasses parsedTestClasses) {
		List<TestCase> testCases = new ArrayList<>();
		for (TestClass testClass : parsedTestClasses.testClasses()) {
			for (TestMethod testMethod : testClass.testMethods()) {
				testCases.add(decompose(testClass, testMethod));
			}
		}
		return new DecomposedTestCases(testCases);
	}

	private TestCase decompose(TestClass testClass, TestMethod testMethod) {
		List<CodeStatement> statements = new ArrayList<>();
		testClass.fields().stream().map(this::declare).forEach(statements::add);
		testClass.fixtures().stream().filter(fixture -> fixture.kind() == FixtureKind.BEFORE).map(FixtureMethod::body).map(CodeBlock::statements).forEach(statements::addAll);
		statements.addAll(testMethod.body().statements());
		return new TestCase(testMethod.name(), new CodeBlock(statements));
	}

	private CodeStatement declare(Field field) {
		String declaration = field.initialization()
				.map(initialization -> field.type() + " " + field.name() + " = " + initialization.normalizedText() + ";")
				.orElseGet(() -> field.type() + " " + field.name() + ";");
		return new CodeStatement(declaration, declaration);
	}
}
