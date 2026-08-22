package br.ufsc.ine.leb.roza.core.modern.analytics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCodeEligibility;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

public final class AcceptedTestClassProjection {

	public static List<TestClass> project(ParsedTestClasses parsedTestClasses) {
		TestCodeEligibility eligibility = new TestCodeEligibility(parsedTestClasses.violations());
		List<TestClass> acceptedClasses = new ArrayList<>();
		for (TestClass testClass : parsedTestClasses.testClasses()) {
			if (!eligibility.accepts(testClass)) {
				continue;
			}
			List<TestMethod> acceptedMethods = testClass.testMethods().stream()
					.filter(testMethod -> eligibility.accepts(testClass, testMethod))
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
}
