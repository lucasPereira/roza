package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.List;
import java.util.Objects;

public final class CodeBlock {

	private final List<CodeStatement> statements;

	public CodeBlock(List<CodeStatement> statements) {
		this.statements = List.copyOf(Objects.requireNonNull(statements));
	}

	public List<CodeStatement> statements() {
		return statements;
	}
}
