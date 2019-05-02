package br.ufsc.ine.leb.roza.measurer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.measurer.SimianMatrixElementToKeyConverter;
import br.ufsc.ine.leb.roza.support.matrix.MatrixElementToKeyConverter;

public class SimianElementToKeyConverterTest {

	@Test
	void testCaseMaterializationToString() throws Exception {
		TestCase testCase = new TestCase("test", Arrays.asList(), Arrays.asList());
		TestCaseMaterialization materialization = new TestCaseMaterialization(new File("Materialization.java"), 10, testCase);
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new SimianMatrixElementToKeyConverter();
		assertEquals(materialization.getAbsoluteFilePath(), converter.convert(materialization));
	}

}
