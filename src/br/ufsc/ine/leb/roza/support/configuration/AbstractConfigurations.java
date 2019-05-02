package br.ufsc.ine.leb.roza.support.configuration;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.exceptions.InvalidConfigurationException;

public abstract class AbstractConfigurations implements Configurations {

	@Override
	public final List<String> getAllAsArguments() {
		List<String> arguments = new ArrayList<>(getAll().size());
		for (Configuration configuration : getAll()) {
			String argument = configuration.toArgument();
			arguments.add(argument);
		}
		return arguments;
	}

	protected final void ensureThat(Boolean condition) {
		if (!condition) {
			throw new InvalidConfigurationException();
		}
	}

}
