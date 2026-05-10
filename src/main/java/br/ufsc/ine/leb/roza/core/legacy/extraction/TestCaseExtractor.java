package br.ufsc.ine.leb.roza.core.legacy.extraction;

import java.util.List;

import br.ufsc.ine.leb.roza.core.legacy.TestCase;
import br.ufsc.ine.leb.roza.core.legacy.TestClass;

public interface TestCaseExtractor {

	List<TestCase> extract(List<TestClass> testClasses);

}
