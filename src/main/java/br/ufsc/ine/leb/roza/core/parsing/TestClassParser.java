package br.ufsc.ine.leb.roza.core.parsing;

import java.util.List;

import br.ufsc.ine.leb.roza.core.TestClass;
import br.ufsc.ine.leb.roza.core.TextFile;

public interface TestClassParser {

	List<TestClass> parse(List<TextFile> files);

}
