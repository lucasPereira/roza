package br.ufsc.ine.leb.roza.measurement.configuration.simian;

import java.util.List;

import br.ufsc.ine.leb.roza.measurement.configuration.Configuration;
import br.ufsc.ine.leb.roza.measurement.configuration.IntegerConfiguration;

public class SimianIntegerConfiguration extends IntegerConfiguration implements Configuration {

	public SimianIntegerConfiguration(String name, Integer value, String description) {
		super(name, value, description);
	}

	@Override
	public void addArgument(List<String> arguments) {
		String argument = String.format("-%s=%s", getName(), getValue());
		arguments.add(argument);
	}

}
