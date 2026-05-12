package br.ufsc.ine.leb.roza.ui.modern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PipelineStateTest {

	@Test
	void shouldExposeAnalyticsOnlyAfterWritingCompletes() {
		PipelineState state = new PipelineState();

		assertFalse(state.canSelect(PipelineStage.ANALYTICS));

		runThrough(PipelineStage.WRITING, state);

		assertTrue(state.canSelect(PipelineStage.ANALYTICS));
	}

	@Test
	void shouldHideAnalyticsAgainWhenPipelineIsResetByEarlierStage() {
		PipelineState state = new PipelineState();
		runThrough(PipelineStage.WRITING, state);
		state.select(PipelineStage.LOADING);
		state.runSelectedStage();

		assertFalse(state.canSelect(PipelineStage.ANALYTICS));
	}

	private void runThrough(PipelineStage stage, PipelineState state) {
		while (!state.selectedStage().equals(stage)) {
			state.runSelectedStage();
		}
		state.runSelectedStage();
	}
}
