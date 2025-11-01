package br.ufsc.ine.leb.roza.utils;

public class CodeStringBuilder {

	private final StringBuilder code;
	private Integer indentations;

	public CodeStringBuilder() {
		code = new StringBuilder();
		indentations = 0;
	}

	public void add(String line) {
		code.append(getIndentation()).append(line).append(System.lineSeparator());
		indentations = 0;
	}

	public void add() {
		add("");
	}

	public CodeStringBuilder tab() {
		indentations++;
		return this;
	}

	private String getIndentation() {
		return "\t".repeat(Math.max(0, indentations));
	}

	@Override
	public String toString() {
		return code.toString();
	}

}
