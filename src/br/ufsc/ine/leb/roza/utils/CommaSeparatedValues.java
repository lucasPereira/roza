package br.ufsc.ine.leb.roza.utils;

import java.util.Arrays;
import java.util.Iterator;

public class CommaSeparatedValues {

	private String columnSeparator;
	private String lineBreak;
	private StringBuilder text;

	public CommaSeparatedValues() {
		columnSeparator = ";";
		lineBreak = "\n";
		text = new StringBuilder();
	}

	public void addLine(String... columns) {
		Iterator<String> iterator = Arrays.asList(columns).iterator();
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
