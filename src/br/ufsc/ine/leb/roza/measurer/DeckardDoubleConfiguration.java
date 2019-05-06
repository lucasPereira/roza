package br.ufsc.ine.leb.roza.measurer;

import java.util.List;
import java.util.Locale;

import br.ufsc.ine.leb.roza.support.configuration.Configuration;
import br.ufsc.ine.leb.roza.support.configuration.DoubleConfiguration;

public class DeckardDoubleConfiguration extends DoubleConfiguration implements Configuration {

	public DeckardDoubleConfiguration(String name, Double value, String description) {
		super(name, value, description);
	}

	@Override
	public void addArgument(List<String> arguments) {
		arguments.add(String.format(Locale.ENGLISH, "%s=%.1f", getName(), getValue()));
		arguments.add(String.format("export %s", getName()));
	}

}
