package br.ufsc.ine.leb.roza.extractor;

import java.util.List;

import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TextFile;

public interface TestClassExtractor {

	public List<TestClass> select(List<TextFile> files);

}
