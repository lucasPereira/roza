package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Field {

	private final List<String> modifiers;
	private final String type;
	private final String name;
	private final Optional<CodeStatement> initialization;

	public Field(List<String> modifiers, String type, String name, Optional<CodeStatement> initialization) {
		this.modifiers = List.copyOf(Objects.requireNonNull(modifiers));
		this.type = Objects.requireNonNull(type);
		this.name = Objects.requireNonNull(name);
		this.initialization = Objects.requireNonNull(initialization);
	}

	public List<String> modifiers() {
		return modifiers;
	}

	public String type() {
		return type;
	}

	public String name() {
		return name;
	}

	public Optional<CodeStatement> initialization() {
		return initialization;
	}
}
