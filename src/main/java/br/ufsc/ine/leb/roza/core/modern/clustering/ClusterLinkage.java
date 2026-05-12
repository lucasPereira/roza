package br.ufsc.ine.leb.roza.core.modern.clustering;

import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMatrix;

public interface ClusterLinkage {

	double evaluate(TestCaseSimilarityMatrix matrix, TestCaseCluster first, TestCaseCluster second);
}
