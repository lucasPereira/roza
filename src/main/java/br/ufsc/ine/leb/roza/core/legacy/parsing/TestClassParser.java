package br.ufsc.ine.leb.roza.core.legacy.parsing;

import java.util.List;

import br.ufsc.ine.leb.roza.core.legacy.TestClass;
import br.ufsc.ine.leb.roza.core.legacy.TextFile;

public interface TestClassParser {

	List<TestClass> parse(List<TextFile> files);

}
