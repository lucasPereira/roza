package br.ufsc.ine.leb.roza.measurer.configuration.simian;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.measurer.configuration.simian.SimianBooleanConfiguration;

public class SimianBooleanConfigurationTest {

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
	void nameAndDescription() throws Exception {
		assertEquals("ignoreCurlyBraces", configurationTrue.getName());
		assertEquals("Curly braces are ignored", configurationTrue.getDescription());
		assertEquals("ignoreCurlyBraces", configurationFalse.getName());
		assertEquals("Curly braces are ignored", configurationFalse.getDescription());
	}

	@Test
	void createTrue() throws Exception {
		configurationTrue.addArgument(arguments);
		assertTrue(configurationTrue.getValue());
		assertEquals(1, arguments.size());
		assertEquals("-ignoreCurlyBraces+", arguments.get(0));
	}

	@Test
	void createFalse() throws Exception {
		configurationFalse.addArgument(arguments);
		assertFalse(configurationFalse.getValue());
		assertEquals(1, arguments.size());
		assertEquals("-ignoreCurlyBraces-", arguments.get(0));
	}

	@Test
	void changeTrueToFalse() throws Exception {
		configurationTrue.unset();
		configurationTrue.addArgument(arguments);
		assertFalse(configurationTrue.getValue());
		assertEquals(1, arguments.size());
		assertEquals("-ignoreCurlyBraces-", arguments.get(0));
	}

	@Test
	void changeFalseToTrue() throws Exception {
		configurationFalse.set();
		configurationFalse.addArgument(arguments);
		assertTrue(configurationFalse.getValue());
		assertEquals(1, arguments.size());
		assertEquals("-ignoreCurlyBraces+", arguments.get(0));
	}

}
