package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.util.List;
import java.util.Objects;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;

public final class TestCaseSimilarityMatrix {

	private final List<TestCase> testCases;
	private final double[] similarities;

	public TestCaseSimilarityMatrix(List<TestCase> testCases) {
		this.testCases = List.copyOf(Objects.requireNonNull(testCases));
		similarities = new double[this.testCases.size() * this.testCases.size()];
		for (int index = 0; index < this.testCases.size(); index++) {
			setSimilarity(index, index, 1.0);
		}
	}

	void setSimilarity(int sourceIndex, int targetIndex, double similarity) {
		similarities[index(sourceIndex, targetIndex)] = similarity;
	}

	double similarity(int sourceIndex, int targetIndex) {
		return similarities[index(sourceIndex, targetIndex)];
	}

	int size() {
		return testCases.size();
	}

	private int index(int sourceIndex, int targetIndex) {
		return sourceIndex * testCases.size() + targetIndex;
	}
}
