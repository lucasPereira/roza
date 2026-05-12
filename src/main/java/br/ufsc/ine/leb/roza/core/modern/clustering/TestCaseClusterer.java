package br.ufsc.ine.leb.roza.core.modern.clustering;

import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMatrix;

public interface TestCaseClusterer {

	TestCaseClusters cluster(TestCaseSimilarityMatrix matrix);
}
