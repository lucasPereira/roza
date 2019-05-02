package br.ufsc.ine.leb.roza.measurer;

import br.ufsc.ine.leb.roza.support.configuration.BooleanConfiguration;
import br.ufsc.ine.leb.roza.support.configuration.Configuration;

public class SimianBooleanConfiguration extends BooleanConfiguration implements Configuration {

	public SimianBooleanConfiguration(String name, Boolean value, String description) {
		super(name, value, description);
	}

	@Override
	public String toArgument() {
		String sinal = getValue() ? "+" : "-";
		return String.format("-%s%s", getName(), sinal);
	}

}
