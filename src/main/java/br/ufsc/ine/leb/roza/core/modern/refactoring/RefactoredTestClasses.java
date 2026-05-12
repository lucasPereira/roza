package br.ufsc.ine.leb.roza.core.modern.refactoring;

import java.util.List;
import java.util.Objects;

import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;

public final class RefactoredTestClasses {

	private final List<TestClass> testClasses;

	public RefactoredTestClasses(List<TestClass> testClasses) {
		this.testClasses = List.copyOf(Objects.requireNonNull(testClasses));
	}

	public List<TestClass> testClasses() {
		return testClasses;
	}
}
