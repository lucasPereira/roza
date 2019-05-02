package br.ufsc.ine.leb.roza.measurer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimianBooleanConfigurationTest {

	private SimianBooleanConfiguration configurationTrue;
	private SimianBooleanConfiguration configurationFalse;

	@BeforeEach
	void setup() {
		configurationTrue = new SimianBooleanConfiguration("ignoreCurlyBraces", true, "Curly braces are ignored");
		configurationFalse = new SimianBooleanConfiguration("ignoreCurlyBraces", false, "Curly braces are ignored");
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
		assertTrue(configurationTrue.getValue());
		assertEquals("-ignoreCurlyBraces+", configurationTrue.toArgument());
	}

	@Test
	void createFalse() throws Exception {
		assertFalse(configurationFalse.getValue());
		assertEquals("-ignoreCurlyBraces-", configurationFalse.toArgument());
	}

	@Test
	void changeTrueToFalse() throws Exception {
		configurationTrue.unset();
		assertFalse(configurationTrue.getValue());
		assertEquals("-ignoreCurlyBraces-", configurationTrue.toArgument());
	}

	@Test
	void changeFalseToTrue() throws Exception {
		configurationFalse.set();
		assertTrue(configurationFalse.getValue());
		assertEquals("-ignoreCurlyBraces+", configurationFalse.toArgument());
	}

}
