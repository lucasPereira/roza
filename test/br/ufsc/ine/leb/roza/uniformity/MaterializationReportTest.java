package br.ufsc.ine.leb.roza.uniformity;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestMethod;

public class MaterializationReportTest {

	@Test
	void create() throws Exception {
		TestMethod testMethod = new TestMethod("test", Arrays.asList());
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));
		TestCaseMaterialization<TestClass> materialization = new TestCaseMaterialization<>(new File("generated/Example.java"), testClass);
		List<TestCaseMaterialization<TestClass>> a=Arrays.asList(materialization);
		MaterializationReport<TestClass> materializtionReport = new MaterializationReport<TestClass>("generated", a);
		assertEquals(1, materializtionReport.getMaterializations().size());
		assertEquals(materialization, materializtionReport.getMaterializations().get(0));
		assertEquals("generated", materializtionReport.getBaseFolder());
	}

}
