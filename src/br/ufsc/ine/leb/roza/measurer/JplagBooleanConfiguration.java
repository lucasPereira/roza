package br.ufsc.ine.leb.roza.measurer;

import java.util.List;

import br.ufsc.ine.leb.roza.support.configuration.BooleanConfiguration;
import br.ufsc.ine.leb.roza.support.configuration.Configuration;

public class JplagBooleanConfiguration extends BooleanConfiguration implements Configuration {

	public JplagBooleanConfiguration(String name, Boolean value, String description) {
		super(name, value, description);
	}

	@Override
	public void addArgument(List<String> arguments) {
		if (getValue()) {
			arguments.add(String.format("-%s", getName()));
		}
	}

}
