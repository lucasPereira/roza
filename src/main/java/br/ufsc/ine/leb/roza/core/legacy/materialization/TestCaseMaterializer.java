package br.ufsc.ine.leb.roza.core.legacy.materialization;

import java.util.List;

import br.ufsc.ine.leb.roza.core.legacy.MaterializationReport;
import br.ufsc.ine.leb.roza.core.legacy.TestCase;

public interface TestCaseMaterializer {

	MaterializationReport materialize(List<TestCase> tests);

}
