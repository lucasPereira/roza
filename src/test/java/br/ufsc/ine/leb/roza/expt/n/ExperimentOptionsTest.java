package br.ufsc.ine.leb.roza.expt.n;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.expt.n.ThesisTables.ResultRow;

class ExperimentOptionsTest {

	@Test
	void shouldDefaultToEverySubject() {
		ExperimentOptions options = ExperimentOptions.parse(new String[] {});
		assertFalse(options.missingOnly());
		assertEquals(Subjects.all().size(), options.subjects(Subjects.all(), List.of()).size());
	}

	@Test
	void shouldSelectIncompleteSubjectsForMissingOnly() {
		List<ResultRow> rows = new ArrayList<>();
		for (String variant : ThesisTables.TREATMENTS) {
			rows.add(row("roza", variant));
		}
		rows.add(row("javaparser", "original"));
		ExperimentOptions options = ExperimentOptions.parse(new String[] { "--missing-only" });
		assertTrue(options.missingOnly());
		List<String> missing = options.subjects(Subjects.all(), rows).stream()
				.map(Subjects.Subject::name)
				.collect(Collectors.toList());
		assertFalse(missing.contains("roza"));
		assertTrue(missing.contains("javaparser"));
		assertTrue(missing.contains("saas+teste"));
	}

	@Test
	void shouldRejectUnknownArgument() {
		assertThrows(IllegalArgumentException.class, () -> ExperimentOptions.parse(new String[] { "--fresh" }));
		assertThrows(IllegalArgumentException.class, () -> ExperimentOptions.parse(new String[] { "--subjects" }));
	}

	private static ResultRow row(String project, String variant) {
		return new ResultRow(project, variant, 1, 0, 0, 0, 0, 0, 0.0, 0.0);
	}
}
