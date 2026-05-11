package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.Objects;

public final class CodeStatement {

	private final String originalText;
	private final String normalizedText;
	private final boolean assertion;

	public CodeStatement(String originalText, String normalizedText) {
		this(originalText, normalizedText, false);
	}

	public CodeStatement(String originalText, String normalizedText, boolean assertion) {
		this.originalText = Objects.requireNonNull(originalText);
		this.normalizedText = Objects.requireNonNull(normalizedText);
		this.assertion = assertion;
	}

	public String originalText() {
		return originalText;
	}

	public String normalizedText() {
		return normalizedText;
	}

	public boolean isAssertion() {
		return assertion;
	}
}
