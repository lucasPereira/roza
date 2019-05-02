package br.ufsc.ine.leb.roza.support.configuration;

public abstract class BooleanConfiguration extends AbstractConfiguration implements Configuration {

	private Boolean value;

	public BooleanConfiguration(String name, Boolean value, String description) {
		super(name, description);
		this.value = value;
	}

	public final Boolean getValue() {
		return value;
	}

	public final void set() {
		this.value = true;
	}

	public final void unset() {
		this.value = false;
	}

}
