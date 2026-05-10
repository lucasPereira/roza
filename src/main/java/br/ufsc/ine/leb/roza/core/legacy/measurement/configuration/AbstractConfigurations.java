package br.ufsc.ine.leb.roza.core.legacy.measurement.configuration;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.core.legacy.exceptions.InvalidConfigurationException;
import br.ufsc.ine.leb.roza.core.legacy.exceptions.MissingConfigurationException;

public abstract class AbstractConfigurations implements Configurations {

	@Override
	public final List<String> getAllAsArguments() {
		if (!hasAllConfigurations()) {
			throw new MissingConfigurationException();
		}
		List<String> arguments = new ArrayList<>(getAll().size());
		for (Configuration configuration : getAll()) {
			configuration.addArgument(arguments);
		}
		return arguments;
	}

	protected final void ensureThat(Boolean condition) {
		if (!condition) {
			throw new InvalidConfigurationException();
		}
	}

	protected abstract Boolean hasAllConfigurations();

}
