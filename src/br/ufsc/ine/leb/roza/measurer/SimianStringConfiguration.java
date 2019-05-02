package br.ufsc.ine.leb.roza.measurer;

import br.ufsc.ine.leb.roza.support.configuration.Configuration;

import br.ufsc.ine.leb.roza.support.configuration.StringConfiguration;

public class SimianStringConfiguration extends StringConfiguration implements Configuration {

	public SimianStringConfiguration(String name, String value, String description) {
		super(name, value, description);
	}

	@Override
	public String toArgument() {
		return String.format("-%s=%s", getName(), getValue());
	}

}
