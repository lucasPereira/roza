package br.ufsc.ine.leb.roza.core.modern.decomposition;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

public final class WithoutImplicitSetupTestCaseDecomposer implements TestCaseDecomposer {

	@Override
	public DecomposedTestCases decompose(ParsedTestClasses parsedTestClasses) {
		List<TestCase> testCases = new ArrayList<>();
		TestCodeEligibility eligibility = new TestCodeEligibility(parsedTestClasses.violations());
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
