package br.ufsc.ine.leb.roza.core.modern.clustering;

import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMatrix;

public final class SingleLinkage implements ClusterLinkage {

	@Override
	public double evaluate(TestCaseSimilarityMatrix matrix, TestCaseCluster first, TestCaseCluster second) {
		double nearest = 0.0;
		for (int firstIndex : first.testCaseIndexes()) {
			for (int secondIndex : second.testCaseIndexes()) {
				nearest = Math.max(nearest, matrix.similarity(firstIndex, secondIndex));
				nearest = Math.max(nearest, matrix.similarity(secondIndex, firstIndex));
			}
		}
		return nearest;
	}

	@Override
	public double combinedSimilarity(double similarityWithFirst, double similarityWithSecond, int firstSize, int secondSize) {
		return Math.max(similarityWithFirst, similarityWithSecond);
	}
}
