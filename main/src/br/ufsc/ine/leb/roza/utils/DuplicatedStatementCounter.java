package br.ufsc.ine.leb.roza.utils;

import br.ufsc.ine.leb.roza.SetupMethod;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestMethod;
import br.ufsc.ine.leb.roza.extraction.Junit4TestCaseExtractor;
import br.ufsc.ine.leb.roza.extraction.JunitTestCaseExtractor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicatedStatementCounter {

	/**
	 * <p>>Counts the number of unique duplicated statements across multiple test classes.
	 * A statement is considered duplicated if it appears more than once.
	 * Each unique statement text is counted only once, regardless of how many times it appears.</p>
	 *
	 * </p>Counts the total number of duplicate occurrences (not unique statements).
	 * For example, if a statement appears 3 times, it contributes 2 to the count (3 - 1 = 2 duplicates).</p>
	 *
	 * </p>Returns detailed information about duplicated statements.</p>
	 *
	 * @param testClasses List of test classes to analyze
	 * @return number of unique duplicated statements, total number of duplicate occurrences,
	 * and detailed information about duplicated statements
	 */
	public StatementCount count(List<TestClass> testClasses) {
		JunitTestCaseExtractor extractor = new Junit4TestCaseExtractor();
		Map<String, Integer> statementCounts = new HashMap<>();
		int statementCount = 0;

		for (TestClass testClass : testClasses) {
			for (SetupMethod setupMethod : testClass.getSetupMethods()) {
				for (Statement statement : setupMethod.getStatements()) {
					if (!extractor.statementIsAssertion(statement)) {
						statementCount++;
						String statementText = statement.getText();
						statementCounts.put(statementText, statementCounts.getOrDefault(statementText, 0) + 1);
					}
				}
			}

			for (TestMethod testMethod : testClass.getTestMethods()) {
				for (Statement statement : testMethod.getStatements()) {
					if (!extractor.statementIsAssertion(statement)) {
						statementCount++;
						String statementText = statement.getText();
						statementCounts.put(statementText, statementCounts.getOrDefault(statementText, 0) + 1);
					}
				}
			}
		}

		var count = statementCounts
			.values()
			.stream()
			.map(value -> value > 1 ? 1 : 0)
			.mapToInt(Integer::intValue)
			.sum();

		var totalCount = statementCounts
			.values()
			.stream()
			.map(value -> value > 1 ? value - 1 : 0)
			.mapToInt(Integer::intValue)
			.sum();

		Map<String, Integer> duplicatedStatements = new HashMap<>();
		for (Map.Entry<String, Integer> entry : statementCounts.entrySet()) {
			if (entry.getValue() > 1) {
				duplicatedStatements.put(entry.getKey(), entry.getValue());
			}
		}

		return new StatementCount(statementCount, count, totalCount, duplicatedStatements);
	}

	public static class StatementCount {
		private final int statementCount;
		private final int uniqueDuplicateCount;
		private final int totalDuplicateCount;
		private final Map<String, Integer> countByDuplicatedStatement;

		public StatementCount(int statementCount, int uniqueDuplicateCount, int totalDuplicateCount, Map<String, Integer> countByDuplicatedStatement) {
			this.statementCount = statementCount;
			this.uniqueDuplicateCount = uniqueDuplicateCount;
			this.totalDuplicateCount = totalDuplicateCount;
			this.countByDuplicatedStatement = countByDuplicatedStatement;
		}

		public int getStatementCount() {
			return statementCount;
		}

		public int getUniqueDuplicateCount() {
			return uniqueDuplicateCount;
		}

		public int getTotalDuplicateCount() {
			return totalDuplicateCount;
		}

		public Map<String, Integer> getCountByDuplicatedStatement() {
			return countByDuplicatedStatement;
		}
	}
}
