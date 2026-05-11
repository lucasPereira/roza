package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.List;
import java.util.Objects;

public final class TestClass {

	private final String name;
	private final List<Field> fields;
	private final List<FixtureMethod> fixtures;
	private final List<HelperMethod> helperMethods;
	private final List<TestMethod> testMethods;

	public TestClass(String name, List<Field> fields, List<FixtureMethod> fixtures, List<HelperMethod> helperMethods, List<TestMethod> testMethods) {
		this.name = Objects.requireNonNull(name);
		this.fields = List.copyOf(Objects.requireNonNull(fields));
		this.fixtures = List.copyOf(Objects.requireNonNull(fixtures));
		this.helperMethods = List.copyOf(Objects.requireNonNull(helperMethods));
		this.testMethods = List.copyOf(Objects.requireNonNull(testMethods));
	}

	public String name() {
		return name;
	}

	public List<Field> fields() {
		return fields;
	}

	public List<FixtureMethod> fixtures() {
		return fixtures;
	}

	public List<HelperMethod> helperMethods() {
		return helperMethods;
	}

	public List<TestMethod> testMethods() {
		return testMethods;
	}
}
