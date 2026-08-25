package br.ufsc.ine.leb.roza.expt.n;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.expt.n.ThesisTables.ResultRow;

final class ExperimentOptions {

	private final boolean missingOnly;

	private ExperimentOptions(boolean missingOnly) {
		this.missingOnly = missingOnly;
	}

	boolean missingOnly() {
		return missingOnly;
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
		for (String argument : args) {
			if ("--missing-only".equals(argument)) {
				missingOnly = true;
			} else {
				throw new IllegalArgumentException(usage("Unknown argument: " + argument));
			}
		}
		return new ExperimentOptions(missingOnly);
	}

	private static String usage(String message) {
		return message + ". Usage: [--missing-only]";
	}
}
