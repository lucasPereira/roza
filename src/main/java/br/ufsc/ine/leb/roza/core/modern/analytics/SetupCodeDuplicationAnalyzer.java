package br.ufsc.ine.leb.roza.core.modern.analytics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;

public final class SetupCodeDuplicationAnalyzer {

	public static DuplicationMetrics analyze(List<TestClass> testClasses) {
		List<String> statements = SetupCodeProjection.statements(testClasses);
		Map<String, Integer> frequencies = new HashMap<>();
		for (String statement : statements) {
			frequencies.merge(statement, 1, Integer::sum);
		}
		int duplicatedStatements = frequencies.values().stream()
				.filter(count -> count > 1)
				.mapToInt(count -> count - 1)
				.sum();
		return new DuplicationMetrics(statements.size(), duplicatedStatements);
	}

	public static final class DuplicationMetrics {

		private final int totalStatements;
		private final int duplicatedStatements;

		public DuplicationMetrics(int totalStatements, int duplicatedStatements) {
			this.totalStatements = totalStatements;
			this.duplicatedStatements = duplicatedStatements;
		}

		public int totalStatements() {
			return totalStatements;
		}

		public int duplicatedStatements() {
			return duplicatedStatements;
		}
	}
}
