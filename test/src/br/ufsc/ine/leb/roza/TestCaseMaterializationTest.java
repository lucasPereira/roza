package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class TestCaseMaterializationTest {

	@Test
	void create() throws Exception {
		TestCase testCase = new TestCase("example", Arrays.asList(), Arrays.asList());
		TestCaseMaterialization materialization = new TestCaseMaterialization(new File("example/Example.java"), 5, testCase);
		assertEquals("Example.java", materialization.getFileName());
		assertEquals(5, materialization.getLength().intValue());
		assertEquals("example/Example.java", materialization.getFilePath());
		assertEquals("example", materialization.getFolder());
		assertEquals(testCase, materialization.getTestCase());
	}

}
