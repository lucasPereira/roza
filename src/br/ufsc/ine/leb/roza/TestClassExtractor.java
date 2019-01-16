package br.ufsc.ine.leb.roza;

import java.util.List;

public interface TestClassExtractor {

	public List<TestClass> select(List<TextFile> files);

}
