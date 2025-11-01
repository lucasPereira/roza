package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

class MaterializationReportTest {

	@Test
	void create() {
		TestCase testCase = new TestCase("example", List.of(), List.of());
		TestCaseMaterialization materialization = new TestCaseMaterialization(new File("example/Example.java"), 10, testCase);
		MaterializationReport materializtionReport = new MaterializationReport("example", List.of(materialization));
		assertEquals(1, materializtionReport.getMaterialization().size());
		assertEquals(materialization, materializtionReport.getMaterialization().get(0));
		assertEquals("example", materializtionReport.getBaseFolder());
	}

}
