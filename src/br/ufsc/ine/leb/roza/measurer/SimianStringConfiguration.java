package br.ufsc.ine.leb.roza.measurer;

import java.util.List;

import br.ufsc.ine.leb.roza.support.configuration.Configuration;

import br.ufsc.ine.leb.roza.support.configuration.StringConfiguration;

public class SimianStringConfiguration extends StringConfiguration implements Configuration {

	public SimianStringConfiguration(String name, String value, String description) {
		super(name, value, description);
	}

	@Override
	public void addArgument(List<String> arguments) {
		String argument = String.format("-%s=%s", getName(), getValue());
		arguments.add(argument);
	}

}
