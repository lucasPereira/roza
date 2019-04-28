package br.ufsc.ine.leb.roza;

public class Field {

	private String type;
	private String name;
	private String text;

	public Field(String type, String name, String text) {
		this.type = type;
		this.name = name;
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getText() {
		return text;
	}
}
