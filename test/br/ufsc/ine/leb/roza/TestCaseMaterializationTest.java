package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class TestCaseMaterializationTest {

	@Test
	void create() throws Exception {
		TestCase testCase = new TestCase("example", Arrays.asList(), Arrays.asList());
		TestMaterialization<TestCase> materialization = new TestMaterialization<>(new File("generated/Example.java"), testCase);
		assertEquals("Example.java", materialization.getFileName());
		assertEquals("generated/Example.java", materialization.getFilePath());
		assertEquals("generated", materialization.getFolder());
		assertEquals(testCase, materialization.getTestBlock());
	}

}
