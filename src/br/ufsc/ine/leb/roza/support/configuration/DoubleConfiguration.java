package br.ufsc.ine.leb.roza.support.configuration;

public abstract class DoubleConfiguration extends AbstractConfiguration implements Configuration {

	private Double value;

	public DoubleConfiguration(String name, Double value, String description) {
		super(name, description);
		this.value = value;
	}

	public final Double getValue() {
		return value;
	}

	public final void setValue(Double value) {
		this.value = value;
	}

}
