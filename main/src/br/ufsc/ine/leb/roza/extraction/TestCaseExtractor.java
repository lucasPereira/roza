package br.ufsc.ine.leb.roza.extraction;

import java.util.List;

import br.ufsc.ine.leb.roza.parsing.TestClass;

public interface TestCaseExtractor {

	List<TestCase> extract(List<TestClass> testClasses);

}
