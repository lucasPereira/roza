package br.ufsc.ine.leb.roza.uniformity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TestMaterialization;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestMethod;

public class TestClassMaterializationTest {

	@Test
	void create() throws Exception {
		TestMethod testMethod = new TestMethod("test", Arrays.asList());
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));
		TestMaterialization<TestClass> materialization = new TestMaterialization<>(
				new File("generated/ExampleTest.java"), testClass);
		assertEquals("ExampleTest.java", materialization.getFileName());
		assertEquals("generated/ExampleTest.java", materialization.getFilePath());
		assertEquals("generated", materialization.getFolder());
		assertEquals(testClass, materialization.getTestBlock());
	}

}
