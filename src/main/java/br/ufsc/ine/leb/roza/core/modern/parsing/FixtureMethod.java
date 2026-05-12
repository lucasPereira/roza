package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.List;
import java.util.Objects;

public final class FixtureMethod {

	private final FixtureKind kind;
	private final String name;
	private final List<CodeAnnotation> annotations;
	private final List<String> thrownExceptions;
	private final CodeBlock body;

	public FixtureMethod(FixtureKind kind, String name, List<CodeAnnotation> annotations, CodeBlock body) {
		this(kind, name, annotations, List.of(), body);
	}

	public FixtureMethod(FixtureKind kind, String name, List<CodeAnnotation> annotations, List<String> thrownExceptions, CodeBlock body) {
		this.kind = Objects.requireNonNull(kind);
		this.name = Objects.requireNonNull(name);
		this.annotations = List.copyOf(Objects.requireNonNull(annotations));
		this.thrownExceptions = List.copyOf(Objects.requireNonNull(thrownExceptions));
		this.body = Objects.requireNonNull(body);
	}

	public FixtureKind kind() {
		return kind;
	}

	public String name() {
		return name;
	}

	public List<CodeAnnotation> annotations() {
		return annotations;
	}

	public List<String> thrownExceptions() {
		return thrownExceptions;
	}

	public CodeBlock body() {
		return body;
	}
}
