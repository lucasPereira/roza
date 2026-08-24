package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.util.Objects;

import br.ufsc.ine.leb.roza.core.modern.StageProgress;
import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;

public interface TestCaseSimilarityMeasurer {

	TestCaseSimilarityMatrix measure(DecomposedTestCases testCases);

	default TestCaseSimilarityMatrix measure(DecomposedTestCases testCases, StageProgress progress) {
		Objects.requireNonNull(progress);
		progress.report(0, 0);
		try {
			return measure(testCases);
		} finally {
			progress.report(1, 1);
		}
	}
}
