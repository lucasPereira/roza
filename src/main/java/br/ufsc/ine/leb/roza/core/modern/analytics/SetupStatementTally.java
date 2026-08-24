package br.ufsc.ine.leb.roza.core.modern.analytics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class SetupStatementTally {

	private final Map<String, Integer> frequencies = new HashMap<>();
	private int totalStatements;
	private int duplicatedStatements;

	void add(List<String> statements) {
		for (String statement : statements) {
			int frequency = frequencies.merge(statement, 1, Integer::sum);
			totalStatements++;
			if (frequency >= 2) {
				duplicatedStatements++;
			}
		}
	}

	void remove(List<String> statements) {
		for (String statement : statements) {
			Integer frequency = frequencies.get(statement);
			if (frequency == null || frequency < 1) {
				throw new IllegalStateException("Cannot remove setup statement that is not in the tally.");
			}
			if (frequency >= 2) {
				duplicatedStatements--;
			}
			totalStatements--;
			if (frequency == 1) {
				frequencies.remove(statement);
			} else {
				frequencies.put(statement, frequency - 1);
			}
		}
	}

	int duplicatedStatements() {
		return duplicatedStatements;
	}

	int totalStatements() {
		return totalStatements;
	}
}
