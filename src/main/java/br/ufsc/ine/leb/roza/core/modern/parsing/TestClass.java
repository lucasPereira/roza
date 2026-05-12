package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class TestClass {

	private final String name;
	private final String packageName;
	private final List<String> imports;
	private final SetupAnnotation setupAnnotation;
	private final List<Field> fields;
	private final List<FixtureMethod> fixtures;
	private final List<HelperMethod> helperMethods;
	private final List<TestMethod> testMethods;

	public TestClass(String name, List<Field> fields, List<FixtureMethod> fixtures, List<HelperMethod> helperMethods, List<TestMethod> testMethods) {
		this(name, null, List.of(), null, fields, fixtures, helperMethods, testMethods);
	}

	public TestClass(
			String name,
			List<String> imports,
			SetupAnnotation setupAnnotation,
			List<Field> fields,
			List<FixtureMethod> fixtures,
			List<HelperMethod> helperMethods,
			List<TestMethod> testMethods) {
		this(name, null, imports, setupAnnotation, fields, fixtures, helperMethods, testMethods);
	}

	public TestClass(
			String name,
			String packageName,
			List<String> imports,
			SetupAnnotation setupAnnotation,
			List<Field> fields,
			List<FixtureMethod> fixtures,
			List<HelperMethod> helperMethods,
			List<TestMethod> testMethods) {
		this.name = Objects.requireNonNull(name);
		this.packageName = packageName;
		this.imports = List.copyOf(Objects.requireNonNull(imports));
		this.setupAnnotation = setupAnnotation;
		this.fields = List.copyOf(Objects.requireNonNull(fields));
		this.fixtures = List.copyOf(Objects.requireNonNull(fixtures));
		this.helperMethods = List.copyOf(Objects.requireNonNull(helperMethods));
		this.testMethods = List.copyOf(Objects.requireNonNull(testMethods));
	}

	public String name() {
		return name;
	}

	public Optional<String> packageName() {
		return Optional.ofNullable(packageName);
	}

	public List<String> imports() {
		return imports;
	}

	public Optional<SetupAnnotation> setupAnnotation() {
		return Optional.ofNullable(setupAnnotation);
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
