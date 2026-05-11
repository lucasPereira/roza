package br.ufsc.ine.leb.roza.core.modern.decomposition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.Field;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureKind;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestCodeViolation;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.ViolationScope;

public final class DefaultTestCaseDecomposer implements TestCaseDecomposer {

	@Override
	public DecomposedTestCases decompose(ParsedTestClasses parsedTestClasses) {
		List<TestCase> testCases = new ArrayList<>();
		Set<String> excludedClasses = excludedClasses(parsedTestClasses.violations());
		Set<String> excludedMethods = excludedMethods(parsedTestClasses.violations());
		for (TestClass testClass : parsedTestClasses.testClasses()) {
			if (excludedClasses.contains(testClass.name())) {
				continue;
			}
			for (TestMethod testMethod : testClass.testMethods()) {
				if (excludedMethods.contains(methodKey(testClass.name(), testMethod.name()))) {
					continue;
				}
				testCases.add(decompose(testClass, testMethod));
			}
		}
		return new DecomposedTestCases(testCases);
	}

	private Set<String> excludedClasses(List<TestCodeViolation> violations) {
		Set<String> classes = new HashSet<>();
		violations.stream().filter(violation -> violation.scope() == ViolationScope.TEST_CLASS).map(TestCodeViolation::testClassName).forEach(classes::add);
		return classes;
	}

	private Set<String> excludedMethods(List<TestCodeViolation> violations) {
		Set<String> methods = new HashSet<>();
		violations.stream()
				.filter(violation -> violation.scope() == ViolationScope.TEST_METHOD)
				.filter(violation -> violation.testMethodName().isPresent())
				.map(violation -> methodKey(violation.testClassName(), violation.testMethodName().orElseThrow()))
				.forEach(methods::add);
		return methods;
	}

	private String methodKey(String testClassName, String testMethodName) {
		return testClassName + "#" + testMethodName;
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
