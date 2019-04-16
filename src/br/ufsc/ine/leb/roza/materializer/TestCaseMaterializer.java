package br.ufsc.ine.leb.roza.materializer;

import java.util.List;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.TestBlock;

public interface TestCaseMaterializer<T extends TestBlock> {

	MaterializationReport<T> materialize(List<T> tests);

}
