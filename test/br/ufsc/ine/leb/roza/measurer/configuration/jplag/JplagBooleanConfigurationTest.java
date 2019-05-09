package br.ufsc.ine.leb.roza.measurer.configuration.jplag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.measurer.configuration.jplag.JplagBooleanConfiguration;

public class JplagBooleanConfigurationTest {

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
	void nameAndDescription() throws Exception {
		assertEquals("d", configurationTrue.getName());
		assertEquals("Non-parsable files will be stored", configurationTrue.getDescription());
		assertEquals("d", configurationFalse.getName());
		assertEquals("Non-parsable files will be stored", configurationFalse.getDescription());
	}

	@Test
	void createTrue() throws Exception {
		configurationTrue.addArgument(arguments);
		assertTrue(configurationTrue.getValue());
		assertEquals(1, arguments.size());
		assertEquals("-d", arguments.get(0));
	}

	@Test
	void createFalse() throws Exception {
		configurationFalse.addArgument(arguments);
		assertFalse(configurationFalse.getValue());
		assertEquals(0, arguments.size());
	}

	@Test
	void changeTrueToFalse() throws Exception {
		configurationTrue.unset();
		configurationTrue.addArgument(arguments);
		assertFalse(configurationTrue.getValue());
		assertEquals(0, arguments.size());
	}

	@Test
	void changeFalseToTrue() throws Exception {
		configurationFalse.set();
		configurationFalse.addArgument(arguments);
		assertTrue(configurationFalse.getValue());
		assertEquals(1, arguments.size());
		assertEquals("-d", arguments.get(0));
	}

}
