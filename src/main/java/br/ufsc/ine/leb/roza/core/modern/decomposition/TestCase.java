package br.ufsc.ine.leb.roza.core.modern.decomposition;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeAnnotation;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;

public final class TestCase {

	private final String name;
	private final CodeBlock body;
	private final TestClass sourceTestClass;
	private final List<CodeAnnotation> annotations;

	public TestCase(String name, CodeBlock body) {
		this(name, body, null, List.of());
	}

	public TestCase(String name, CodeBlock body, TestClass sourceTestClass, List<CodeAnnotation> annotations) {
		this.name = Objects.requireNonNull(name);
		this.body = Objects.requireNonNull(body);
		this.sourceTestClass = sourceTestClass;
		this.annotations = List.copyOf(Objects.requireNonNull(annotations));
	}

	public String name() {
		return name;
	}

	public CodeBlock body() {
		return body;
	}

	public Optional<TestClass> sourceTestClass() {
		return Optional.ofNullable(sourceTestClass);
	}

	public List<CodeAnnotation> annotations() {
		return annotations;
	}
}
