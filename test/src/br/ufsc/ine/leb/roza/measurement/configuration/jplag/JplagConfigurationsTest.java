package br.ufsc.ine.leb.roza.measurement.configuration.jplag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.exceptions.InvalidConfigurationException;

class JplagConfigurationsTest {

	private JplagConfigurations configurations;

	@BeforeEach
	void setup() {
		configurations = new JplagConfigurations();
	}

	@Test
	void configurations() throws Exception {
		assertEquals(11, configurations.getAll().size());

		assertEquals("t", configurations.getAll().get(0).getName());
		assertEquals("Tune the sensitivity of the comparison", configurations.getAll().get(0).getDescription());

		assertEquals("l", configurations.getAll().get(1).getName());
		assertEquals("Language", configurations.getAll().get(1).getDescription());

		assertEquals("m", configurations.getAll().get(2).getName());
		assertEquals("All matches with more than % similarity will be saved", configurations.getAll().get(2).getDescription());

		assertEquals("vq", configurations.getAll().get(3).getName());
		assertEquals("No output", configurations.getAll().get(3).getDescription());

		assertEquals("vl", configurations.getAll().get(4).getName());
		assertEquals("Detailed output", configurations.getAll().get(4).getDescription());

		assertEquals("vp", configurations.getAll().get(5).getName());
		assertEquals("Parser messages output", configurations.getAll().get(5).getDescription());

		assertEquals("vd", configurations.getAll().get(6).getName());
		assertEquals("Details about each submission output", configurations.getAll().get(6).getDescription());

		assertEquals("d", configurations.getAll().get(7).getName());
		assertEquals("Non-parsable files will be stored", configurations.getAll().get(7).getDescription());

		assertEquals("o", configurations.getAll().get(8).getName());
		assertEquals("Output file of the parser log", configurations.getAll().get(8).getDescription());

		assertEquals("r", configurations.getAll().get(9).getName());
		assertEquals("Name of directory in which the web pages will be stored", configurations.getAll().get(9).getDescription());

		assertEquals("s", configurations.getAll().get(10).getName());
		assertEquals("Look at files in subdirs too", configurations.getAll().get(10).getDescription());
	}

	@Test
	void defaultValues() throws Exception {
		assertEquals(13, configurations.getAllAsArguments().size());

		assertEquals("-t", configurations.getAllAsArguments().get(0));
		assertEquals("1", configurations.getAllAsArguments().get(1));

		assertEquals("-l", configurations.getAllAsArguments().get(2));
		assertEquals("java17", configurations.getAllAsArguments().get(3));

		assertEquals("-m", configurations.getAllAsArguments().get(4));
		assertEquals("0%", configurations.getAllAsArguments().get(5));

		assertEquals("-vl", configurations.getAllAsArguments().get(6));

		assertEquals("-o", configurations.getAllAsArguments().get(7));
		assertEquals("main/exec/measurer/log.txt", configurations.getAllAsArguments().get(8));

		assertEquals("-r", configurations.getAllAsArguments().get(9));
		assertEquals("main/exec/measurer", configurations.getAllAsArguments().get(10));

		assertEquals("-s", configurations.getAllAsArguments().get(11));
		assertEquals("main/exec/materializer", configurations.getAllAsArguments().get(12));
	}

	@Test
	void changeValues() throws Exception {
		configurations.sensitivity(2);

		assertEquals(13, configurations.getAllAsArguments().size());

		assertEquals("-t", configurations.getAllAsArguments().get(0));
		assertEquals("2", configurations.getAllAsArguments().get(1));

		assertEquals("-l", configurations.getAllAsArguments().get(2));
		assertEquals("java17", configurations.getAllAsArguments().get(3));

		assertEquals("-m", configurations.getAllAsArguments().get(4));
		assertEquals("0%", configurations.getAllAsArguments().get(5));

		assertEquals("-vl", configurations.getAllAsArguments().get(6));

		assertEquals("-o", configurations.getAllAsArguments().get(7));
		assertEquals("main/exec/measurer/log.txt", configurations.getAllAsArguments().get(8));

		assertEquals("-r", configurations.getAllAsArguments().get(9));
		assertEquals("main/exec/measurer", configurations.getAllAsArguments().get(10));

		assertEquals("-s", configurations.getAllAsArguments().get(11));
		assertEquals("main/exec/materializer", configurations.getAllAsArguments().get(12));
	}

	@Test
	void sensitivityShouldBeLargerThanZero() throws Exception {
		assertThrows(InvalidConfigurationException.class, () -> {
			configurations.sensitivity(-1);
		});
		assertThrows(InvalidConfigurationException.class, () -> {
			configurations.sensitivity(0);
		});
		assertThrows(InvalidConfigurationException.class, () -> {
			configurations.sensitivity(null);
		});
	}

	@Test
	void getConfigurations() throws Exception {
		assertEquals("main/exec/measurer", configurations.results());
	}

}
