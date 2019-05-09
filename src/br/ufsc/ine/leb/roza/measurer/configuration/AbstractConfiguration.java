package br.ufsc.ine.leb.roza.measurer.configuration;

public abstract class AbstractConfiguration implements Configuration {

	private String name;
	private String description;

	public AbstractConfiguration(String name, String description) {
		this.name = name;
		this.description = description;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final String getDescription() {
		return description;
	}

}
