package br.ufsc.ine.leb.roza.core.modern.clustering;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;

public final class TestCaseCluster {

	private final List<Integer> testCaseIndexes;
	private final List<TestCase> testCases;

	public TestCaseCluster(int testCaseIndex, TestCase testCase) {
		this(List.of(testCaseIndex), List.of(Objects.requireNonNull(testCase)));
	}

	private TestCaseCluster(List<Integer> testCaseIndexes, List<TestCase> testCases) {
		this.testCaseIndexes = List.copyOf(Objects.requireNonNull(testCaseIndexes));
		this.testCases = List.copyOf(Objects.requireNonNull(testCases));
	}

	public TestCaseCluster merge(TestCaseCluster other) {
		List<IndexedTestCase> merged = Stream.concat(indexedTestCases().stream(), other.indexedTestCases().stream())
				.sorted(Comparator.comparingInt(IndexedTestCase::index))
				.collect(Collectors.toList());
		return new TestCaseCluster(
				merged.stream().map(IndexedTestCase::index).collect(Collectors.toList()),
				merged.stream().map(IndexedTestCase::testCase).collect(Collectors.toList()));
	}

	public List<TestCase> testCases() {
		return testCases;
	}

	public List<Integer> testCaseIndexes() {
		return testCaseIndexes;
	}

	public int size() {
		return testCases.size();
	}

	public int firstTestCaseIndex() {
		return testCaseIndexes.get(0);
	}

	private List<IndexedTestCase> indexedTestCases() {
		return IntStream.range(0, testCaseIndexes.size())
				.mapToObj(position -> new IndexedTestCase(testCaseIndexes.get(position), testCases.get(position)))
				.collect(Collectors.toList());
	}

	private static final class IndexedTestCase {

		private final int index;
		private final TestCase testCase;

		private IndexedTestCase(int index, TestCase testCase) {
			this.index = index;
			this.testCase = testCase;
		}

		private int index() {
			return index;
		}

		private TestCase testCase() {
			return testCase;
		}
	}
}
