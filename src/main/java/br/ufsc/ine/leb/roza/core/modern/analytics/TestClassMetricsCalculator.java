package br.ufsc.ine.leb.roza.core.modern.analytics;

import java.util.List;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;

public final class TestClassMetricsCalculator {

	public static TestClassMetrics forSetupCode(List<TestClass> testClasses) {
		SetupCodeDuplicationAnalyzer.DuplicationMetrics duplication = SetupCodeDuplicationAnalyzer.analyze(testClasses);
		List<TestClass> countedClasses = testClasses.stream().filter(testClass -> !testClass.isHelperClass()).collect(Collectors.toList());
		return metrics(countedClasses, duplication.totalStatements(), duplication.duplicatedStatements());
	}

	private static TestClassMetrics metrics(List<TestClass> testClasses, int totalStatements, int duplicatedStatements) {
		int testMethods = testClasses.stream().mapToInt(testClass -> testClass.testMethods().size()).sum();
		int setupMethods = testClasses.stream().mapToInt(testClass -> testClass.fixtures().size()).sum();
		int attributes = testClasses.stream().mapToInt(testClass -> testClass.fields().size()).sum();
		return new TestClassMetrics(
				testClasses.size(),
				testMethods,
				setupMethods,
				attributes,
				totalStatements,
				duplicatedStatements);
	}

	public static TestClassMetrics forEligibleSetupCode(ParsedTestClasses parsedTestClasses, DecomposedTestCases acceptedTestCases) {
		List<TestClass> acceptedClasses = AcceptedTestClassProjection.project(parsedTestClasses, acceptedTestCases);
		assertAcceptedTestCount(acceptedClasses, acceptedTestCases);
		return forSetupCode(acceptedClasses);
	}

	private static void assertAcceptedTestCount(List<TestClass> acceptedClasses, DecomposedTestCases acceptedTestCases) {
		int acceptedMethods = acceptedClasses.stream().mapToInt(testClass -> testClass.testMethods().size()).sum();
		if (acceptedMethods != acceptedTestCases.testCases().size()) {
			throw new IllegalStateException("Eligible source projection and decomposed test cases must contain the same tests.");
		}
	}
}
