package br.ufsc.ine.leb.roza.core.legacy.measurement.configuration;

import java.util.List;

public interface Configuration {

	String getName();

	String getDescription();

	void addArgument(List<String> arguments);

}
