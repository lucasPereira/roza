package br.ufsc.ine.leb.roza.core.modern.decomposition;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.ViolationScope;

public final class TestCodeEligibilitySummary {

	private final ParsedTestClasses parsedTestClasses;
	private final TestCodeEligibility eligibility;
	private final Set<String> parsedTestKeys;
	private final Set<String> methodViolationTestKeys;

	public TestCodeEligibilitySummary(ParsedTestClasses parsedTestClasses) {
		this.parsedTestClasses = Objects.requireNonNull(parsedTestClasses);
		eligibility = new TestCodeEligibility(parsedTestClasses.violations());
		parsedTestKeys = parsedTestKeys(parsedTestClasses);
		methodViolationTestKeys = parsedTestClasses.violations()
				.stream()
				.filter(violation -> violation.scope() == ViolationScope.TEST_METHOD)
				.flatMap(violation -> violation.testMethodName()
						.map(methodName -> testKey(violation.testClassName(), methodName))
						.stream())
				.collect(Collectors.toSet());
	}

	public int totalTestCount() {
		int parsedTests = parsedTestClasses.testClasses().stream().mapToInt(testClass -> testClass.testMethods().size()).sum();
		return parsedTests + unparsedViolatedTestCount(methodViolationTestKeys, parsedTestKeys);
	}

	public int acceptedTestCount() {
		return parsedTestClasses.testClasses().stream().mapToInt(this::acceptedTestCount).sum();
	}

	public int testsWithViolationsCount() {
		return totalTestCount() - acceptedTestCount();
	}

	public int methodLevelViolationTestCount() {
		return methodViolationTestKeys.size();
	}

	public int totalTestCount(TestClass testClass) {
		Set<String> parsedClassTestKeys = parsedTestKeys(testClass);
		Set<String> classMethodViolationTestKeys = methodViolationTestKeys(testClass);
		return testClass.testMethods().size() + unparsedViolatedTestCount(classMethodViolationTestKeys, parsedClassTestKeys);
	}

	public int acceptedTestCount(TestClass testClass) {
		return (int) testClass.testMethods().stream().filter(testMethod -> eligibility.accepts(testClass, testMethod)).count();
	}

	public int testsWithViolationsCount(TestClass testClass) {
		return totalTestCount(testClass) - acceptedTestCount(testClass);
	}

	public int methodLevelViolationTestCount(TestClass testClass) {
		return methodViolationTestKeys(testClass).size();
	}

	private int unparsedViolatedTestCount(Set<String> violationTestKeys, Set<String> parsedKeys) {
		return (int) violationTestKeys.stream().filter(testKey -> !parsedKeys.contains(testKey)).count();
	}

	private Set<String> parsedTestKeys(ParsedTestClasses parsedTestClasses) {
		Set<String> keys = new HashSet<>();
		for (TestClass testClass : parsedTestClasses.testClasses()) {
			keys.addAll(parsedTestKeys(testClass));
		}
		return keys;
	}

	private Set<String> parsedTestKeys(TestClass testClass) {
		return testClass.testMethods()
				.stream()
				.map(testMethod -> testKey(testClass.qualifiedName(), testMethod.name()))
				.collect(Collectors.toSet());
	}

	private Set<String> methodViolationTestKeys(TestClass testClass) {
		String qualifiedName = testClass.qualifiedName();
		return parsedTestClasses.violations()
				.stream()
				.filter(violation -> violation.scope() == ViolationScope.TEST_METHOD)
				.filter(violation -> violation.testClassName().equals(qualifiedName))
				.flatMap(violation -> violation.testMethodName()
						.map(methodName -> testKey(violation.testClassName(), methodName))
						.stream())
				.collect(Collectors.toSet());
	}

	private String testKey(String testClassName, String testMethodName) {
		return testClassName + "#" + testMethodName;
	}
}
