package br.ufsc.ine.leb.roza.materialization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.extraction.TestCase;
import br.ufsc.ine.leb.roza.materialization.MaterializationReport;
import br.ufsc.ine.leb.roza.materialization.TestCaseMaterialization;

class MaterializationReportTest {

	@Test
	void create() throws Exception {
		TestCase testCase = new TestCase("example", Arrays.asList(), Arrays.asList());
		TestCaseMaterialization materialization = new TestCaseMaterialization(new File("example/Example.java"), 10, testCase);
		MaterializationReport materializtionReport = new MaterializationReport("example", Arrays.asList(materialization));
		assertEquals(1, materializtionReport.getMaterializations().size());
		assertEquals(materialization, materializtionReport.getMaterializations().get(0));
		assertEquals("example", materializtionReport.getBaseFolder());
	}

}
