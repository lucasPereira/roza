package br.ufsc.ine.leb.roza.measurer.configuration.deckard;

import java.util.List;

import br.ufsc.ine.leb.roza.measurer.configuration.Configuration;
import br.ufsc.ine.leb.roza.measurer.configuration.StringConfiguration;

public class DeckardStringConfiguration extends StringConfiguration implements Configuration {

	public DeckardStringConfiguration(String name, String value, String description) {
		super(name, value, description);
	}

	@Override
	public void addArgument(List<String> arguments) {
		arguments.add(String.format("export %s=%s", getName(), getValue()));
	}

}
