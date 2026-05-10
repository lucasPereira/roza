package br.ufsc.ine.leb.roza.core.materialization;

import java.util.List;

import br.ufsc.ine.leb.roza.core.MaterializationReport;
import br.ufsc.ine.leb.roza.core.TestCase;

public interface TestCaseMaterializer {

	MaterializationReport materialize(List<TestCase> tests);

}
