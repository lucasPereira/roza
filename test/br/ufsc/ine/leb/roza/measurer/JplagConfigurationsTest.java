package br.ufsc.ine.leb.roza.measurer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JplagConfigurationsTest {

	private JplagConfigurations configurations;

	@BeforeEach
	void setup() {
		configurations = new JplagConfigurations();
	}

	@Test
	void configurations() throws Exception {
		assertEquals(12, configurations.getAll().size());

		assertEquals("t", configurations.getAll().get(0).getName());
		assertEquals("Tune the sensitivity of the comparison", configurations.getAll().get(0).getDescription());
		
		assertEquals("l", configurations.getAll().get(0).getName());
		assertEquals("Language", configurations.getAll().get(0).getDescription());

		assertEquals("m", configurations.getAll().get(1).getName());
		assertEquals("All matches with more than % similarity will be saved", configurations.getAll().get(1).getDescription());

		assertEquals("vq", configurations.getAll().get(2).getName());
		assertEquals("No output", configurations.getAll().get(2).getDescription());

		assertEquals("vl", configurations.getAll().get(3).getName());
		assertEquals("Detailed output", configurations.getAll().get(3).getDescription());

		assertEquals("vp", configurations.getAll().get(4).getName());
		assertEquals("Parser messages output", configurations.getAll().get(4).getDescription());

		assertEquals("vd", configurations.getAll().get(5).getName());
		assertEquals("Details about each submission output", configurations.getAll().get(5).getDescription());

		assertEquals("d", configurations.getAll().get(6).getName());
		assertEquals("Non-parsable files will be stored", configurations.getAll().get(6).getDescription());

		assertEquals("o", configurations.getAll().get(7).getName());
		assertEquals("Output file of the parser log", configurations.getAll().get(7).getDescription());

		assertEquals("r", configurations.getAll().get(8).getName());
		assertEquals("Name of directory in which the web pages will be stored", configurations.getAll().get(8).getDescription());

		assertEquals("bc", configurations.getAll().get(9).getName());
		assertEquals("Directory which contains the basecode", configurations.getAll().get(9).getDescription());

		assertEquals("s", configurations.getAll().get(10).getName());
		assertEquals("Look at files in subdirs too", configurations.getAll().get(10).getDescription());
	}

}
