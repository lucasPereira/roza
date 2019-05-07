package br.ufsc.ine.leb.roza.measurer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeckardStringConfigurationTest {

	private DeckardStringConfiguration configuration;
	private List<String> arguments;

	@BeforeEach
	void setup() {
		configuration = new DeckardStringConfiguration("FILE_PATTERN", "*.java", "Input file name pattern");
		arguments = new LinkedList<>();
	}

	@Test
	void nameAndDescription() throws Exception {
		assertEquals("FILE_PATTERN", configuration.getName());
		assertEquals("Input file name pattern", configuration.getDescription());
	}

	@Test
	void create() throws Exception {
		configuration.addArgument(arguments);
		assertEquals("*.java", configuration.getValue());
		assertEquals(1, arguments.size());
		assertEquals("export FILE_PATTERN=*.java", arguments.get(0));
	}

	@Test
	void change() throws Exception {
		configuration.setValue("*.c");
		configuration.addArgument(arguments);
		assertEquals("*.c", configuration.getValue());
		assertEquals(1, arguments.size());
		assertEquals("export FILE_PATTERN=*.c", arguments.get(0));
	}

}
