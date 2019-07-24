package br.ufsc.ine.leb.roza.measurement.configuration.jplag;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.measurement.configuration.jplag.JplagStringConfiguration;

public class JplagStringConfigurationTest {

	private JplagStringConfiguration configuration;
	private List<String> arguments;

	@BeforeEach
	void setup() {
		configuration = new JplagStringConfiguration("l", "java17", "Language");
		arguments = new LinkedList<>();
	}

	@Test
	void nameAndDescription() throws Exception {
		assertEquals("l", configuration.getName());
		assertEquals("Language", configuration.getDescription());
	}

	@Test
	void create() throws Exception {
		configuration.addArgument(arguments);
		assertEquals("java17", configuration.getValue());
		assertEquals(2, arguments.size());
		assertEquals("-l", arguments.get(0));
		assertEquals("java17", arguments.get(1));
	}

	@Test
	void change() throws Exception {
		configuration.setValue("python3");
		configuration.addArgument(arguments);
		assertEquals("python3", configuration.getValue());
		assertEquals(2, arguments.size());
		assertEquals("-l", arguments.get(0));
		assertEquals("python3", arguments.get(1));
	}

}
