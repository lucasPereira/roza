package br.ufsc.ine.leb.roza.support.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.ufsc.ine.leb.roza.exceptions.InvalidConfigurationException;

public abstract class AbstractConfigurations implements Configurations {

	private Map<String, Configuration> map;
	private List<Configuration> list;

	public AbstractConfigurations() {
		map = new HashMap<>();
		list = new LinkedList<>();
	}

	@Override
	public final List<Configuration> getAll() {
		return list;
	}

	@Override
	public final List<String> getAllAsArguments() {
		List<String> arguments = new ArrayList<>(getAll().size());
		for (Configuration configuration : getAll()) {
			String argument = generateArgument(configuration.getName(), configuration.getValue());
			arguments.add(argument);
		}
		return arguments;
	}

	protected final void set(String name, Object value) {
		map.get(name).setValue(value.toString());
	}

	protected final void create(String name, Object value, String description) {
		Configuration configuration = new Configuration(name, value.toString(), description);
		list.add(configuration);
		map.put(name, configuration);
	}

	protected final void throwInvalidConfigurationExceptionIfFalse(Boolean correct) {
		if (!correct) {
			throw new InvalidConfigurationException();
		}
	}

	protected abstract String generateArgument(String name, String value);

}
