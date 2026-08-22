package br.ufsc.ine.leb.roza.core.modern.analytics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureKind;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

public final class TestClassDuplicationAnalyzer {

	private TestClassDuplicationAnalyzer() {
	}

	public static DuplicationMetrics analyze(List<TestClass> testClasses) {
		Map<String, Integer> frequencies = new HashMap<>();
		int totalStatements = 0;
		for (TestClass testClass : testClasses) {
			for (String statement : statements(testClass)) {
				totalStatements++;
				frequencies.merge(statement, 1, Integer::sum);
			}
		}
		int duplicatedStatements = 0;
		for (int count : frequencies.values()) {
			if (count > 1) {
				duplicatedStatements += count - 1;
			}
		}
		return new DuplicationMetrics(totalStatements, duplicatedStatements);
	}

	private static List<String> statements(TestClass testClass) {
		List<String> statements = new ArrayList<>();
		for (FixtureMethod fixture : testClass.fixtures()) {
			if (fixture.kind() != FixtureKind.BEFORE) {
				continue;
			}
			for (CodeStatement statement : fixture.body().statements()) {
				if (!statement.isAssertion()) {
					statements.add(statement.normalizedText());
				}
			}
		}
		for (TestMethod testMethod : testClass.testMethods()) {
			for (CodeStatement statement : testMethod.body().statements()) {
				if (!statement.isAssertion()) {
					statements.add(statement.normalizedText());
				}
			}
		}
		return statements;
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
