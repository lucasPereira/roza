package br.ufsc.ine.leb.roza.measurer;

import java.util.List;

import br.ufsc.ine.leb.roza.support.configuration.Configuration;
import br.ufsc.ine.leb.roza.support.configuration.StringConfiguration;

public class JplagStringConfiguration extends StringConfiguration implements Configuration {

	public JplagStringConfiguration(String name, String value, String description) {
		super(name, value, description);
	}

	@Override
	public void addArgument(List<String> arguments) {
		arguments.add(String.format("-%s", getName()));
		arguments.add(String.format("%s", getValue()));
	}

}
