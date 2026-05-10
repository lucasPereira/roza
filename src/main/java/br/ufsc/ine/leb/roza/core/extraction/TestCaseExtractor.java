package br.ufsc.ine.leb.roza.core.extraction;

import java.util.List;

import br.ufsc.ine.leb.roza.core.TestCase;
import br.ufsc.ine.leb.roza.core.TestClass;

public interface TestCaseExtractor {

	List<TestCase> extract(List<TestClass> testClasses);

}
