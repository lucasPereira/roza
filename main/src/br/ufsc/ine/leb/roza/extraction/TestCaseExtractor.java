package br.ufsc.ine.leb.roza.extraction;

import java.util.List;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;

public interface TestCaseExtractor {

	List<TestCase> extract(List<TestClass> testClasses);

}
