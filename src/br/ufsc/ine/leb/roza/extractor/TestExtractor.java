package br.ufsc.ine.leb.roza.extractor;

import java.util.List;

import br.ufsc.ine.leb.roza.TestClass;

public interface TestExtractor<T> {

	List<T> extract(List<TestClass> testClasses);

}
