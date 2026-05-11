package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.Objects;

public final class CodeAnnotation {

	private final String name;
	private final String text;

	public CodeAnnotation(String name, String text) {
		this.name = Objects.requireNonNull(name);
		this.text = Objects.requireNonNull(text);
	}

	public String name() {
		return name;
	}

	public String text() {
		return text;
	}
}
