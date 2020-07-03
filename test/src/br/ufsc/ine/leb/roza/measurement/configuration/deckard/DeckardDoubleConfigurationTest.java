package br.ufsc.ine.leb.roza.measurement.configuration.deckard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeckardDoubleConfigurationTest {

	private DeckardDoubleConfiguration configuration;
	private List<String> arguments;

	@BeforeEach
	void setup() {
		configuration = new DeckardDoubleConfiguration("SIMILARITY", 1.0, "Similarity thresold based on editing distance");
		arguments = new LinkedList<>();
	}

	@Test
	void nameAndDescription() throws Exception {
		assertEquals("SIMILARITY", configuration.getName());
		assertEquals("Similarity thresold based on editing distance", configuration.getDescription());
	}

	@Test
	void create() throws Exception {
		configuration.addArgument(arguments);
		assertEquals(1, configuration.getValue().doubleValue());
		assertEquals(1, arguments.size());
		assertEquals("export SIMILARITY=1.0", arguments.get(0));
	}

	@Test
	void change() throws Exception {
		configuration.setValue(0.5);
		configuration.addArgument(arguments);
		assertEquals(0.5, configuration.getValue().doubleValue());
		assertEquals(1, arguments.size());
		assertEquals("export SIMILARITY=0.5", arguments.get(0));
	}

}
