package br.ufsc.ine.leb.roza.expt.n;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.expt.n.StatisticalTables.ResultRow;

final class ExperimentOptions {

	private final boolean missingOnly;
	private final boolean fromComparison;

	private ExperimentOptions(boolean missingOnly, boolean fromComparison) {
		this.missingOnly = missingOnly;
		this.fromComparison = fromComparison;
	}

	boolean missingOnly() {
		return missingOnly;
	}

	boolean fromComparison() {
		return fromComparison;
	}

	List<Subjects.Subject> subjects(List<Subjects.Subject> all, List<ResultRow> rows) {
		if (!missingOnly) {
			return all;
		}
		List<Subjects.Subject> missing = new ArrayList<>();
		for (Subjects.Subject subject : all) {
			if (!ExperimentResume.subjectComplete(rows, subject.name())) {
				missing.add(subject);
			}
		}
		return missing;
	}

	static ExperimentOptions parse(String[] args) {
		boolean missingOnly = false;
		boolean fromComparison = false;
		for (String argument : args) {
			if ("--missing-only".equals(argument)) {
				missingOnly = true;
			} else if ("--from-comparison".equals(argument)) {
				fromComparison = true;
			} else {
				throw new IllegalArgumentException(usage("Unknown argument: " + argument));
			}
		}
		if (missingOnly && fromComparison) {
			throw new IllegalArgumentException(usage("Cannot combine --missing-only and --from-comparison"));
		}
		return new ExperimentOptions(missingOnly, fromComparison);
	}

	private static String usage(String message) {
		return message + ". Usage: [--missing-only | --from-comparison]";
	}
}
