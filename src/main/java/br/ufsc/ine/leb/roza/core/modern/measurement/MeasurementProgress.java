package br.ufsc.ine.leb.roza.core.modern.measurement;

import br.ufsc.ine.leb.roza.core.modern.StageProgress;

final class MeasurementProgress {

	private MeasurementProgress() {
	}

	static void afterDirectedRow(StageProgress progress, int sourceIndex, int n) {
		if (n <= 1) {
			progress.report(1, 1);
			return;
		}
		progress.report((sourceIndex + 1) * (n - 1), n * (n - 1));
	}

	static void afterUpperTriangleRow(StageProgress progress, int sourceIndex, int n) {
		if (n <= 1) {
			progress.report(1, 1);
			return;
		}
		int completed = (sourceIndex + 1) * (2 * n - 2 - sourceIndex) / 2;
		progress.report(completed, n * (n - 1) / 2);
	}
}
