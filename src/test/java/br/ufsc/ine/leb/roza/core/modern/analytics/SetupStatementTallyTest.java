package br.ufsc.ine.leb.roza.core.modern.analytics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class SetupStatementTallyTest {

	@Test
	void shouldCountExtrasAsDuplicationAndUndoThemOnRemove() {
		SetupStatementTally tally = new SetupStatementTally();

		tally.add(List.of("a();", "a();", "b();"));
		assertEquals(1, tally.duplicatedStatements());
		assertEquals(3, tally.totalStatements());

		tally.remove(List.of("a();"));
		assertEquals(0, tally.duplicatedStatements());
		assertEquals(2, tally.totalStatements());
	}
}
