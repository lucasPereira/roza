package br.ufsc.ine.leb.roza.utils;

import java.util.Iterator;
import java.util.List;

public class CommaSeparatedValues {

	private final String columnSeparator;
	private final String lineBreak;
	private final StringBuilder text;

	public CommaSeparatedValues() {
		columnSeparator = ";";
		lineBreak = "\n";
		text = new StringBuilder();
	}

	public void addLine(String... columns) {
		Iterator<String> iterator = List.of(columns).iterator();
		while (iterator.hasNext()) {
			String column = iterator.next();
			String delimiter = iterator.hasNext() ? columnSeparator : lineBreak;
			text.append(column);
			text.append(delimiter);
		}
	}

	public String getContent() {
		return text.toString();
	}

}
