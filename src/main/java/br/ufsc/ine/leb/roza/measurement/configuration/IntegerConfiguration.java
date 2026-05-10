package br.ufsc.ine.leb.roza.measurement.configuration;

public abstract class IntegerConfiguration extends AbstractConfiguration implements Configuration {

	private Integer value;

	public IntegerConfiguration(String name, Integer value, String description) {
		super(name, description);
		this.value = value;
	}

	public final Integer getValue() {
		return value;
	}

	public final void setValue(Integer value) {
		this.value = value;
	}

}
