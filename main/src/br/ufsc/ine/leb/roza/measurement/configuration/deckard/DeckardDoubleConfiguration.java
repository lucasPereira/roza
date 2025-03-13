package br.ufsc.ine.leb.roza.measurement.configuration.deckard;

import java.util.List;

import br.ufsc.ine.leb.roza.measurement.configuration.Configuration;
import br.ufsc.ine.leb.roza.measurement.configuration.DoubleConfiguration;
import br.ufsc.ine.leb.roza.utils.FormatterUtils;

public class DeckardDoubleConfiguration extends DoubleConfiguration implements Configuration {

	public DeckardDoubleConfiguration(String name, Double value, String description) {
		super(name, value, description);
	}

	@Override
	public void addArgument(List<String> arguments) {
		FormatterUtils formatterUtils = new FormatterUtils();
		String valueAsString = formatterUtils.fractionNumberForDeckardConfiguration(getValue());
		arguments.add(String.format("export %s=%s", getName(), valueAsString));
	}

}
