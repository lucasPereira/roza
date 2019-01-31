package br.ufsc.ine.leb.roza;

public class Field {

	private String type;
	private String name;

	public Field(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

}
