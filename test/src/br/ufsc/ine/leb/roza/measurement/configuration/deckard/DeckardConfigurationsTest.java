package br.ufsc.ine.leb.roza.measurement.configuration.deckard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.exceptions.InvalidConfigurationException;

public class DeckardConfigurationsTest {

	private DeckardConfigurations configurations;

	@BeforeEach
	void setup() {
		configurations = new DeckardConfigurations();
	}

	@Test
	void configurations() throws Exception {
		assertEquals(16, configurations.getAll().size());

		assertEquals("MIN_TOKENS", configurations.getAll().get(0).getName());
		assertEquals("Minimum token count to suppress vectors for small sub-trees", configurations.getAll().get(0).getDescription());

		assertEquals("STRIDE", configurations.getAll().get(1).getName());
		assertEquals("Width of the sliding window and how far it moves in each step", configurations.getAll().get(1).getDescription());

		assertEquals("SIMILARITY", configurations.getAll().get(2).getName());
		assertEquals("Similarity thresold based on editing distance", configurations.getAll().get(2).getDescription());

		assertEquals("SRC_DIR", configurations.getAll().get(3).getName());
		assertEquals("The root directory containing the source files", configurations.getAll().get(3).getDescription());

		assertEquals("FILE_PATTERN", configurations.getAll().get(4).getName());
		assertEquals("Input file name pattern", configurations.getAll().get(4).getDescription());

		assertEquals("VECTOR_DIR", configurations.getAll().get(5).getName());
		assertEquals("Where to output detected clone clusters", configurations.getAll().get(5).getDescription());

		assertEquals("CLUSTER_DIR", configurations.getAll().get(6).getName());
		assertEquals("Where to output detected clone clusters", configurations.getAll().get(6).getDescription());

		assertEquals("TIME_DIR", configurations.getAll().get(7).getName());
		assertEquals("Where to output timing/debugging info", configurations.getAll().get(7).getDescription());

		assertEquals("DECKARD_DIR", configurations.getAll().get(8).getName());
		assertEquals("Where is the home directory of Deckard", configurations.getAll().get(8).getDescription());

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
		assertEquals(16, configurations.getAllAsArguments().size());
		assertEquals("export MIN_TOKENS=1", configurations.getAllAsArguments().get(0));
		assertEquals("export STRIDE=0", configurations.getAllAsArguments().get(1));
		assertEquals("export SIMILARITY=1.0", configurations.getAllAsArguments().get(2));
		assertEquals("export SRC_DIR=" + new File("main/exec/materializer").getAbsolutePath(), configurations.getAllAsArguments().get(3));
		assertEquals("export FILE_PATTERN=*.java", configurations.getAllAsArguments().get(4));
		assertEquals("export VECTOR_DIR=" + new File("main/exec/measurer/vectors").getAbsolutePath(), configurations.getAllAsArguments().get(5));
		assertEquals("export CLUSTER_DIR=" + new File("main/exec/measurer/cluster").getAbsolutePath(), configurations.getAllAsArguments().get(6));
		assertEquals("export TIME_DIR=" + new File("main/exec/measurer/times").getAbsolutePath(), configurations.getAllAsArguments().get(7));
		assertEquals("export DECKARD_DIR=tool", configurations.getAllAsArguments().get(8));
		assertEquals("export VGEN_EXEC=tool/src/main/jvecgen", configurations.getAllAsArguments().get(9));
		assertEquals("export GROUPING_EXEC=tool/src/vgen/vgrouping/runvectorsort", configurations.getAllAsArguments().get(10));
		assertEquals("export CLUSTER_EXEC=tool/src/lsh/bin/enumBuckets", configurations.getAllAsArguments().get(11));
		assertEquals("export QUERY_EXEC=tool/src/lsh/bin/queryBuckets", configurations.getAllAsArguments().get(12));
		assertEquals("export POSTPRO_EXEC=tool/scripts/clonedetect/post_process_groupfile", configurations.getAllAsArguments().get(13));
		assertEquals("export GROUPING_S=1", configurations.getAllAsArguments().get(14));
		assertEquals("export MAX_PROC=1", configurations.getAllAsArguments().get(15));
	}

	@Test
	void changeValues() throws Exception {
		configurations.minTokens(2).stride(Integer.MAX_VALUE).similarity(0.9);
		assertEquals(16, configurations.getAllAsArguments().size());
		assertEquals("export MIN_TOKENS=2", configurations.getAllAsArguments().get(0));
		assertEquals("export STRIDE=inf", configurations.getAllAsArguments().get(1));
		assertEquals("export SIMILARITY=0.9", configurations.getAllAsArguments().get(2));
		assertEquals("export SRC_DIR=" + new File("main/exec/materializer").getAbsolutePath(), configurations.getAllAsArguments().get(3));
		assertEquals("export FILE_PATTERN=*.java", configurations.getAllAsArguments().get(4));
		assertEquals("export VECTOR_DIR=" + new File("main/exec/measurer/vectors").getAbsolutePath(), configurations.getAllAsArguments().get(5));
		assertEquals("export CLUSTER_DIR=" + new File("main/exec/measurer/cluster").getAbsolutePath(), configurations.getAllAsArguments().get(6));
		assertEquals("export TIME_DIR=" + new File("main/exec/measurer/times").getAbsolutePath(), configurations.getAllAsArguments().get(7));
		assertEquals("export DECKARD_DIR=tool", configurations.getAllAsArguments().get(8));
		assertEquals("export VGEN_EXEC=tool/src/main/jvecgen", configurations.getAllAsArguments().get(9));
		assertEquals("export GROUPING_EXEC=tool/src/vgen/vgrouping/runvectorsort", configurations.getAllAsArguments().get(10));
		assertEquals("export CLUSTER_EXEC=tool/src/lsh/bin/enumBuckets", configurations.getAllAsArguments().get(11));
		assertEquals("export QUERY_EXEC=tool/src/lsh/bin/queryBuckets", configurations.getAllAsArguments().get(12));
		assertEquals("export POSTPRO_EXEC=tool/scripts/clonedetect/post_process_groupfile", configurations.getAllAsArguments().get(13));
		assertEquals("export GROUPING_S=1", configurations.getAllAsArguments().get(14));
		assertEquals("export MAX_PROC=1", configurations.getAllAsArguments().get(15));
	}

	@Test
	void minTokensShouldBeLargerThanZero() throws Exception {
		assertThrows(InvalidConfigurationException.class, () -> {
			configurations.minTokens(-1);
		});
		assertThrows(InvalidConfigurationException.class, () -> {
			configurations.minTokens(0);
		});
		assertThrows(InvalidConfigurationException.class, () -> {
			configurations.minTokens(null);
		});
	}

	@Test
	void strideShouldBePositive() throws Exception {
		assertThrows(InvalidConfigurationException.class, () -> {
			configurations.stride(-1);
		});
		assertThrows(InvalidConfigurationException.class, () -> {
			configurations.stride(null);
		});
	}

	@Test
	void similarityShouldBeBetweenZeroAndOne() throws Exception {
		assertThrows(InvalidConfigurationException.class, () -> {
			configurations.similarity(-0.1);
		});
		assertThrows(InvalidConfigurationException.class, () -> {
			configurations.similarity(1.1);
		});
		assertThrows(InvalidConfigurationException.class, () -> {
			configurations.minTokens(null);
		});
	}

	@Test
	void getConfigurations() throws Exception {
		assertEquals(new File("main/exec/measurer/cluster").getAbsolutePath(), configurations.clusterDir());
	}
}
