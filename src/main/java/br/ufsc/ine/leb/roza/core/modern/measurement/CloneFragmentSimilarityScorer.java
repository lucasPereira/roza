package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

final class CloneFragmentSimilarityScorer {

	TestCaseSimilarityMatrix score(List<MaterializedTestCase> testCases, List<CloneFragment> fragments) {
		Objects.requireNonNull(testCases);
		Objects.requireNonNull(fragments);
		TestCaseSimilarityMatrix matrix = new TestCaseSimilarityMatrix(testCases.stream()
				.map(MaterializedTestCase::testCase)
				.collect(Collectors.toList()));
		Map<String, MaterializedTestCase> testCaseByFileName = testCases.stream()
				.collect(Collectors.toMap(MaterializedTestCase::fileName, testCase -> testCase));
		for (int source = 0; source < testCases.size(); source++) {
			for (int target = 0; target < testCases.size(); target++) {
				if (source != target) {
					matrix.setSimilarity(source, target, score(testCases.get(source), testCases.get(target), testCaseByFileName, fragments));
				}
			}
		}
		return matrix;
	}

	private double score(
			MaterializedTestCase source,
			MaterializedTestCase target,
			Map<String, MaterializedTestCase> testCaseByFileName,
			List<CloneFragment> fragments) {
		if (source.projectedLineCount() == 0) {
			return 0.0;
		}
		List<Interval> intervals = fragments.stream()
				.filter(fragment -> fileName(fragment.sourceFile()).equals(source.fileName()))
				.filter(fragment -> fileName(fragment.targetFile()).equals(target.fileName()))
				.filter(fragment -> testCaseByFileName.containsKey(fileName(fragment.sourceFile())))
				.map(fragment -> intersection(source, fragment))
				.filter(interval -> interval != null)
				.collect(Collectors.toList());
		return ((double) coveredLineCount(intervals)) / source.projectedLineCount();
	}

	private Interval intersection(MaterializedTestCase source, CloneFragment fragment) {
		int start = Math.max(source.firstProjectedLine(), fragment.startLine());
		int end = Math.min(source.lastProjectedLine(), fragment.endLine());
		if (start > end) {
			return null;
		}
		return new Interval(start, end);
	}

	private int coveredLineCount(List<Interval> intervals) {
		if (intervals.isEmpty()) {
			return 0;
		}
		intervals.sort(Comparator.comparingInt(Interval::start).thenComparingInt(Interval::end));
		int covered = 0;
		Interval current = intervals.get(0);
		for (int index = 1; index < intervals.size(); index++) {
			Interval next = intervals.get(index);
			if (next.start() <= current.end() + 1) {
				current = new Interval(current.start(), Math.max(current.end(), next.end()));
			} else {
				covered += current.length();
				current = next;
			}
		}
		return covered + current.length();
	}

	private String fileName(String path) {
		return Path.of(path).getFileName().toString();
	}

	private static final class Interval {

		private final int start;
		private final int end;

		private Interval(int start, int end) {
			this.start = start;
			this.end = end;
		}

		private int start() {
			return start;
		}

		private int end() {
			return end;
		}

		private int length() {
			return end - start + 1;
		}
	}
}
