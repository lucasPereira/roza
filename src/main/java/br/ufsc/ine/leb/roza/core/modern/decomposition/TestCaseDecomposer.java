package br.ufsc.ine.leb.roza.core.modern.decomposition;

import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;

public interface TestCaseDecomposer {

	DecomposedTestCases decompose(ParsedTestClasses parsedTestClasses);
}
