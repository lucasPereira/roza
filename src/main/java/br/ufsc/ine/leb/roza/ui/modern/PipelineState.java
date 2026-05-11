package br.ufsc.ine.leb.roza.ui.modern;

import java.util.List;

final class PipelineState {

	private final List<PipelineStage> stages;
	private int currentStageIndex;
	private int selectedStageIndex;

	PipelineState() {
		stages = List.of(PipelineStage.values());
		currentStageIndex = 0;
		selectedStageIndex = 0;
	}

	List<PipelineStage> stages() {
		return stages;
	}

	PipelineStage selectedStage() {
		return stages.get(selectedStageIndex);
	}

	PipelineStageStatus status(PipelineStage stage) {
		int index = stages.indexOf(stage);
		if (index < currentStageIndex) {
			return PipelineStageStatus.COMPLETED;
		}
		if (index == currentStageIndex && currentStageIndex < stages.size()) {
			return PipelineStageStatus.CURRENT;
		}
		return PipelineStageStatus.BLOCKED;
	}

	boolean canSelect(PipelineStage stage) {
		return stages.indexOf(stage) <= lastSelectableStageIndex();
	}

	boolean isSelected(PipelineStage stage) {
		return stages.indexOf(stage) == selectedStageIndex;
	}

	boolean selectedStageCanRun() {
		return selectedStageIndex <= currentStageIndex;
	}

	void select(PipelineStage stage) {
		if (canSelect(stage)) {
			selectedStageIndex = stages.indexOf(stage);
		}
	}

	void runSelectedStage() {
		if (selectedStageCanRun()) {
			currentStageIndex = selectedStageIndex + 1;
			selectedStageIndex = lastSelectableStageIndex();
		}
	}

	private int lastSelectableStageIndex() {
		return Math.min(currentStageIndex, stages.size() - 1);
	}
}
