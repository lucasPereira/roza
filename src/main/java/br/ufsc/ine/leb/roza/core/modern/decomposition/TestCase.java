package br.ufsc.ine.leb.roza.core.modern.decomposition;

import java.util.Objects;

import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;

public final class TestCase {

	private final String name;
	private final CodeBlock body;

	public TestCase(String name, CodeBlock body) {
		this.name = Objects.requireNonNull(name);
		this.body = Objects.requireNonNull(body);
	}

	public String name() {
		return name;
	}

	public CodeBlock body() {
		return body;
	}
}
