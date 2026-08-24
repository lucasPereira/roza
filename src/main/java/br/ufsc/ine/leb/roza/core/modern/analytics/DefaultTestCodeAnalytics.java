package br.ufsc.ine.leb.roza.core.modern.analytics;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCodeEligibilitySummary;
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
		OriginalTestCodeMetrics original = originalMetrics(originalTestClasses);
		TestCodeMetricComparison comparison = new TestCodeMetricComparison(
				TestClassMetricsCalculator.forEligibleSetupCode(originalTestClasses, acceptedTestCases),
				metricsFor(allClasses(refactoredTestClasses)));
		return new TestCodeAnalyticsReport(original, comparison);
	}

	private List<TestClass> allClasses(RefactoredTestClasses refactoredTestClasses) {
		List<TestClass> classes = new java.util.ArrayList<>(refactoredTestClasses.testClasses());
		classes.addAll(refactoredTestClasses.helperClasses());
		return classes;
	}

	private OriginalTestCodeMetrics originalMetrics(ParsedTestClasses parsedTestClasses) {
		List<TestClass> testClassesOnly = parsedTestClasses.testClasses().stream()
				.filter(testClass -> !testClass.isHelperClass())
				.collect(Collectors.toList());
		int testClasses = testClassesOnly.size();
		Set<String> testClassNames = testClassesOnly.stream().map(TestClass::qualifiedName).collect(Collectors.toSet());
		int testClassesWithViolations = (int) classesWithAnyViolation(parsedTestClasses).stream()
				.filter(testClassNames::contains)
				.count();
		TestCodeEligibilitySummary eligibilitySummary = new TestCodeEligibilitySummary(parsedTestClasses);
		int testMethods = eligibilitySummary.totalTestCount();
		int testMethodsWithoutViolations = eligibilitySummary.acceptedTestCount();
		int testMethodsWithViolations = eligibilitySummary.testsWithViolationsCount();
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
