package br.ufsc.ine.leb.roza.measurer;

import java.util.List;

import br.ufsc.ine.leb.roza.support.configuration.BooleanConfiguration;
import br.ufsc.ine.leb.roza.support.configuration.Configuration;

public class SimianBooleanConfiguration extends BooleanConfiguration implements Configuration {

	public SimianBooleanConfiguration(String name, Boolean value, String description) {
		super(name, value, description);
	}

	@Override
	public void addArgument(List<String> arguments) {
		String sinal = getValue() ? "+" : "-";
		String argument = String.format("-%s%s", getName(), sinal);
		arguments.add(argument);
	}

}
