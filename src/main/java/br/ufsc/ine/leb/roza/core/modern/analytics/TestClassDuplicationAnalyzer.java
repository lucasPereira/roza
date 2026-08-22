package br.ufsc.ine.leb.roza.core.modern.analytics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.Field;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureKind;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

public final class TestClassDuplicationAnalyzer {

	private TestClassDuplicationAnalyzer() {
	}

	public static DuplicationMetrics analyze(List<TestClass> testClasses) {
		Map<String, Integer> frequencies = new HashMap<>();
		for (TestClass testClass : testClasses) {
			for (String statement : statements(testClass)) {
				frequencies.merge(statement, 1, Integer::sum);
			}
		}
		int uniqueDuplicatedLines = 0;
		int duplicatedLines = 0;
		for (int count : frequencies.values()) {
			if (count > 1) {
				uniqueDuplicatedLines++;
				duplicatedLines += count - 1;
			}
		}
		return new DuplicationMetrics(duplicatedLines, uniqueDuplicatedLines);
	}

	private static List<String> statements(TestClass testClass) {
		List<String> statements = new ArrayList<>();
		for (Field field : testClass.fields()) {
			statements.add(fieldDeclaration(field));
		}
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

	private static String fieldDeclaration(Field field) {
		return field.initialization()
				.filter(initialization -> !initialization.isAssertion())
				.map(initialization -> field.type() + " " + field.name() + " = " + initialization.normalizedText() + ";")
				.orElseGet(() -> field.type() + " " + field.name() + ";");
	}

	public static final class DuplicationMetrics {

		private final int duplicatedLines;
		private final int uniqueDuplicatedLines;

		public DuplicationMetrics(int duplicatedLines, int uniqueDuplicatedLines) {
			this.duplicatedLines = duplicatedLines;
			this.uniqueDuplicatedLines = uniqueDuplicatedLines;
		}

		public int duplicatedLines() {
			return duplicatedLines;
		}

		public int uniqueDuplicatedLines() {
			return uniqueDuplicatedLines;
		}
	}
}
