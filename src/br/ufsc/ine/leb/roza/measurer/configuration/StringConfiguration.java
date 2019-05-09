package br.ufsc.ine.leb.roza.measurer.configuration;

public abstract class StringConfiguration extends AbstractConfiguration implements Configuration {

	private String value;

	public StringConfiguration(String name, String value, String description) {
		super(name, description);
		this.value = value;
	}

	public final String getValue() {
		return value;
	}

	public final void setValue(String value) {
		this.value = value;
	}

}
