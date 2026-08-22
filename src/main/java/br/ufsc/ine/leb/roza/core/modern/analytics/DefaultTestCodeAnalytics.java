package br.ufsc.ine.leb.roza.core.modern.analytics;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestCodeViolation;
import br.ufsc.ine.leb.roza.core.modern.refactoring.RefactoredTestClasses;

public final class DefaultTestCodeAnalytics implements TestCodeAnalytics {

	@Override
	public TestCodeAnalyticsReport analyze(ParsedTestClasses originalTestClasses, DecomposedTestCases acceptedTestCases, RefactoredTestClasses refactoredTestClasses) {
		Objects.requireNonNull(originalTestClasses);
		Objects.requireNonNull(acceptedTestCases);
		Objects.requireNonNull(refactoredTestClasses);
		OriginalTestCodeMetrics original = originalMetrics(originalTestClasses, acceptedTestCases);
		TestCodeMetricComparison comparison = new TestCodeMetricComparison(
				TestClassMetricsCalculator.forEligibleSetupCode(originalTestClasses, acceptedTestCases),
				metricsFor(refactoredTestClasses.testClasses()));
		return new TestCodeAnalyticsReport(original, comparison);
	}

	private OriginalTestCodeMetrics originalMetrics(ParsedTestClasses parsedTestClasses, DecomposedTestCases acceptedTestCases) {
		int testClasses = parsedTestClasses.testClasses().size();
		int testClassesWithViolations = classesWithAnyViolation(parsedTestClasses).size();
		int testMethods = parsedTestClasses.testClasses().stream().mapToInt(testClass -> testClass.testMethods().size()).sum();
		int testMethodsWithoutViolations = acceptedTestCases.testCases().size();
		int testMethodsWithViolations = Math.max(0, testMethods - testMethodsWithoutViolations);
		return new OriginalTestCodeMetrics(
				testClasses,
				Math.max(0, testClasses - testClassesWithViolations),
				testClassesWithViolations,
				testMethods,
				testMethodsWithoutViolations,
				testMethodsWithViolations);
	}

	private TestClassMetrics metricsFor(List<TestClass> testClasses) {
		return TestClassMetricsCalculator.forSetupCode(testClasses);
	}

	private Set<String> classesWithAnyViolation(ParsedTestClasses parsedTestClasses) {
		return parsedTestClasses.violations().stream().map(TestCodeViolation::testClassName).collect(Collectors.toSet());
	}
}
