package br.ufsc.ine.leb.roza.measurer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimianNumberConfigurationTest {

	private SimianIntegerConfiguration configuration;

	@BeforeEach
	void setup() {
		configuration = new SimianIntegerConfiguration("threshold", 6, "Matches will contain at least the specified number of lines");
	}

	@Test
	void nameAndDescription() throws Exception {
		assertEquals("threshold", configuration.getName());
		assertEquals("Matches will contain at least the specified number of lines", configuration.getDescription());
	}

	@Test
	void create() throws Exception {
		assertEquals(6, configuration.getValue().intValue());
		assertEquals("-threshold=6", configuration.toArgument());
	}

	@Test
	void change() throws Exception {
		configuration.setValue(2);
		assertEquals(2, configuration.getValue().intValue());
		assertEquals("-threshold=2", configuration.toArgument());
	}

}
