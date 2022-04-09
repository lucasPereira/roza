package br.ufsc.ine.leb.roza.parsing;

import java.util.List;

import br.ufsc.ine.leb.roza.loading.TextFile;

public interface TestClassParser {

	List<TestClass> parse(List<TextFile> files);

}
