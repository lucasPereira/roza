package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.Objects;

public final class UnsupportedFeatureDiagnostic {

	private final String message;

	public UnsupportedFeatureDiagnostic(String message) {
		this.message = Objects.requireNonNull(message);
	}

	public String message() {
		return message;
	}
}
