package br.ufsc.ine.leb.roza.measurer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeckardConfigurationsTest {

	private DeckardConfigurations configurations;

	@BeforeEach
	void setup() {
		configurations = new DeckardConfigurations();
	}

	@Test
	void configurations() throws Exception {
		assertEquals(16, configurations.getAll().size());

		assertEquals("MIN_TOKES", configurations.getAll().get(0).getName());
		assertEquals("Minimum token count to suppress vectors for small sub-trees", configurations.getAll().get(0).getDescription());

		assertEquals("STRIDE", configurations.getAll().get(1).getName());
		assertEquals("Width of the sliding window and how far it moves in each step", configurations.getAll().get(1).getDescription());

		assertEquals("SIMILARITY", configurations.getAll().get(2).getName());
		assertEquals("Similarity thresold based on editing distance", configurations.getAll().get(2).getDescription());

		assertEquals("SRC_DIR", configurations.getAll().get(3).getName());
		assertEquals("The root directory containing the source files", configurations.getAll().get(3).getDescription());

		assertEquals("FILE_PATTERN", configurations.getAll().get(4).getName());
		assertEquals("Input file name pattern", configurations.getAll().get(4).getDescription());

		assertEquals("DECKARD_DIR", configurations.getAll().get(5).getName());
		assertEquals("Where is the home directory of Deckard", configurations.getAll().get(5).getDescription());

		assertEquals("VECTOR_DIR", configurations.getAll().get(6).getName());
		assertEquals("Where to output detected clone clusters", configurations.getAll().get(6).getDescription());

		assertEquals("CLUSTER_DIR", configurations.getAll().get(7).getName());
		assertEquals("Where to output detected clone clusters", configurations.getAll().get(7).getDescription());

		assertEquals("TIME_DIR", configurations.getAll().get(8).getName());
		assertEquals("Where to output timing/debugging info", configurations.getAll().get(8).getDescription());

		assertEquals("VGEN_EXEC", configurations.getAll().get(9).getName());
		assertEquals("Where is the vector generator", configurations.getAll().get(9).getDescription());

		assertEquals("GROUPING_EXEC", configurations.getAll().get(10).getName());
		assertEquals("How to divide the vectors into groups", configurations.getAll().get(10).getDescription());

		assertEquals("CLUSTER_EXEC", configurations.getAll().get(11).getName());
		assertEquals("Where is the LSH for vector clustering", configurations.getAll().get(11).getDescription());

		assertEquals("QUERY_EXEC", configurations.getAll().get(12).getName());
		assertEquals("Where is the LSH for vector querying", configurations.getAll().get(12).getDescription());

		assertEquals("POSTPRO_EXEC", configurations.getAll().get(13).getName());
		assertEquals("How to post process clone groups", configurations.getAll().get(13).getDescription());

		assertEquals("GROUPING_S", configurations.getAll().get(14).getName());
		assertEquals("The maximal vector size for the first group", configurations.getAll().get(14).getDescription());

		assertEquals("MAX_PROC", configurations.getAll().get(15).getName());
		assertEquals("The maximal number of processes to be used", configurations.getAll().get(15).getDescription());
	}

	@Test
	void defaultValues() throws Exception {
		assertEquals(32, configurations.getAllAsArguments().size());
		fail();
	}

	@Test
	void changeValues() throws Exception {
		assertEquals(32, configurations.getAllAsArguments().size());
		fail();
	}

}
