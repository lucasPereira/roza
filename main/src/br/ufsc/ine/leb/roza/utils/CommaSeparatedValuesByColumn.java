package br.ufsc.ine.leb.roza.utils;

import java.util.ArrayList;
import java.util.List;

public class CommaSeparatedValuesByColumn {

	private List<List<String>> lines;
	private Integer currentLine;

	public CommaSeparatedValuesByColumn(String... firstColumn) {
		lines = new ArrayList<>(firstColumn.length);
		currentLine = 0;
		for (String cell : firstColumn) {
			List<String> line = new ArrayList<>();
			line.add(cell);
			lines.add(line);
		}
	}

	public void addCell(String value) {
		lines.get(currentLine).add(value);
		currentLine++;
	}

	public void newColumn(String header) {
		currentLine = 0;
		addCell(header);
	}

	public String getContent() {
		String columnSeparator = ";";
		String lineBreak = "\n";
		StringBuilder builder = new StringBuilder();
		for (List<String> line : lines) {
			for (String cell : line) {
				builder.append(cell);
			}
			builder.append(columnSeparator);
		}
		builder.append(lineBreak);
		return builder.toString();
	}

}
