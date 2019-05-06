package br.ufsc.ine.leb.roza.measurer;

import java.util.List;

import br.ufsc.ine.leb.roza.support.configuration.Configuration;
import br.ufsc.ine.leb.roza.support.configuration.IntegerConfiguration;

public class JplagIntegerConfiguration extends IntegerConfiguration implements Configuration {

	public JplagIntegerConfiguration(String name, Integer value, String description) {
		super(name, value, description);
	}

	@Override
	public void addArgument(List<String> arguments) {
		arguments.add(String.format("-%s", getName()));
		arguments.add(String.format("%d", getValue()));
	}

}
