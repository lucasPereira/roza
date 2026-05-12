package br.ufsc.ine.leb.roza.core.modern.clustering;

import java.util.List;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

public final class MinimumSharedPrefixStopCriterion implements StopCriterion {

	private final int threshold;

	public MinimumSharedPrefixStopCriterion(int threshold) {
		if (threshold < 0) {
			throw new IllegalArgumentException("Minimum shared prefix threshold must not be negative.");
		}
		this.threshold = threshold;
	}

	@Override
	public boolean shouldStop(StopCriterionContext context) {
		return commonPrefixSize(context.candidate().mergedCluster().testCases()) <= threshold;
	}

	private int commonPrefixSize(List<TestCase> testCases) {
		if (testCases.isEmpty()) {
			return 0;
		}
		List<String> prefix = statements(testCases.get(0));
		int size = prefix.size();
		for (int index = 1; index < testCases.size(); index++) {
			List<String> current = statements(testCases.get(index));
			size = Math.min(size, current.size());
			for (int statement = 0; statement < size; statement++) {
				if (!prefix.get(statement).equals(current.get(statement))) {
					size = statement;
					break;
				}
			}
		}
		return size;
	}

	private List<String> statements(TestCase testCase) {
		return testCase.body().statements().stream().map(CodeStatement::normalizedText).collect(Collectors.toList());
	}
}
