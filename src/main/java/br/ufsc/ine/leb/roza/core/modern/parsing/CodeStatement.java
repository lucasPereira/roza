package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.Objects;

public final class CodeStatement {

	private final String originalText;
	private final String normalizedText;

	public CodeStatement(String originalText, String normalizedText) {
		this.originalText = Objects.requireNonNull(originalText);
		this.normalizedText = Objects.requireNonNull(normalizedText);
	}

	public String originalText() {
		return originalText;
	}

	public String normalizedText() {
		return normalizedText;
	}
}
