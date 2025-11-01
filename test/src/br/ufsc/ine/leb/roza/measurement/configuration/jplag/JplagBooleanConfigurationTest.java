package br.ufsc.ine.leb.roza.measurement.configuration.jplag;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JplagBooleanConfigurationTest {

	private JplagBooleanConfiguration configurationTrue;
	private JplagBooleanConfiguration configurationFalse;
	private List<String> arguments;

	@BeforeEach
	void setup() {
		configurationTrue = new JplagBooleanConfiguration("d", true, "Non-parsable files will be stored");
		configurationFalse = new JplagBooleanConfiguration("d", false, "Non-parsable files will be stored");
		arguments = new LinkedList<>();
	}

	@Test
	void nameAndDescription() {
		assertEquals("d", configurationTrue.getName());
		assertEquals("Non-parsable files will be stored", configurationTrue.getDescription());
		assertEquals("d", configurationFalse.getName());
		assertEquals("Non-parsable files will be stored", configurationFalse.getDescription());
	}

	@Test
	void createTrue() {
		configurationTrue.addArgument(arguments);
		assertTrue(configurationTrue.getValue());
		assertEquals(1, arguments.size());
		assertEquals("-d", arguments.get(0));
	}

	@Test
	void createFalse() {
		configurationFalse.addArgument(arguments);
		assertFalse(configurationFalse.getValue());
		assertEquals(0, arguments.size());
	}

	@Test
	void changeTrueToFalse() {
		configurationTrue.unset();
		configurationTrue.addArgument(arguments);
		assertFalse(configurationTrue.getValue());
		assertEquals(0, arguments.size());
	}

	@Test
	void changeFalseToTrue() {
		configurationFalse.set();
		configurationFalse.addArgument(arguments);
		assertTrue(configurationFalse.getValue());
		assertEquals(1, arguments.size());
		assertEquals("-d", arguments.get(0));
	}

}
