package br.ufsc.ine.leb.roza.core.modern.parsing;

import br.ufsc.ine.leb.roza.core.modern.loading.LoadedCodeFiles;

public interface TestClassParser {

	ParsedTestClasses parse(LoadedCodeFiles codeFiles);
}
