package br.ufsc.ine.leb.roza.measurer;

import br.ufsc.ine.leb.roza.support.configuration.Configuration;
import br.ufsc.ine.leb.roza.support.configuration.IntegerConfiguration;

public class SimianIntegerConfiguration extends IntegerConfiguration implements Configuration {

	public SimianIntegerConfiguration(String name, Integer value, String description) {
		super(name, value, description);
	}

	@Override
	public String toArgument() {
		return String.format("-%s=%s", getName(), getValue());
	}

}
