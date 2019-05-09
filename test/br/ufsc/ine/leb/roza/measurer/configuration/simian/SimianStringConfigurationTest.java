package br.ufsc.ine.leb.roza.measurer.configuration.simian;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.measurer.configuration.simian.SimianStringConfiguration;

public class SimianStringConfigurationTest {

	private SimianStringConfiguration configuration;
	private List<String> arguments;

	@BeforeEach
	void setup() {
		configuration = new SimianStringConfiguration("language", "java", "Assumes all files are in the specified language");
		arguments = new LinkedList<>();
	}

	@Test
	void nameAndDescription() throws Exception {
		assertEquals("language", configuration.getName());
		assertEquals("Assumes all files are in the specified language", configuration.getDescription());
	}

	@Test
	void create() throws Exception {
		configuration.addArgument(arguments);
		assertEquals("java", configuration.getValue());
		assertEquals(1, arguments.size());
		assertEquals("-language=java", arguments.get(0));
	}

	@Test
	void change() throws Exception {
		configuration.setValue("ruby");
		configuration.addArgument(arguments);
		assertEquals("ruby", configuration.getValue());
		assertEquals(1, arguments.size());
		assertEquals("-language=ruby", arguments.get(0));
	}

}
