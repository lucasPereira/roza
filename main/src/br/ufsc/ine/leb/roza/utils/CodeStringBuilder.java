package br.ufsc.ine.leb.roza.utils;

public class CodeStringBuilder {

	private StringBuilder code;
	private Integer indentations;

	public CodeStringBuilder() {
		code = new StringBuilder();
		indentations = 0;
	}

	public void add(String line) {
		code.append(getIndentation() + line + System.lineSeparator());
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
		StringBuilder indentation = new StringBuilder();
		for (Integer index = 0; index < indentations; index++) {
			indentation.append("\t");
		}
		return indentation.toString();
	}

	@Override
	public String toString() {
		return code.toString();
	}

}
