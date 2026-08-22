package br.ufsc.ine.leb.roza.core.modern.analytics;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestCodeViolation;

public final class TestClassMetricsCalculator {

	private TestClassMetricsCalculator() {
	}

	public static TestClassMetrics forTestClasses(List<TestClass> testClasses) {
		int testMethods = testClasses.stream().mapToInt(testClass -> testClass.testMethods().size()).sum();
		int setupMethods = testClasses.stream().mapToInt(testClass -> testClass.fixtures().size()).sum();
		int attributes = testClasses.stream().mapToInt(testClass -> testClass.fields().size()).sum();
		TestClassDuplicationAnalyzer.DuplicationMetrics duplication = TestClassDuplicationAnalyzer.analyze(testClasses);
		return new TestClassMetrics(
				testClasses.size(),
				testMethods,
				setupMethods,
				attributes,
				duplication.totalStatements(),
				duplication.duplicatedStatements());
	}

	public static TestClassMetrics forEligibleCode(ParsedTestClasses parsedTestClasses, DecomposedTestCases acceptedTestCases) {
		int testClassesWithoutViolations = parsedTestClasses.testClasses().size() - classesWithAnyViolation(parsedTestClasses).size();
		TestClassMetrics eligibleMetrics = forTestClasses(violationFreeTestClasses(parsedTestClasses));
		return new TestClassMetrics(
				testClassesWithoutViolations,
				acceptedTestCases.testCases().size(),
				eligibleMetrics.setupMethods(),
				eligibleMetrics.attributes(),
				eligibleMetrics.totalStatements(),
				eligibleMetrics.duplicatedStatements());
	}

	private static List<TestClass> violationFreeTestClasses(ParsedTestClasses parsedTestClasses) {
		Set<String> excludedClasses = classesWithAnyViolation(parsedTestClasses);
		return parsedTestClasses.testClasses().stream()
				.filter(testClass -> !excludedClasses.contains(testClass.qualifiedName()))
				.collect(Collectors.toList());
	}

	private static Set<String> classesWithAnyViolation(ParsedTestClasses parsedTestClasses) {
		return parsedTestClasses.violations().stream().map(TestCodeViolation::testClassName).collect(Collectors.toSet());
	}
}
