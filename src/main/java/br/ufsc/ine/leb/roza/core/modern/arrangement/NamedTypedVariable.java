package br.ufsc.ine.leb.roza.core.modern.arrangement;

import java.util.Objects;

public final class NamedTypedVariable {

	private final String name;
	private final String type;

	public NamedTypedVariable(String name, String type) {
		this.name = Objects.requireNonNull(name);
		this.type = Objects.requireNonNull(type);
	}

	public String name() {
		return name;
	}

	public String type() {
		return type;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof NamedTypedVariable)) {
			return false;
		}
		NamedTypedVariable that = (NamedTypedVariable) other;
		return name.equals(that.name) && type.equals(that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, type);
	}
}
