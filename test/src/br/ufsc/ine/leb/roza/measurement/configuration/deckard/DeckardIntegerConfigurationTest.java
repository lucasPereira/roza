package br.ufsc.ine.leb.roza.measurement.configuration.deckard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeckardIntegerConfigurationTest {

	private DeckardIntegerConfiguration configuration;
	private List<String> arguments;

	@BeforeEach
	void setup() {
		configuration = new DeckardIntegerConfiguration("MIN_TOKENS", 1, "Minimum token count to suppress vectors for small sub-trees");
		arguments = new LinkedList<>();
	}

	@Test
	void nameAndDescription() throws Exception {
		assertEquals("MIN_TOKENS", configuration.getName());
		assertEquals("Minimum token count to suppress vectors for small sub-trees", configuration.getDescription());
	}

	@Test
	void create() throws Exception {
		configuration.addArgument(arguments);
		assertEquals(1, configuration.getValue().intValue());
		assertEquals(1, arguments.size());
		assertEquals("export MIN_TOKENS=1", arguments.get(0));
	}

	@Test
	void change() throws Exception {
		configuration.setValue(Integer.MAX_VALUE);
		configuration.addArgument(arguments);
		assertEquals(Integer.MAX_VALUE, configuration.getValue().intValue());
		assertEquals(1, arguments.size());
		assertEquals("export MIN_TOKENS=inf", arguments.get(0));
	}

}
