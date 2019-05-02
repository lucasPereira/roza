package br.ufsc.ine.leb.roza.support.configuration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.support.configuration.Configuration;

public class ConfigurationTest {

	@Test
	void create() throws Exception {
		Configuration configuration = new Configuration("-threshold", "6", "Matches will contain at least the specified number of lines");
		assertEquals("-threshold", configuration.getName());
		assertEquals("6", configuration.getValue());
		assertEquals("Matches will contain at least the specified number of lines", configuration.getDescription());
	}

	@Test
	void change() throws Exception {
		Configuration configuration = new Configuration("-threshold", "6", "Matches will contain at least the specified number of lines");
		configuration.setValue("2");
		assertEquals("-threshold", configuration.getName());
		assertEquals("2", configuration.getValue());
		assertEquals("Matches will contain at least the specified number of lines", configuration.getDescription());
	}

}
