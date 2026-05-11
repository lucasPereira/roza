package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.List;
import java.util.Objects;

public final class TestMethod {

	private final String name;
	private final List<CodeAnnotation> annotations;
	private final CodeBlock body;

	public TestMethod(String name, List<CodeAnnotation> annotations, CodeBlock body) {
		this.name = Objects.requireNonNull(name);
		this.annotations = List.copyOf(Objects.requireNonNull(annotations));
		this.body = Objects.requireNonNull(body);
	}

	public String name() {
		return name;
	}

	public List<CodeAnnotation> annotations() {
		return annotations;
	}

	public CodeBlock body() {
		return body;
	}
}
