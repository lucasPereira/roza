package br.ufsc.ine.leb.roza.core.modern.refactoring;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;

public final class RefactoredTestClasses {

	private final List<TestClass> testClasses;
	private final List<TestClass> helperClasses;

	public RefactoredTestClasses(List<TestClass> testClasses) {
		this(testClasses, List.of());
	}

	public RefactoredTestClasses(List<TestClass> testClasses, List<TestClass> helperClasses) {
		this.testClasses = List.copyOf(Objects.requireNonNull(testClasses));
		this.helperClasses = List.copyOf(Objects.requireNonNull(helperClasses));
	}

	public List<TestClass> testClasses() {
		return testClasses;
	}

	public List<TestClass> helperClasses() {
		return helperClasses;
	}

	public RefactoredTestClasses plusExistingHelpers(ParsedTestClasses parsedTestClasses) {
		Objects.requireNonNull(parsedTestClasses);
		Set<String> already = helperClasses.stream().map(TestClass::qualifiedName).collect(Collectors.toCollection(LinkedHashSet::new));
		List<TestClass> helpers = new ArrayList<>(helperClasses);
		for (TestClass helper : parsedTestClasses.testClasses()) {
			if (helper.isHelperClass() && already.add(helper.qualifiedName())) {
				helpers.add(helper);
			}
		}
		return new RefactoredTestClasses(testClasses, helpers);
	}
}
