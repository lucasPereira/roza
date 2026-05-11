package br.ufsc.ine.leb.roza.core.modern.loading;

import java.util.Objects;

public final class CodeFile {

	private final String source;
	private final String content;

	public CodeFile(String content) {
		this("", content);
	}

	public CodeFile(String source, String content) {
		this.source = Objects.requireNonNull(source);
		this.content = Objects.requireNonNull(content);
	}

	public String source() {
		return source;
	}

	public String content() {
		return content;
	}
}
