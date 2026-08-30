package br.ufsc.ine.leb.roza.expt.n;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.expt.n.ThesisTables.ResultRow;

final class ExperimentOptions {

	private final boolean missingOnly;
	private final boolean chartsOnly;

	private ExperimentOptions(boolean missingOnly, boolean chartsOnly) {
		this.missingOnly = missingOnly;
		this.chartsOnly = chartsOnly;
	}

	boolean missingOnly() {
		return missingOnly;
	}

	boolean chartsOnly() {
		return chartsOnly;
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
		boolean chartsOnly = false;
		for (String argument : args) {
			if ("--missing-only".equals(argument)) {
				missingOnly = true;
			} else if ("--charts-only".equals(argument)) {
				chartsOnly = true;
			} else {
				throw new IllegalArgumentException(usage("Unknown argument: " + argument));
			}
		}
		if (missingOnly && chartsOnly) {
			throw new IllegalArgumentException(usage("Cannot combine --missing-only and --charts-only"));
		}
		return new ExperimentOptions(missingOnly, chartsOnly);
	}

	private static String usage(String message) {
		return message + ". Usage: [--missing-only | --charts-only]";
	}
}
