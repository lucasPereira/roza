package br.ufsc.ine.leb.roza.parser;

import java.util.List;

import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TextFile;

public interface TestClassParser {

	List<TestClass> parse(List<TextFile> files);

}
