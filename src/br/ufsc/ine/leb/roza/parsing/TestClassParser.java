package br.ufsc.ine.leb.roza.parsing;

import java.util.List;

import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TextFile;

public interface TestClassParser {

	List<TestClass> parse(List<TextFile> files);

}
