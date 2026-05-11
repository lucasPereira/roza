package br.ufsc.ine.leb.roza.core.modern.decomposition;

import java.util.List;
import java.util.Objects;

public final class DecomposedTestCases {

	private final List<TestCase> testCases;

	public DecomposedTestCases(List<TestCase> testCases) {
		this.testCases = List.copyOf(Objects.requireNonNull(testCases));
	}

	public List<TestCase> testCases() {
		return testCases;
	}
}
