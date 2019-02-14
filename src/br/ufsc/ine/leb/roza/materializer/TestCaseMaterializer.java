package br.ufsc.ine.leb.roza.materializer;

import java.util.List;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;

public interface TestCaseMaterializer {

	List<TestCaseMaterialization> materialize(List<TestCase> tests);

}
