package br.ufsc.ine.leb.roza.support.configuration;

public class Configuration {

	private String name;
	private String value;
	private String description;

	public Configuration(String name, String value, String description) {
		this.name = name;
		this.value = value;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
