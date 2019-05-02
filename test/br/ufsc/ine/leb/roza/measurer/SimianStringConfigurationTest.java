package br.ufsc.ine.leb.roza.measurer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.measurer.SimianStringConfiguration;

public class SimianStringConfigurationTest {

	private SimianStringConfiguration configuration;

	@BeforeEach
	void setup() {
		configuration = new SimianStringConfiguration("language", "java", "Assumes all files are in the specified language");
	}

	@Test
	void nameAndDescription() throws Exception {
		assertEquals("language", configuration.getName());
		assertEquals("Assumes all files are in the specified language", configuration.getDescription());
	}

	@Test
	void create() throws Exception {
		assertEquals("java", configuration.getValue());
		assertEquals("-language=java", configuration.toArgument());
	}

	@Test
	void change() throws Exception {
		configuration.setValue("ruby");
		assertEquals("ruby", configuration.getValue());
		assertEquals("-language=ruby", configuration.toArgument());
	}

}
