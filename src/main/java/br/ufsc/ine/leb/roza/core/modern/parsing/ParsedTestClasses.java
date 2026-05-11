package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.List;
import java.util.Objects;

public final class ParsedTestClasses {

	private final List<TestClass> testClasses;
	private final List<TestCodeViolation> violations;

	public ParsedTestClasses(List<TestClass> testClasses) {
		this(testClasses, List.of());
	}

	public ParsedTestClasses(List<TestClass> testClasses, List<TestCodeViolation> violations) {
		this.testClasses = List.copyOf(Objects.requireNonNull(testClasses));
		this.violations = List.copyOf(Objects.requireNonNull(violations));
	}

	public List<TestClass> testClasses() {
		return testClasses;
	}

	public List<TestCodeViolation> violations() {
		return violations;
	}
}
