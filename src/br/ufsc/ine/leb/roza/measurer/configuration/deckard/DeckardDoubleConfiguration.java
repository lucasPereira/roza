package br.ufsc.ine.leb.roza.measurer.configuration.deckard;

import java.util.List;
import java.util.Locale;

import br.ufsc.ine.leb.roza.measurer.configuration.Configuration;
import br.ufsc.ine.leb.roza.measurer.configuration.DoubleConfiguration;

public class DeckardDoubleConfiguration extends DoubleConfiguration implements Configuration {

	public DeckardDoubleConfiguration(String name, Double value, String description) {
		super(name, value, description);
	}

	@Override
	public void addArgument(List<String> arguments) {
		arguments.add(String.format(Locale.ENGLISH, "export %s=%.1f", getName(), getValue()));
	}

}
