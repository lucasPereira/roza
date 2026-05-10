package br.ufsc.ine.leb.roza.core.measurement.matrix.deckard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.TestCase;
import br.ufsc.ine.leb.roza.core.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.core.measurement.matrix.MatrixElementToKeyConverter;

class DeckardElementToKeyConverterTest {

	@Test
	void testCaseMaterializationToString() {
		TestCase testCase = new TestCase("test", List.of(), List.of());
		TestCaseMaterialization materialization = new TestCaseMaterialization(new File("Materialization.java"), 10, testCase);
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new DeckardMatrixElementToKeyConverter();
		assertEquals(materialization.getFileName(), converter.convert(materialization));
	}

}
