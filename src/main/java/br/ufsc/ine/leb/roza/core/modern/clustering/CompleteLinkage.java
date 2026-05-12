package br.ufsc.ine.leb.roza.core.modern.clustering;

import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMatrix;

public final class CompleteLinkage implements ClusterLinkage {

	@Override
	public double evaluate(TestCaseSimilarityMatrix matrix, TestCaseCluster first, TestCaseCluster second) {
		double farthest = 1.0;
		for (int firstIndex : first.testCaseIndexes()) {
			for (int secondIndex : second.testCaseIndexes()) {
				farthest = Math.min(farthest, matrix.similarity(firstIndex, secondIndex));
				farthest = Math.min(farthest, matrix.similarity(secondIndex, firstIndex));
			}
		}
		return farthest;
	}
}
