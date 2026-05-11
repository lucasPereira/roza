package br.ufsc.ine.leb.roza.core.modern.loading;

import java.util.Objects;

public final class CodeFile {

	private final String content;

	public CodeFile(String content) {
		this.content = Objects.requireNonNull(content);
	}

	public String content() {
		return content;
	}
}
