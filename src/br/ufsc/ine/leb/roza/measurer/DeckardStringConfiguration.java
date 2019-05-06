package br.ufsc.ine.leb.roza.measurer;

import java.util.List;

import br.ufsc.ine.leb.roza.support.configuration.Configuration;
import br.ufsc.ine.leb.roza.support.configuration.StringConfiguration;

public class DeckardStringConfiguration extends StringConfiguration implements Configuration {

	public DeckardStringConfiguration(String name, String value, String description) {
		super(name, value, description);
	}

	@Override
	public void addArgument(List<String> arguments) {
		arguments.add(String.format("%s=%s", getName(), getValue()));
		arguments.add(String.format("export %s", getName()));
	}

}
