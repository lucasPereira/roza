package br.ufsc.ine.leb.roza.core.modern.clustering;

import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMatrix;

public final class AverageLinkage implements ClusterLinkage {

	@Override
	public double evaluate(TestCaseSimilarityMatrix matrix, TestCaseCluster first, TestCaseCluster second) {
		double sum = 0.0;
		for (int firstIndex : first.testCaseIndexes()) {
			for (int secondIndex : second.testCaseIndexes()) {
				sum += Math.max(matrix.similarity(firstIndex, secondIndex), matrix.similarity(secondIndex, firstIndex));
			}
		}
		return sum / (first.size() * second.size());
	}
}
