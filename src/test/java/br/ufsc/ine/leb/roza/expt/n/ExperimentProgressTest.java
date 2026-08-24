package br.ufsc.ine.leb.roza.expt.n;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.StageProgress;

class ExperimentProgressTest {

	@Test
	void totalPercentCountsFinishedVariantsAndTheCurrentStage() {
		ExperimentProgress progress = new ExperimentProgress(22, 7, null);
		finishVariants(progress, "roza", 7);
		finishVariants(progress, "commons-csv", 7);
		progress.beginSubject("commons-lang", 4683);
		progress.beginVariant("implicit");
		progress.finishVariant();
		progress.beginVariant("residual-implicit");
		progress.finishVariant();
		progress.beginVariant("delegated");
		StageProgress measure = progress.stage("measure");
		measure.report(0, 100);

		assertEquals(10.4, Math.round(progress.totalPercent() * 10.0) / 10.0);
		assertEquals(0.0, progress.variantPercent());

		measure.report(50, 100);

		assertEquals(16.7, Math.round(progress.variantPercent() * 10.0) / 10.0);
		assertEquals(10.5, Math.round(progress.totalPercent() * 10.0) / 10.0);
	}

	private void finishVariants(ExperimentProgress progress, String project, int count) {
		progress.beginSubject(project, 1);
		for (int index = 0; index < count; index++) {
			progress.beginVariant("v" + index);
			progress.finishVariant();
		}
	}
}
