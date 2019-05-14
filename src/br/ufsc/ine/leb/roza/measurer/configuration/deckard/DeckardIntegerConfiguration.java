package br.ufsc.ine.leb.roza.measurer.configuration.deckard;

import java.util.List;

import br.ufsc.ine.leb.roza.measurer.configuration.Configuration;
import br.ufsc.ine.leb.roza.measurer.configuration.IntegerConfiguration;

public class DeckardIntegerConfiguration extends IntegerConfiguration implements Configuration {

	public DeckardIntegerConfiguration(String name, Integer value, String description) {
		super(name, value, description);
	}

	@Override
	public void addArgument(List<String> arguments) {
		arguments.add(String.format("export %s=%d", getName(), getValue()));
	}

}