package br.ufsc.ine.leb.roza.measurement.configuration.simian;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimianIntegerConfigurationTest {

	private SimianIntegerConfiguration configuration;
	private List<String> arguments;

	@BeforeEach
	void setup() {
		configuration = new SimianIntegerConfiguration("threshold", 6, "Matches will contain at least the specified number of lines");
		arguments = new LinkedList<>();
	}

	@Test
	void nameAndDescription() {
		assertEquals("threshold", configuration.getName());
		assertEquals("Matches will contain at least the specified number of lines", configuration.getDescription());
	}

	@Test
	void create() {
		configuration.addArgument(arguments);
		assertEquals(6, configuration.getValue().intValue());
		assertEquals(1, arguments.size());
		assertEquals("-threshold=6", arguments.get(0));
	}

	@Test
	void change() {
		configuration.setValue(2);
		configuration.addArgument(arguments);
		assertEquals(2, configuration.getValue().intValue());
		assertEquals(1, arguments.size());
		assertEquals("-threshold=2", arguments.get(0));
	}

}
