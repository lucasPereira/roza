package br.ufsc.ine.leb.roza.measurement.matrix.deckard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.extraction.TestCase;
import br.ufsc.ine.leb.roza.materialization.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.measurement.matrix.MatrixElementToKeyConverter;

class DeckardElementToKeyConverterTest {

	@Test
	void testCaseMaterializationToString() throws Exception {
		TestCase testCase = new TestCase("test", Arrays.asList(), Arrays.asList());
		TestCaseMaterialization materialization = new TestCaseMaterialization(new File("Materialization.java"), 10, testCase);
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new DeckardMatrixElementToKeyConverter();
		assertEquals(materialization.getAbsoluteFilePath(), converter.convert(materialization));
	}

}
