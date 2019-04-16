package br.ufsc.ine.leb.roza.uniformity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestField;
import br.ufsc.ine.leb.roza.TestMaterialization;
import br.ufsc.ine.leb.roza.TestMethod;

public class MaterializationReportTest {

	@Test
	void create() throws Exception {
		TestMethod testMethod = new TestMethod("test", Arrays.asList());
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));
		TestMaterialization<TestClass> materialization = new TestMaterialization<>(
				new File("generated/ExampleTest.java"), testClass);
		List<TestMaterialization<TestClass>> testMaterialization = Arrays.asList(materialization);
		MaterializationReport<TestClass> materializationReport = new MaterializationReport<TestClass>("generated",
				testMaterialization);
		assertEquals(1, materializationReport.getMaterializations().size());
		assertEquals(materialization, materializationReport.getMaterializations().get(0));
		assertEquals("generated", materializationReport.getBaseFolder());
	}

	@Test
	void createTestField() throws Exception {
		TestMethod testMethod = new TestMethod("test", Arrays.asList());
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));
		TestField fields = new TestField(testClass, Arrays.asList());
		TestMaterialization<TestField> materialization = new TestMaterialization<>(
				new File("generated/ExampleTest.field"), fields);
		List<TestMaterialization<TestField>> testMaterialization = Arrays.asList(materialization);
		MaterializationReport<TestField> materializationReport = new MaterializationReport<TestField>("generated",
				testMaterialization);
		assertEquals(1, materializationReport.getMaterializations().size());
		assertEquals(materialization, materializationReport.getMaterializations().get(0));
		assertEquals("generated", materializationReport.getBaseFolder());
	}

}
