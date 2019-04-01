package br.ufsc.ine.leb.roza.materializer;

import java.util.List;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.TestCase;

public interface TestCaseMaterializer {

	MaterializationReport materialize(List<TestCase> tests);

}
