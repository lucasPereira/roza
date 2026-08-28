package br.ufsc.ine.leb.roza.expt.n;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ExperimentProgressTest {

	@Test
	void shouldCountAlreadyDoneAndFailedVariantsTowardTheTotal() {
		StringBuilder out = new StringBuilder();
		ExperimentProgress progress = new ExperimentProgress(1, 3, out);
		progress.beginSubject("javaparser", 10);
		progress.alreadyDone("implicit");
		progress.beginVariant("residual-implicit+delegated");
		progress.failVariant("java.lang.OutOfMemoryError: Java heap space");
		progress.skipUnrun("delegated+implicit", "prerequisite missing");
		assertEquals(100.0, progress.totalPercent(), 0.01);
	}
}
