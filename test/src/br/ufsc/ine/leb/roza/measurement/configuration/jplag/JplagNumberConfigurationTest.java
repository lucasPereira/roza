package br.ufsc.ine.leb.roza.measurement.configuration.jplag;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JplagNumberConfigurationTest {

	private JplagIntegerConfiguration configuration;
	private List<String> arguments;

	@BeforeEach
	void setup() {
		configuration = new JplagIntegerConfiguration("t", 1, "Tune the sensitivity of the comparison");
		arguments = new LinkedList<>();
	}

	@Test
	void nameAndDescription() {
		assertEquals("t", configuration.getName());
		assertEquals("Tune the sensitivity of the comparison", configuration.getDescription());
	}

	@Test
	void create() {
		configuration.addArgument(arguments);
		assertEquals(1, configuration.getValue().intValue());
		assertEquals(2, arguments.size());
		assertEquals("-t", arguments.get(0));
		assertEquals("1", arguments.get(1));
	}

	@Test
	void change() {
		configuration.setValue(2);
		configuration.addArgument(arguments);
		assertEquals(2, configuration.getValue().intValue());
		assertEquals(2, arguments.size());
		assertEquals("-t", arguments.get(0));
		assertEquals("2", arguments.get(1));
	}

}
