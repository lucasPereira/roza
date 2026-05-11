package br.ufsc.ine.leb.roza.core.modern.measurement;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;

public interface TestCaseSimilarityMeasurer {

	TestCaseSimilarityMatrix measure(DecomposedTestCases testCases);
}
