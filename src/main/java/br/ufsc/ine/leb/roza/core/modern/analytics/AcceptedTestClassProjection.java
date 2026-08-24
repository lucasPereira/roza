package br.ufsc.ine.leb.roza.core.modern.analytics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

public final class AcceptedTestClassProjection {

	public static List<TestClass> project(ParsedTestClasses parsedTestClasses, DecomposedTestCases acceptedTestCases) {
		AcceptedTests acceptedTests = AcceptedTests.from(acceptedTestCases);
		List<TestClass> acceptedClasses = new ArrayList<>();
		for (TestClass testClass : parsedTestClasses.testClasses()) {
			if (testClass.isHelperClass()) {
				acceptedClasses.add(testClass);
				continue;
			}
			List<TestMethod> acceptedMethods = testClass.testMethods().stream()
					.filter(testMethod -> acceptedTests.contains(testClass, testMethod))
					.collect(Collectors.toList());
			if (acceptedMethods.isEmpty()) {
				continue;
			}
			acceptedClasses.add(new TestClass(
					testClass.name(),
					testClass.packageName().orElse(null),
					testClass.imports(),
					testClass.setupAnnotation().orElse(null),
					testClass.fields(),
					testClass.fixtures(),
					testClass.helperMethods(),
					acceptedMethods));
		}
		return List.copyOf(acceptedClasses);
	}

	private static final class AcceptedTests {

		private final Set<String> qualifiedKeys;
		private final Set<String> nameOnlyKeys;

		private AcceptedTests(Set<String> qualifiedKeys, Set<String> nameOnlyKeys) {
			this.qualifiedKeys = qualifiedKeys;
			this.nameOnlyKeys = nameOnlyKeys;
		}

		private static AcceptedTests from(DecomposedTestCases acceptedTestCases) {
			Set<String> qualifiedKeys = new HashSet<>();
			Set<String> nameOnlyKeys = new HashSet<>();
			for (TestCase testCase : acceptedTestCases.testCases()) {
				if (testCase.sourceTestClass().isPresent()) {
					qualifiedKeys.add(key(testCase.sourceTestClass().get().qualifiedName(), testCase.name()));
				} else {
					nameOnlyKeys.add(testCase.name());
				}
			}
			return new AcceptedTests(qualifiedKeys, nameOnlyKeys);
		}

		private boolean contains(TestClass testClass, TestMethod testMethod) {
			return qualifiedKeys.contains(key(testClass.qualifiedName(), testMethod.name()))
					|| nameOnlyKeys.contains(testMethod.name());
		}

		private static String key(String testClassName, String testMethodName) {
			return testClassName + "#" + testMethodName;
		}
	}
}
