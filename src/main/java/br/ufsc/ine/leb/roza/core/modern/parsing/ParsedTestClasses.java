package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.List;
import java.util.Objects;

public final class ParsedTestClasses {

	private final List<TestClass> testClasses;

	public ParsedTestClasses(List<TestClass> testClasses) {
		this.testClasses = List.copyOf(Objects.requireNonNull(testClasses));
	}

	public List<TestClass> testClasses() {
		return testClasses;
	}
}
