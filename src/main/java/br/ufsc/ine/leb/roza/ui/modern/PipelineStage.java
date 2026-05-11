package br.ufsc.ine.leb.roza.ui.modern;

import java.util.List;

enum PipelineStage {
	LOADING(
			"Loading",
			"Load",
			List.of("Source folder", "Recursive loading", "Accepted extensions"),
			"No previous data. Configure how source files will be loaded."),
	PARSING(
			"Parsing",
			"Parse",
			List.of("Parser implementation", "Unsupported feature policy"),
			"Loaded files from the loading stage will be shown here."),
	DECOMPOSITION(
			"Decomposition",
			"Decompose",
			List.of("Decomposer implementation"),
			"Parsed test classes from the parsing stage will be shown here."),
	MEASUREMENT(
			"Measurement",
			"Measure",
			List.of("Similarity measurer", "Assertion handling"),
			"Decomposed test cases from the decomposition stage will be shown here."),
	CLUSTERING(
			"Clustering",
			"Cluster",
			List.of("Clusterer implementation", "Threshold"),
			"The similarity matrix from the measurement stage will be shown here."),
	REFACTORING(
			"Refactoring",
			"Refactor",
			List.of("Refactoring strategy"),
			"Test case clusters from the clustering stage will be shown here."),
	WRITING(
			"Writing",
			"Write",
			List.of("Writer implementation", "Output folder"),
			"Refactored test classes from the refactoring stage will be shown here.");

	private final String displayName;
	private final String actionLabel;
	private final List<String> configurationItems;
	private final String previousStageDataDescription;

	PipelineStage(String displayName, String actionLabel, List<String> configurationItems, String previousStageDataDescription) {
		this.displayName = displayName;
		this.actionLabel = actionLabel;
		this.configurationItems = configurationItems;
		this.previousStageDataDescription = previousStageDataDescription;
	}

	String displayName() {
		return displayName;
	}

	String actionLabel() {
		return actionLabel;
	}

	List<String> configurationItems() {
		return configurationItems;
	}

	String previousStageDataDescription() {
		return previousStageDataDescription;
	}
}
