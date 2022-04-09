package br.ufsc.ine.leb.roza.materialization;

import java.util.List;

import br.ufsc.ine.leb.roza.extraction.TestCase;

public interface TestCaseMaterializer {

	MaterializationReport materialize(List<TestCase> tests);

}
