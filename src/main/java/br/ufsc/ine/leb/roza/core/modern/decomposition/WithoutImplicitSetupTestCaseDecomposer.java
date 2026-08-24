package br.ufsc.ine.leb.roza.core.modern.decomposition;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

public final class WithoutImplicitSetupTestCaseDecomposer implements TestCaseDecomposer {

	private final boolean ignoreViolations;

	public WithoutImplicitSetupTestCaseDecomposer() {
		this(false);
	}

	public WithoutImplicitSetupTestCaseDecomposer(boolean ignoreViolations) {
		this.ignoreViolations = ignoreViolations;
	}

	@Override
	public DecomposedTestCases decompose(ParsedTestClasses parsedTestClasses) {
		List<TestCase> testCases = new ArrayList<>();
		TestCodeEligibility eligibility = TestCodeEligibility.of(parsedTestClasses.violations(), ignoreViolations);
		for (TestClass testClass : parsedTestClasses.testClasses()) {
			if (!eligibility.accepts(testClass)) {
				continue;
			}
			for (TestMethod testMethod : testClass.testMethods()) {
				if (!eligibility.accepts(testClass, testMethod)) {
					continue;
				}
				testCases.add(new TestCase(
						testMethod.name(),
						testMethod.body(),
						testClass,
						testMethod.annotations(),
						testMethod.thrownExceptions()));
			}
		}
		return new DecomposedTestCases(testCases);
	}
}
