package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.util.Objects;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;

final class DeckardMaterializedTestCase {

	private final TestCase testCase;
	private final String fileName;
	private final int firstProjectedLine;
	private final int projectedLineCount;

	DeckardMaterializedTestCase(TestCase testCase, String fileName, int firstProjectedLine, int projectedLineCount) {
		this.testCase = Objects.requireNonNull(testCase);
		this.fileName = Objects.requireNonNull(fileName);
		this.firstProjectedLine = firstProjectedLine;
		this.projectedLineCount = projectedLineCount;
	}

	TestCase testCase() {
		return testCase;
	}

	String fileName() {
		return fileName;
	}

	int firstProjectedLine() {
		return firstProjectedLine;
	}

	int lastProjectedLine() {
		return firstProjectedLine + projectedLineCount - 1;
	}

	int projectedLineCount() {
		return projectedLineCount;
	}
}
