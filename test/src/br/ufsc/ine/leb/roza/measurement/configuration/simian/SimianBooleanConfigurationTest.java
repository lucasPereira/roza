package br.ufsc.ine.leb.roza.measurement.configuration.simian;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimianBooleanConfigurationTest {

	private SimianBooleanConfiguration configurationTrue;
	private SimianBooleanConfiguration configurationFalse;
	private List<String> arguments;

	@BeforeEach
	void setup() {
		configurationTrue = new SimianBooleanConfiguration("ignoreCurlyBraces", true, "Curly braces are ignored");
		configurationFalse = new SimianBooleanConfiguration("ignoreCurlyBraces", false, "Curly braces are ignored");
		arguments = new LinkedList<>();
	}

	@Test
	void nameAndDescription() {
		assertEquals("ignoreCurlyBraces", configurationTrue.getName());
		assertEquals("Curly braces are ignored", configurationTrue.getDescription());
		assertEquals("ignoreCurlyBraces", configurationFalse.getName());
		assertEquals("Curly braces are ignored", configurationFalse.getDescription());
	}

	@Test
	void createTrue() {
		configurationTrue.addArgument(arguments);
		assertTrue(configurationTrue.getValue());
		assertEquals(1, arguments.size());
		assertEquals("-ignoreCurlyBraces+", arguments.get(0));
	}

	@Test
	void createFalse() {
		configurationFalse.addArgument(arguments);
		assertFalse(configurationFalse.getValue());
		assertEquals(1, arguments.size());
		assertEquals("-ignoreCurlyBraces-", arguments.get(0));
	}

	@Test
	void changeTrueToFalse() {
		configurationTrue.unset();
		configurationTrue.addArgument(arguments);
		assertFalse(configurationTrue.getValue());
		assertEquals(1, arguments.size());
		assertEquals("-ignoreCurlyBraces-", arguments.get(0));
	}

	@Test
	void changeFalseToTrue() {
		configurationFalse.set();
		configurationFalse.addArgument(arguments);
		assertTrue(configurationFalse.getValue());
		assertEquals(1, arguments.size());
		assertEquals("-ignoreCurlyBraces+", arguments.get(0));
	}

}
