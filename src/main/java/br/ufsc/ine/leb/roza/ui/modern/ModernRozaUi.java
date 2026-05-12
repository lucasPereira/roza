package br.ufsc.ine.leb.roza.ui.modern;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.clustering.AgglomerativeHierarchicalTestCaseClusterer;
import br.ufsc.ine.leb.roza.core.modern.clustering.ClusteringLevel;
import br.ufsc.ine.leb.roza.core.modern.clustering.CompositeMergeTieBreaker;
import br.ufsc.ine.leb.roza.core.modern.clustering.CompositeStopCriterion;
import br.ufsc.ine.leb.roza.core.modern.clustering.LinkageMethod;
import br.ufsc.ine.leb.roza.core.modern.clustering.MaxLevelStopCriterion;
import br.ufsc.ine.leb.roza.core.modern.clustering.MaxTestsPerClusterStopCriterion;
import br.ufsc.ine.leb.roza.core.modern.clustering.MergeCandidate;
import br.ufsc.ine.leb.roza.core.modern.clustering.MergeTieBreaker;
import br.ufsc.ine.leb.roza.core.modern.clustering.MergeTieBreakerKind;
import br.ufsc.ine.leb.roza.core.modern.clustering.MinimumSharedPrefixStopCriterion;
import br.ufsc.ine.leb.roza.core.modern.clustering.MinimumSimilarityStopCriterion;
import br.ufsc.ine.leb.roza.core.modern.clustering.StopCriterion;
import br.ufsc.ine.leb.roza.core.modern.clustering.TargetClusterCountStopCriterion;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseCluster;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseClusters;
import br.ufsc.ine.leb.roza.core.modern.decomposition.DefaultTestCaseDecomposer;
import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCaseDecomposer;
import br.ufsc.ine.leb.roza.core.modern.loading.CodeFile;
import br.ufsc.ine.leb.roza.core.modern.loading.FileSystemCodeFileLoader;
import br.ufsc.ine.leb.roza.core.modern.loading.LoadedCodeFiles;
import br.ufsc.ine.leb.roza.core.modern.measurement.DeckardMeasurementConfiguration;
import br.ufsc.ine.leb.roza.core.modern.measurement.DeckardTestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.JplagMeasurementConfiguration;
import br.ufsc.ine.leb.roza.core.modern.measurement.JplagTestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.LccssTestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.LcsTestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.SimianMeasurementConfiguration;
import br.ufsc.ine.leb.roza.core.modern.measurement.SimianTestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMatrix;
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.JunitTestClassParser;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsingException;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClassParser;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestCodeViolation;
import br.ufsc.ine.leb.roza.core.modern.parsing.UnsupportedFeatureException;
import br.ufsc.ine.leb.roza.core.modern.parsing.ViolationScope;
import javafx.beans.binding.Bindings;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

public final class ModernRozaUi extends Application {

	private static final String FONT_FAMILY = "-fx-font-family: 'Arial';";
	private static final String[] CLUSTER_BLOCK_STYLES = {
			"-fx-background-color: #c8e6c9; -fx-border-color: #2e7d32; -fx-border-width: 1; -fx-border-radius: 6; -fx-background-radius: 6;",
			"-fx-background-color: #ffcdd2; -fx-border-color: #c62828; -fx-border-width: 1; -fx-border-radius: 6; -fx-background-radius: 6;",
			"-fx-background-color: #bbdefb; -fx-border-color: #1565c0; -fx-border-width: 1; -fx-border-radius: 6; -fx-background-radius: 6;",
			"-fx-background-color: #fff9c4; -fx-border-color: #f9a825; -fx-border-width: 1; -fx-border-radius: 6; -fx-background-radius: 6;",
			"-fx-background-color: #ffe0b2; -fx-border-color: #e65100; -fx-border-width: 1; -fx-border-radius: 6; -fx-background-radius: 6;",
			"-fx-background-color: #e1bee7; -fx-border-color: #6a1b9a; -fx-border-width: 1; -fx-border-radius: 6; -fx-background-radius: 6;",
	};
	/** Appended to the palette style for the cluster produced by the level's accepted merge. */
	private static final String MERGED_CLUSTER_BLOCK_EMPHASIS =
			"-fx-border-width: 2; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 6, 0, 0, 1);";
	private static final int CONFIGURATION_INNER_SPACING = 10;
	private static final Insets MARGIN_AFTER_CONFIGURATION_GROUP = new Insets(0, 0, 12, 0);
	private static final Insets MARGIN_SECTION_TITLE_AFTER_GROUP = new Insets(4, 0, 0, 0);
	private static final Insets MARGIN_ACTION_BUTTON_TOP = new Insets(12, 0, 0, 0);
	/** Matches vertical gap above the stage action button (sidebar spacing + button top margin). */
	private static final int CONFIGURATION_SIDEBAR_SPACING = 14;
	private static final int CONFIGURATION_GROUP_VERTICAL_GAP =
			CONFIGURATION_SIDEBAR_SPACING + (int) MARGIN_ACTION_BUTTON_TOP.getTop();

	private final PipelineState pipelineState;
	private final HBox pipelineBar;
	private final VBox configurationSidebar;
	private final VBox contentArea;
	private final CheckBox recursiveLoading;
	private final CheckBox javaExtension;
	private final CheckBox txtExtension;
	private final ComboBox<String> metricCombo;
	private final TextField deckardMinTokensInput;
	private final TextField deckardStrideInput;
	private final TextField deckardSimilarityInput;
	private final TextField jplagSensitivityInput;
	private final TextField simianThresholdInput;
	private final ComboBox<TestCase> sourceTestCombo;
	private final ComboBox<TestCase> targetTestCombo;
	private final ComboBox<LinkageMethod> linkageMethodCombo;
	private final CheckBox minimumSimilarityStopEnabled;
	private final TextField minimumSimilarityStopInput;
	private final CheckBox maxTestsPerClusterStopEnabled;
	private final TextField maxTestsPerClusterStopInput;
	private final CheckBox maxLevelStopEnabled;
	private final TextField maxLevelStopInput;
	private final CheckBox targetClusterCountStopEnabled;
	private final TextField targetClusterCountStopInput;
	private final CheckBox minimumSharedPrefixStopEnabled;
	private final TextField minimumSharedPrefixStopInput;
	private final List<MergeTieBreakerKind> selectedTieBreakerKinds;
	private Path sourceFolder;
	private LoadedCodeFiles loadedCodeFiles;
	private CodeFile selectedCodeFile;
	private String loadingError;
	private ParsedTestClasses parsedTestClasses;
	private String parsingError;
	private int selectedViolationIndex;
	private DecomposedTestCases decomposedTestCases;
	private String decompositionError;
	private TestCase selectedDecomposedTestCase;
	private TestCaseSimilarityMatrix similarityMatrix;
	private String measurementError;
	private TestCaseClusters testCaseClusters;
	private List<ClusteringLevel> clusteringLevels;
	private String clusteringError;
	private int selectedRefactoringLevelIndex;
	private boolean rankedSimilarityDescending;
	private boolean suppressSimilaritySelectionRender;
	private boolean suppressSimilarityComboListener;
	private TextArea clusteringSourceCodeArea;
	private TextArea clusteringTargetCodeArea;

	public ModernRozaUi() {
		pipelineState = new PipelineState();
		pipelineBar = new HBox(8);
		configurationSidebar = new VBox(CONFIGURATION_SIDEBAR_SPACING);
		contentArea = new VBox(14);
		recursiveLoading = new CheckBox("Enabled");
		javaExtension = new CheckBox(".java");
		txtExtension = new CheckBox(".txt");
		recursiveLoading.setSelected(true);
		javaExtension.setSelected(true);
		txtExtension.setSelected(false);

		metricCombo = new ComboBox<>();
		metricCombo.getItems().add("LCCSS");
		metricCombo.getItems().add("LCS");
		metricCombo.getItems().add("Deckard");
		metricCombo.getItems().add("JPlag");
		metricCombo.getItems().add("Simian");
		metricCombo.getSelectionModel().selectFirst();
		metricCombo.setStyle(singleLineComboBoxStyle());
		metricCombo.valueProperty().addListener((observable, previous, selected) -> renderConfigurationSidebar());

		deckardMinTokensInput = metricConfigurationInput(String.valueOf(DeckardMeasurementConfiguration.DEFAULT_MIN_TOKENS));
		deckardStrideInput = metricConfigurationInput(String.valueOf(DeckardMeasurementConfiguration.DEFAULT_STRIDE));
		deckardSimilarityInput = metricConfigurationInput(String.valueOf(DeckardMeasurementConfiguration.DEFAULT_SIMILARITY));
		jplagSensitivityInput = metricConfigurationInput(String.valueOf(JplagMeasurementConfiguration.DEFAULT_SENSITIVITY));
		simianThresholdInput = metricConfigurationInput(String.valueOf(SimianMeasurementConfiguration.DEFAULT_THRESHOLD));

		sourceTestCombo = testCaseComboBox();
		targetTestCombo = testCaseComboBox();
		sourceTestCombo.valueProperty().addListener((observable, previous, selected) -> {
			if (suppressSimilarityComboListener) {
				return;
			}
			if (suppressSimilaritySelectionRender) {
				updateSelectedSimilarityCodeBlocks(selectedSimilaritySourceIndex(), selectedSimilarityTargetIndex());
			} else {
				renderContentArea();
			}
		});
		targetTestCombo.valueProperty().addListener((observable, previous, selected) -> {
			if (suppressSimilarityComboListener) {
				return;
			}
			if (suppressSimilaritySelectionRender) {
				updateSelectedSimilarityCodeBlocks(selectedSimilaritySourceIndex(), selectedSimilarityTargetIndex());
			} else {
				renderContentArea();
			}
		});

		linkageMethodCombo = linkageMethodComboBox();
		minimumSimilarityStopEnabled = new CheckBox("Minimum similarity");
		minimumSimilarityStopInput = metricConfigurationInput("0.0");
		maxTestsPerClusterStopEnabled = new CheckBox("Maximum tests per cluster");
		maxTestsPerClusterStopInput = metricConfigurationInput("10");
		maxLevelStopEnabled = new CheckBox("Maximum level");
		maxLevelStopInput = metricConfigurationInput("1");
		targetClusterCountStopEnabled = new CheckBox("Target cluster count");
		targetClusterCountStopInput = metricConfigurationInput("1");
		minimumSharedPrefixStopEnabled = new CheckBox("Minimum shared prefix threshold");
		minimumSharedPrefixStopInput = metricConfigurationInput("0");
		selectedTieBreakerKinds = new ArrayList<>();

		sourceFolder = defaultSourceFolder();
		selectedViolationIndex = -1;
		rankedSimilarityDescending = true;
	}

	private Path defaultSourceFolder() {
		Path fromRozaProject = Path.of("src", "test", "java").toAbsolutePath().normalize();
		if (Files.isDirectory(fromRozaProject)) {
			return fromRozaProject;
		}
		Path fromWorkspaceRoot = Path.of("roza", "src", "test", "java").toAbsolutePath().normalize();
		if (Files.isDirectory(fromWorkspaceRoot)) {
			return fromWorkspaceRoot;
		}
		return fromRozaProject;
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(javafx.stage.Stage stage) {
		BorderPane root = new BorderPane();
		root.setStyle(FONT_FAMILY + "-fx-background-color: #f4f6f8;");
		root.setTop(pipelineBar);
		root.setLeft(configurationSidebar);
		root.setCenter(contentArea);

		render();

		stage.setTitle("Modern Roza UI");
		stage.setScene(new Scene(root, 1100, 700));
		stage.setMaximized(true);
		stage.show();
	}

	private void render() {
		renderPipelineBar();
		renderConfigurationSidebar();
		renderContentArea();
	}

	private void renderPipelineBar() {
		pipelineBar.getChildren().clear();
		pipelineBar.setPadding(new Insets(16));
		pipelineBar.setAlignment(Pos.CENTER_LEFT);
		pipelineBar.setStyle(FONT_FAMILY + "-fx-background-color: #333333;");

		for (PipelineStage stage : pipelineState.stages()) {
			Button stageButton = new Button(stage.displayName());
			boolean selectable = pipelineState.canSelect(stage);
			stageButton.setMouseTransparent(!selectable);
			stageButton.setFocusTraversable(selectable);
			stageButton.setStyle(stageButtonStyle(pipelineState.status(stage), pipelineState.isSelected(stage)));
			stageButton.setOnAction(event -> {
				pipelineState.select(stage);
				render();
			});
			pipelineBar.getChildren().add(stageButton);
		}
	}

	private void renderConfigurationSidebar() {
		PipelineStage selectedStage = pipelineState.selectedStage();
		configurationSidebar.getChildren().clear();
		configurationSidebar.setPadding(new Insets(18));
		configurationSidebar.setPrefWidth(300);
		configurationSidebar.setStyle(FONT_FAMILY + "-fx-background-color: #edf1f5;");

		VBox configuration = configurationFor(selectedStage);

		if (selectedStage == PipelineStage.CLUSTERING) {
			configurationSidebar.getChildren().add(configuration);
		} else {
			Button actionButton = new Button(actionButtonText(selectedStage));
			actionButton.setMaxWidth(Double.MAX_VALUE);
			actionButton.setDisable(!stageActionEnabled(selectedStage));
			actionButton.setStyle(primaryButtonStyle());
			actionButton.setOnAction(event -> {
				runStage(selectedStage);
			});

			if (configuration.getChildren().isEmpty()) {
				configurationSidebar.getChildren().add(actionButton);
			} else {
				configurationSidebar.getChildren().addAll(configuration, actionButton);
				VBox.setMargin(actionButton, MARGIN_ACTION_BUTTON_TOP);
			}
		}
	}

	private VBox configurationFor(PipelineStage selectedStage) {
		if (selectedStage == PipelineStage.LOADING) {
			return loadingConfiguration();
		}
		if (selectedStage == PipelineStage.PARSING) {
			return parsingConfiguration();
		}
		if (selectedStage == PipelineStage.DECOMPOSITION) {
			return decompositionConfiguration();
		}
		if (selectedStage == PipelineStage.MEASUREMENT) {
			return measurementConfiguration();
		}
		if (selectedStage == PipelineStage.CLUSTERING) {
			return clusteringConfiguration();
		}
		if (selectedStage == PipelineStage.REFACTORING) {
			return refactoringConfiguration();
		}
		VBox configuration = new VBox(CONFIGURATION_INNER_SPACING);
		configuration.setPadding(new Insets(0, 0, 4, 0));
		for (String item : selectedStage.configurationItems()) {
			configuration.getChildren().add(configurationRow(item));
		}
		return configuration;
	}

	private VBox loadingConfiguration() {
		VBox configuration = new VBox(CONFIGURATION_INNER_SPACING);
		configuration.setPadding(new Insets(0, 0, 4, 0));
		Button sourceFolderButton = new Button("Source folder");
		sourceFolderButton.setMaxWidth(Double.MAX_VALUE);
		sourceFolderButton.setStyle(secondaryButtonStyle());
		sourceFolderButton.setOnAction(event -> chooseSourceFolder());

		Label selectedFolder = body(sourceFolderText());
		VBox.setMargin(selectedFolder, new Insets(4, 0, 12, 0));

		Label recursiveSectionTitle = body("Recursive loading");
		recursiveSectionTitle.setStyle(recursiveSectionTitle.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		VBox.setMargin(recursiveLoading, MARGIN_AFTER_CONFIGURATION_GROUP);

		Label acceptedExtensions = body("Accepted extensions");
		VBox.setMargin(acceptedExtensions, MARGIN_SECTION_TITLE_AFTER_GROUP);
		acceptedExtensions.setStyle(acceptedExtensions.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");

		configuration.getChildren().addAll(sourceFolderButton, selectedFolder, recursiveSectionTitle, recursiveLoading, acceptedExtensions,
				javaExtension, txtExtension);
		return configuration;
	}

	private VBox refactoringConfiguration() {
		VBox configuration = new VBox(CONFIGURATION_INNER_SPACING);
		configuration.setPadding(new Insets(0, 0, 4, 0));
		return configuration;
	}

	private VBox parsingConfiguration() {
		VBox configuration = new VBox(CONFIGURATION_INNER_SPACING);
		configuration.setPadding(new Insets(0, 0, 4, 0));
		return configuration;
	}

	private VBox decompositionConfiguration() {
		VBox configuration = new VBox(CONFIGURATION_INNER_SPACING);
		configuration.setPadding(new Insets(0, 0, 4, 0));
		return configuration;
	}

	private VBox measurementConfiguration() {
		VBox metricBlock = new VBox(CONFIGURATION_INNER_SPACING);
		Label metricTitle = body("Similarity metric");
		metricTitle.setStyle(metricTitle.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		metricCombo.setMaxWidth(Double.MAX_VALUE);
		metricBlock.getChildren().addAll(metricTitle, metricCombo);

		VBox configuration = new VBox(CONFIGURATION_GROUP_VERTICAL_GAP);
		configuration.setPadding(new Insets(0, 0, 4, 0));
		configuration.getChildren().add(metricBlock);
		if ("Deckard".equals(metricCombo.getSelectionModel().getSelectedItem())) {
			VBox deckardFields = new VBox(CONFIGURATION_GROUP_VERTICAL_GAP);
			deckardFields.getChildren().addAll(
					configurationInput("Minimum tokens", deckardMinTokensInput),
					configurationInput("Stride", deckardStrideInput),
					configurationInput("Similarity", deckardSimilarityInput));
			configuration.getChildren().add(deckardFields);
		}
		if ("JPlag".equals(metricCombo.getSelectionModel().getSelectedItem())) {
			VBox jplagFields = new VBox(CONFIGURATION_GROUP_VERTICAL_GAP);
			jplagFields.getChildren().add(configurationInput("Sensitivity", jplagSensitivityInput));
			configuration.getChildren().add(jplagFields);
		}
		if ("Simian".equals(metricCombo.getSelectionModel().getSelectedItem())) {
			VBox simianFields = new VBox(CONFIGURATION_GROUP_VERTICAL_GAP);
			simianFields.getChildren().add(configurationInput("Threshold", simianThresholdInput));
			configuration.getChildren().add(simianFields);
		}
		return configuration;
	}

	private VBox clusteringConfiguration() {
		VBox linkageBlock = new VBox(CONFIGURATION_INNER_SPACING);
		Label linkageTitle = body("Linkage method");
		linkageTitle.setStyle(linkageTitle.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		linkageMethodCombo.setMaxWidth(Double.MAX_VALUE);
		linkageBlock.getChildren().addAll(linkageTitle, linkageMethodCombo);

		VBox stopCriteriaBlock = new VBox(CONFIGURATION_INNER_SPACING);
		Label stopCriteriaTitle = body("Stop criteria");
		stopCriteriaTitle.setStyle(stopCriteriaTitle.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		stopCriteriaBlock.getChildren().addAll(
				stopCriteriaTitle,
				stopCriterionInput(minimumSimilarityStopEnabled, minimumSimilarityStopInput),
				stopCriterionInput(maxTestsPerClusterStopEnabled, maxTestsPerClusterStopInput),
				stopCriterionInput(maxLevelStopEnabled, maxLevelStopInput),
				stopCriterionInput(targetClusterCountStopEnabled, targetClusterCountStopInput),
				stopCriterionInput(minimumSharedPrefixStopEnabled, minimumSharedPrefixStopInput));

		VBox tieBreakersBlock = new VBox(CONFIGURATION_INNER_SPACING);
		Label tieBreakersTitle = body("Merge tie breakers");
		tieBreakersTitle.setStyle(tieBreakersTitle.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		tieBreakersBlock.getChildren().addAll(tieBreakersTitle, tieBreakerEditor());

		Button clusterButton = new Button(PipelineStage.CLUSTERING.actionLabel());
		clusterButton.setMaxWidth(Double.MAX_VALUE);
		clusterButton.setStyle(primaryButtonStyle());
		clusterButton.setDisable(!stageActionEnabled(PipelineStage.CLUSTERING));
		clusterButton.setOnAction(event -> runStage(PipelineStage.CLUSTERING));

		VBox configuration = new VBox(CONFIGURATION_GROUP_VERTICAL_GAP);
		configuration.setPadding(new Insets(0, 0, 4, 0));
		configuration.getChildren().addAll(linkageBlock, stopCriteriaBlock, tieBreakersBlock, clusterButton);
		VBox.setMargin(clusterButton, MARGIN_ACTION_BUTTON_TOP);
		return configuration;
	}

	private VBox stopCriterionInput(CheckBox enabled, TextField input) {
		VBox row = new VBox(4);
		input.setDisable(!enabled.isSelected());
		enabled.setOnAction(event -> input.setDisable(!enabled.isSelected()));
		row.getChildren().addAll(enabled, input);
		return row;
	}

	private VBox tieBreakerEditor() {
		VBox editor = new VBox(8);
		for (int index = 0; index < selectedTieBreakerKinds.size(); index++) {
			editor.getChildren().add(tieBreakerRow(index));
		}
		Button add = new Button("Add tie breaker");
		add.setStyle(secondaryButtonStyle());
		add.setMaxWidth(Double.MAX_VALUE);
		add.setOnAction(event -> {
			selectedTieBreakerKinds.add(MergeTieBreakerKind.STABLE_TEST_CASE_ORDER);
			renderConfigurationSidebar();
		});
		editor.getChildren().add(add);
		return editor;
	}

	private HBox tieBreakerRow(int index) {
		ComboBox<MergeTieBreakerKind> comboBox = mergeTieBreakerComboBox();
		comboBox.getSelectionModel().select(selectedTieBreakerKinds.get(index));
		comboBox.valueProperty().addListener((observable, previous, selected) -> selectedTieBreakerKinds.set(index, selected));
		comboBox.setMinWidth(0);
		comboBox.setMaxWidth(Double.MAX_VALUE);
		Button remove = new Button("Remove");
		remove.setStyle(secondaryButtonStyle());
		remove.setMinWidth(Region.USE_PREF_SIZE);
		remove.setOnAction(event -> {
			selectedTieBreakerKinds.remove(index);
			renderConfigurationSidebar();
		});
		HBox row = new HBox(8);
		row.getChildren().addAll(comboBox, remove);
		HBox.setHgrow(comboBox, Priority.ALWAYS);
		HBox.setHgrow(remove, Priority.NEVER);
		return row;
	}

	private ComboBox<TestCase> testCaseComboBox() {
		ComboBox<TestCase> comboBox = new ComboBox<>();
		comboBox.setStyle(singleLineComboBoxStyle());
		comboBox.setCellFactory(list -> new ListCell<>() {
			@Override
			protected void updateItem(TestCase item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.name());
			}
		});
		comboBox.setButtonCell(new ListCell<>() {
			@Override
			protected void updateItem(TestCase item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.name());
			}
		});
		return comboBox;
	}

	private ComboBox<LinkageMethod> linkageMethodComboBox() {
		ComboBox<LinkageMethod> comboBox = new ComboBox<>();
		comboBox.getItems().addAll(LinkageMethod.values());
		comboBox.getSelectionModel().select(LinkageMethod.SINGLE);
		comboBox.setStyle(singleLineComboBoxStyle());
		comboBox.setCellFactory(list -> new ListCell<>() {
			@Override
			protected void updateItem(LinkageMethod item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.displayName());
			}
		});
		comboBox.setButtonCell(new ListCell<>() {
			@Override
			protected void updateItem(LinkageMethod item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.displayName());
			}
		});
		return comboBox;
	}

	private ComboBox<MergeTieBreakerKind> mergeTieBreakerComboBox() {
		ComboBox<MergeTieBreakerKind> comboBox = new ComboBox<>();
		comboBox.getItems().addAll(MergeTieBreakerKind.values());
		comboBox.getSelectionModel().selectFirst();
		comboBox.setStyle(singleLineComboBoxStyle());
		comboBox.setCellFactory(list -> new ListCell<>() {
			@Override
			protected void updateItem(MergeTieBreakerKind item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.displayName());
			}
		});
		comboBox.setButtonCell(new ListCell<>() {
			@Override
			protected void updateItem(MergeTieBreakerKind item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.displayName());
			}
		});
		return comboBox;
	}

	private TextField metricConfigurationInput(String defaultValue) {
		TextField input = new TextField(defaultValue);
		input.setStyle(singleLineComboBoxStyle());
		input.setMaxWidth(Double.MAX_VALUE);
		return input;
	}

	private VBox configurationInput(String label, TextField input) {
		VBox row = new VBox(4);
		Label title = body(label);
		title.setStyle(title.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		row.getChildren().addAll(title, input);
		return row;
	}

	private void chooseSourceFolder() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Source folder");
		File selectedFolder = directoryChooser.showDialog(configurationSidebar.getScene().getWindow());
		if (selectedFolder != null) {
			sourceFolder = selectedFolder.toPath();
			loadingError = null;
			render();
		}
	}

	private String sourceFolderText() {
		if (sourceFolder == null) {
			return "No source folder selected.";
		}
		return sourceFolder.toString();
	}

	private boolean stageActionEnabled(PipelineStage selectedStage) {
		if (!pipelineState.selectedStageCanRun()) {
			return false;
		}
		if (selectedStage == PipelineStage.LOADING) {
			return sourceFolder != null;
		}
		if (selectedStage == PipelineStage.PARSING) {
			return loadedCodeFiles != null;
		}
		if (selectedStage == PipelineStage.DECOMPOSITION) {
			return parsedTestClasses != null;
		}
		if (selectedStage == PipelineStage.MEASUREMENT) {
			return decomposedTestCases != null;
		}
		if (selectedStage == PipelineStage.CLUSTERING) {
			return similarityMatrix != null;
		}
		return true;
	}

	private void runStage(PipelineStage selectedStage) {
		if (selectedStage == PipelineStage.LOADING) {
			loadCodeFiles();
		} else if (selectedStage == PipelineStage.PARSING) {
			runParsing();
		} else if (selectedStage == PipelineStage.DECOMPOSITION) {
			runDecomposition();
		} else if (selectedStage == PipelineStage.MEASUREMENT) {
			runMeasurement();
		} else if (selectedStage == PipelineStage.CLUSTERING) {
			runClustering();
		} else {
			pipelineState.runSelectedStage();
			render();
		}
	}

	private void loadCodeFiles() {
		try {
			loadedCodeFiles = new FileSystemCodeFileLoader(sourceFolder, recursiveLoading.isSelected(), acceptedExtensions()).load();
			selectedCodeFile = null;
			loadingError = null;
			clearParsingAndDecompositionResults();
			pipelineState.runSelectedStage();
		} catch (RuntimeException exception) {
			loadedCodeFiles = null;
			selectedCodeFile = null;
			loadingError = exception.getMessage();
			clearParsingAndDecompositionResults();
		}
		render();
	}

	private void clearParsingAndDecompositionResults() {
		parsedTestClasses = null;
		parsingError = null;
		selectedViolationIndex = -1;
		decomposedTestCases = null;
		decompositionError = null;
		selectedDecomposedTestCase = null;
		clearMeasurementResults();
	}

	private void clearMeasurementResults() {
		similarityMatrix = null;
		measurementError = null;
		sourceTestCombo.getItems().clear();
		targetTestCombo.getItems().clear();
		sourceTestCombo.getSelectionModel().clearSelection();
		targetTestCombo.getSelectionModel().clearSelection();
		selectedTieBreakerKinds.clear();
		clearClusteringResults();
	}

	private void clearClusteringResults() {
		testCaseClusters = null;
		clusteringLevels = null;
		clusteringError = null;
		selectedRefactoringLevelIndex = 0;
	}

	private static String failedParseCodeFileSource(Throwable exception) {
		for (Throwable t = exception; t != null; t = t.getCause()) {
			if (t instanceof ParsingException) {
				return ((ParsingException) t).codeFileSource();
			}
			if (t instanceof UnsupportedFeatureException) {
				return ((UnsupportedFeatureException) t).codeFileSource();
			}
		}
		return "";
	}

	private void selectCodeFileForParseFailure(RuntimeException exception) {
		if (loadedCodeFiles == null) {
			return;
		}
		String source = failedParseCodeFileSource(exception);
		if (source == null || source.isBlank()) {
			return;
		}
		for (CodeFile file : loadedCodeFiles.codeFiles()) {
			if (source.equals(file.source())) {
				selectedCodeFile = file;
				return;
			}
		}
	}

	private void selectFirstViolation() {
		if (parsedTestClasses == null || parsedTestClasses.violations().isEmpty()) {
			selectedViolationIndex = -1;
			return;
		}
		selectedViolationIndex = 0;
		selectCodeFileForViolation(selectedViolation());
	}

	private TestCodeViolation selectedViolation() {
		return parsedTestClasses.violations().get(selectedViolationIndex);
	}

	private void selectPreviousViolation() {
		if (selectedViolationIndex > 0) {
			selectedViolationIndex--;
			selectCodeFileForViolation(selectedViolation());
			renderContentArea();
		}
	}

	private void selectNextViolation() {
		if (parsedTestClasses != null && selectedViolationIndex < parsedTestClasses.violations().size() - 1) {
			selectedViolationIndex++;
			selectCodeFileForViolation(selectedViolation());
			renderContentArea();
		}
	}

	private void selectCodeFileForViolation(TestCodeViolation violation) {
		if (loadedCodeFiles == null) {
			return;
		}
		Pattern classDeclaration = Pattern.compile("\\bclass\\s+" + Pattern.quote(violation.testClassName()) + "\\b");
		for (CodeFile file : loadedCodeFiles.codeFiles()) {
			if (classDeclaration.matcher(file.content()).find()) {
				selectedCodeFile = file;
				return;
			}
		}
	}

	private void runParsing() {
		try {
			TestClassParser parser = new JunitTestClassParser();
			parsedTestClasses = parser.parse(loadedCodeFiles);
			selectFirstViolation();
			parsingError = null;
			decomposedTestCases = null;
			decompositionError = null;
			selectedDecomposedTestCase = null;
			clearMeasurementResults();
			pipelineState.runSelectedStage();
		} catch (RuntimeException exception) {
			parsingError = exception.getMessage() != null ? exception.getMessage() : exception.toString();
			selectCodeFileForParseFailure(exception);
			parsedTestClasses = null;
			selectedViolationIndex = -1;
			decomposedTestCases = null;
			decompositionError = null;
			selectedDecomposedTestCase = null;
			clearMeasurementResults();
		}
		render();
	}

	private void runDecomposition() {
		try {
			TestCaseDecomposer decomposer = new DefaultTestCaseDecomposer();
			decomposedTestCases = decomposer.decompose(parsedTestClasses);
			decompositionError = null;
			clearMeasurementResults();
			pipelineState.runSelectedStage();
		} catch (RuntimeException exception) {
			decompositionError = exception.getMessage() != null ? exception.getMessage() : exception.toString();
			decomposedTestCases = null;
			clearMeasurementResults();
		}
		render();
	}

	private void runMeasurement() {
		try {
			TestCaseSimilarityMeasurer measurer = selectedSimilarityMeasurer();
			similarityMatrix = measurer.measure(decomposedTestCases);
			measurementError = null;
			clearClusteringResults();
			refreshSimilaritySelectionControls();
			pipelineState.runSelectedStage();
		} catch (RuntimeException exception) {
			measurementError = exception.getMessage() != null ? exception.getMessage() : exception.toString();
			similarityMatrix = null;
			clearClusteringResults();
		}
		render();
	}

	private void runClustering() {
		try {
			AgglomerativeHierarchicalTestCaseClusterer clusterer = selectedClusterer();
			clusteringLevels = clusterer.generateLevels(similarityMatrix);
			testCaseClusters = new TestCaseClusters(clusteringLevels.get(clusteringLevels.size() - 1).clusters());
			clusteringError = null;
			pipelineState.runSelectedStage();
		} catch (RuntimeException exception) {
			testCaseClusters = null;
			clusteringLevels = null;
			clusteringError = exception.getMessage() != null ? exception.getMessage() : exception.toString();
		}
		render();
	}

	private AgglomerativeHierarchicalTestCaseClusterer selectedClusterer() {
		LinkageMethod linkageMethod = linkageMethodCombo.getSelectionModel().getSelectedItem();
		return new AgglomerativeHierarchicalTestCaseClusterer(
				linkageMethod.createLinkage(),
				new CompositeStopCriterion(stopCriteria()),
				new CompositeMergeTieBreaker(mergeTieBreakers()));
	}

	private TestCaseSimilarityMeasurer selectedSimilarityMeasurer() {
		if ("Deckard".equals(metricCombo.getSelectionModel().getSelectedItem())) {
			return new DeckardTestCaseSimilarityMeasurer(deckardConfiguration());
		}
		if ("JPlag".equals(metricCombo.getSelectionModel().getSelectedItem())) {
			return new JplagTestCaseSimilarityMeasurer(jplagConfiguration());
		}
		if ("Simian".equals(metricCombo.getSelectionModel().getSelectedItem())) {
			return new SimianTestCaseSimilarityMeasurer(simianConfiguration());
		}
		if ("LCS".equals(metricCombo.getSelectionModel().getSelectedItem())) {
			return new LcsTestCaseSimilarityMeasurer();
		}
		return new LccssTestCaseSimilarityMeasurer();
	}

	private DeckardMeasurementConfiguration deckardConfiguration() {
		try {
			return new DeckardMeasurementConfiguration(
					Integer.parseInt(deckardMinTokensInput.getText().trim()),
					Integer.parseInt(deckardStrideInput.getText().trim()),
					Double.parseDouble(deckardSimilarityInput.getText().trim()));
		} catch (NumberFormatException exception) {
			throw new IllegalArgumentException("Deckard configuration values must be numeric.", exception);
		}
	}

	private JplagMeasurementConfiguration jplagConfiguration() {
		try {
			return new JplagMeasurementConfiguration(Integer.parseInt(jplagSensitivityInput.getText().trim()));
		} catch (NumberFormatException exception) {
			throw new IllegalArgumentException("JPlag sensitivity must be numeric.", exception);
		}
	}

	private SimianMeasurementConfiguration simianConfiguration() {
		try {
			return new SimianMeasurementConfiguration(Integer.parseInt(simianThresholdInput.getText().trim()));
		} catch (NumberFormatException exception) {
			throw new IllegalArgumentException("Simian threshold must be numeric.", exception);
		}
	}

	private List<StopCriterion> stopCriteria() {
		try {
			List<StopCriterion> criteria = new ArrayList<>();
			if (minimumSimilarityStopEnabled.isSelected()) {
				criteria.add(new MinimumSimilarityStopCriterion(Double.parseDouble(minimumSimilarityStopInput.getText().trim())));
			}
			if (maxTestsPerClusterStopEnabled.isSelected()) {
				criteria.add(new MaxTestsPerClusterStopCriterion(Integer.parseInt(maxTestsPerClusterStopInput.getText().trim())));
			}
			if (maxLevelStopEnabled.isSelected()) {
				criteria.add(new MaxLevelStopCriterion(Integer.parseInt(maxLevelStopInput.getText().trim())));
			}
			if (targetClusterCountStopEnabled.isSelected()) {
				criteria.add(new TargetClusterCountStopCriterion(Integer.parseInt(targetClusterCountStopInput.getText().trim())));
			}
			if (minimumSharedPrefixStopEnabled.isSelected()) {
				criteria.add(new MinimumSharedPrefixStopCriterion(Integer.parseInt(minimumSharedPrefixStopInput.getText().trim())));
			}
			return criteria;
		} catch (NumberFormatException exception) {
			throw new IllegalArgumentException("Clustering stop criterion values must be numeric.", exception);
		}
	}

	private List<MergeTieBreaker> mergeTieBreakers() {
		return selectedTieBreakerKinds.stream()
				.filter(Objects::nonNull)
				.map(MergeTieBreakerKind::createTieBreaker)
				.collect(Collectors.toList());
	}

	private List<String> acceptedExtensions() {
		List<String> extensions = new ArrayList<>();
		if (javaExtension.isSelected()) {
			extensions.add(".java");
		}
		if (txtExtension.isSelected()) {
			extensions.add(".txt");
		}
		return extensions;
	}

	private void renderContentArea() {
		PipelineStage selectedStage = pipelineState.selectedStage();
		contentArea.getChildren().clear();
		contentArea.setPadding(new Insets(24));
		contentArea.setStyle(FONT_FAMILY + "-fx-background-color: #f4f6f8;");

		if (selectedStage == PipelineStage.LOADING && loadingError != null) {
			Label error = body("Loading failed: " + loadingError);
			error.setStyle(error.getStyle() + "-fx-text-fill: #991b1b;");
			contentArea.getChildren().add(error);
		} else if (selectedStage == PipelineStage.PARSING && loadedCodeFiles != null) {
			VBox parsingColumn = new VBox(12);
			if (parsingError != null) {
				Label error = body("Parsing failed: " + parsingError);
				error.setStyle(error.getStyle() + "-fx-text-fill: #991b1b;");
				parsingColumn.getChildren().add(error);
			}
			if (hasParsingViolations()) {
				parsingColumn.getChildren().add(violationNavigator());
			}
			parsingColumn.getChildren().add(loadedFilesView());
			contentArea.getChildren().add(parsingColumn);
			VBox.setVgrow(parsingColumn, Priority.ALWAYS);
		} else if (selectedStage == PipelineStage.DECOMPOSITION) {
			VBox decompositionColumn = new VBox(12);
			if (parsedTestClasses != null) {
				decompositionColumn.getChildren().add(decompositionSummary());
			}
			if (decompositionError != null) {
				Label error = body("Decomposition failed: " + decompositionError);
				error.setStyle(error.getStyle() + "-fx-text-fill: #991b1b;");
				decompositionColumn.getChildren().add(error);
			} else if (decomposedTestCases != null) {
				HBox decompositionRow = decomposedTestsView();
				decompositionColumn.getChildren().add(decompositionRow);
				VBox.setVgrow(decompositionRow, Priority.ALWAYS);
			}
			contentArea.getChildren().add(decompositionColumn);
			VBox.setVgrow(decompositionColumn, Priority.ALWAYS);
		} else if (selectedStage == PipelineStage.MEASUREMENT && decomposedTestCases != null) {
			VBox measurementColumn = new VBox(12);
			if (measurementError != null) {
				Label error = body("Measurement failed: " + measurementError);
				error.setStyle(error.getStyle() + "-fx-text-fill: #991b1b;");
				measurementColumn.getChildren().add(error);
			}
			HBox measurementInput = decomposedTestsView();
			measurementColumn.getChildren().add(measurementInput);
			contentArea.getChildren().add(measurementColumn);
			VBox.setVgrow(measurementInput, Priority.ALWAYS);
			VBox.setVgrow(measurementColumn, Priority.ALWAYS);
		} else if (selectedStage == PipelineStage.CLUSTERING && similarityMatrix != null) {
			VBox clusteringColumn = new VBox(12);
			if (clusteringError != null) {
				Label error = body("Clustering failed: " + clusteringError);
				error.setStyle(error.getStyle() + "-fx-text-fill: #991b1b;");
				clusteringColumn.getChildren().add(error);
			}
			VBox matrixView = similarityMatrixView();
			clusteringColumn.getChildren().add(matrixView);
			VBox.setVgrow(matrixView, Priority.ALWAYS);
			contentArea.getChildren().add(clusteringColumn);
			VBox.setVgrow(clusteringColumn, Priority.ALWAYS);
		} else if (selectedStage == PipelineStage.REFACTORING && clusteringLevels != null && !clusteringLevels.isEmpty()) {
			VBox refactoringColumn = new VBox(12);
			if (clusteringError != null) {
				Label error = body("Clustering failed: " + clusteringError);
				error.setStyle(error.getStyle() + "-fx-text-fill: #991b1b;");
				refactoringColumn.getChildren().add(error);
			}
			HBox levelsView = refactoringMergeLevelsView();
			refactoringColumn.getChildren().add(levelsView);
			VBox.setVgrow(levelsView, Priority.ALWAYS);
			contentArea.getChildren().add(refactoringColumn);
			VBox.setVgrow(refactoringColumn, Priority.ALWAYS);
		} else {
			contentArea.getChildren().add(body(selectedStage.previousStageDataDescription()));
		}
	}

	private VBox decompositionSummary() {
		VBox summary = new VBox(6);
		Label classViolations = body("Classes with class-level violations: " + classViolationCount());
		Label testViolations = body("Tests with method-level violations: " + methodLevelViolationTestCount());
		Label excludedTests = body("Tests excluded by violations: " + excludedTestCount());
		Label acceptedTests = body("Accepted tests: " + acceptedTestCount());
		summary.getChildren().addAll(classViolations, testViolations, excludedTests, acceptedTests);
		return summary;
	}

	private long classViolationCount() {
		return parsedTestClasses.violations().stream()
				.filter(violation -> violation.scope() == ViolationScope.TEST_CLASS)
				.map(TestCodeViolation::testClassName)
				.distinct()
				.count();
	}

	private long methodLevelViolationTestCount() {
		return parsedTestClasses.violations().stream()
				.filter(violation -> violation.scope() == ViolationScope.TEST_METHOD)
				.map(this::violationTestKey)
				.distinct()
				.count();
	}

	private long excludedTestCount() {
		Set<String> excludedTests = new HashSet<>();
		parsedTestClasses.violations().stream()
				.filter(violation -> violation.scope() == ViolationScope.TEST_METHOD)
				.map(this::violationTestKey)
				.forEach(excludedTests::add);
		Set<String> excludedClasses = parsedTestClasses.violations().stream()
				.filter(violation -> violation.scope() == ViolationScope.TEST_CLASS)
				.map(TestCodeViolation::testClassName)
				.collect(Collectors.toSet());
		parsedTestClasses.testClasses().stream()
				.filter(testClass -> excludedClasses.contains(testClass.name()))
				.flatMap(testClass -> testClass.testMethods().stream().map(testMethod -> testClass.name() + "." + testMethod.name()))
				.forEach(excludedTests::add);
		return excludedTests.size();
	}

	private long acceptedTestCount() {
		if (decomposedTestCases != null) {
			return decomposedTestCases.testCases().size();
		}
		Set<String> excludedClasses = parsedTestClasses.violations().stream()
				.filter(violation -> violation.scope() == ViolationScope.TEST_CLASS)
				.map(TestCodeViolation::testClassName)
				.collect(Collectors.toSet());
		Set<String> excludedTests = parsedTestClasses.violations().stream()
				.filter(violation -> violation.scope() == ViolationScope.TEST_METHOD)
				.map(this::violationTestKey)
				.collect(Collectors.toSet());
		return parsedTestClasses.testClasses().stream()
				.filter(testClass -> !excludedClasses.contains(testClass.name()))
				.flatMap(testClass -> testClass.testMethods()
						.stream()
						.filter(testMethod -> !excludedTests.contains(testClass.name() + "." + testMethod.name())))
				.count();
	}

	private String violationTestKey(TestCodeViolation violation) {
		return violation.testClassName() + "." + violation.testMethodName().orElse("");
	}

	private void refreshSimilaritySelectionControls() {
		suppressSimilarityComboListener = true;
		try {
			if (similarityMatrix == null) {
				sourceTestCombo.getItems().clear();
				targetTestCombo.getItems().clear();
				return;
			}
			List<TestCase> testCases = new ArrayList<>();
			for (int index = 0; index < similarityMatrix.size(); index++) {
				testCases.add(similarityMatrix.testCaseAt(index));
			}
			if (!sourceTestCombo.getItems().equals(testCases)) {
				sourceTestCombo.getItems().setAll(testCases);
				targetTestCombo.getItems().setAll(testCases);
			}
			if (similarityMatrix.size() > 0) {
				List<SimilarityRankingItem> ranking = rankingItems();
				boolean bothEmpty = sourceTestCombo.getSelectionModel().isEmpty() && targetTestCombo.getSelectionModel().isEmpty();
				if (bothEmpty) {
					if (!ranking.isEmpty()) {
						SimilarityRankingItem first = ranking.get(0);
						sourceTestCombo.getSelectionModel().select(first.sourceIndex());
						targetTestCombo.getSelectionModel().select(first.targetIndex());
					} else {
						sourceTestCombo.getSelectionModel().select(0);
						targetTestCombo.getSelectionModel().select(0);
					}
				} else {
					if (sourceTestCombo.getSelectionModel().isEmpty()) {
						sourceTestCombo.getSelectionModel().select(0);
					}
					if (targetTestCombo.getSelectionModel().isEmpty()) {
						targetTestCombo.getSelectionModel().select(0);
					}
				}
				if (similarityMatrix.size() >= 2 && !ranking.isEmpty()) {
					int si = sourceTestCombo.getSelectionModel().getSelectedIndex();
					int ti = targetTestCombo.getSelectionModel().getSelectedIndex();
					if (si >= 0 && ti >= 0 && si == ti) {
						SimilarityRankingItem first = ranking.get(0);
						sourceTestCombo.getSelectionModel().clearAndSelect(first.sourceIndex());
						targetTestCombo.getSelectionModel().clearAndSelect(first.targetIndex());
					}
				}
			}
		} finally {
			suppressSimilarityComboListener = false;
		}
	}

	private boolean hasParsingViolations() {
		return parsedTestClasses != null && !parsedTestClasses.violations().isEmpty() && selectedViolationIndex >= 0;
	}

	private VBox violationNavigator() {
		TestCodeViolation violation = selectedViolation();

		VBox box = new VBox(8);

		Label position = body("Violation " + (selectedViolationIndex + 1) + " of " + parsedTestClasses.violations().size());
		position.setStyle(position.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");

		Label description = body(violation.testClassName() + violation.testMethodName().map(method -> "." + method).orElse("")
				+ ": " + violation.description());

		Label snippet = body(violation.codeSnippet());
		snippet.setStyle(FONT_FAMILY + "-fx-font-family: 'Monospaced'; -fx-font-size: 13px; -fx-text-fill: #333333;");

		Button previous = new Button("Previous");
		previous.setDisable(selectedViolationIndex == 0);
		previous.setStyle(secondaryButtonStyle());
		previous.setOnAction(event -> selectPreviousViolation());

		Button next = new Button("Next");
		next.setDisable(selectedViolationIndex == parsedTestClasses.violations().size() - 1);
		next.setStyle(secondaryButtonStyle());
		next.setOnAction(event -> selectNextViolation());

		HBox controls = new HBox(8);
		controls.getChildren().addAll(previous, next);
		box.getChildren().addAll(position, description);
		if (!violation.codeSnippet().isBlank()) {
			box.getChildren().add(snippet);
		}
		box.getChildren().add(controls);
		return box;
	}

	private HBox loadedFilesView() {
		ListView<CodeFile> fileList = new ListView<>();
		fileList.getItems().addAll(loadedCodeFiles.codeFiles());
		fileList.setCellFactory(list -> new ListCell<>() {
			@Override
			protected void updateItem(CodeFile item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.source());
			}
		});
		fileList.setPrefWidth(320);
		if (selectedCodeFile != null) {
			fileList.getSelectionModel().select(selectedCodeFile);
		}
		fileList.getSelectionModel().selectedItemProperty().addListener((observable, previous, selected) -> {
			selectedCodeFile = selected;
			renderContentArea();
		});

		TextArea fileContent = new TextArea(selectedCodeFile == null ? "Select a loaded file to inspect its content." : selectedCodeFile.content());
		fileContent.setEditable(false);
		fileContent.setWrapText(false);
		fileContent.setStyle(FONT_FAMILY + "-fx-font-family: 'Monospaced'; -fx-font-size: 13px;");
		HBox.setHgrow(fileContent, Priority.ALWAYS);

		HBox loadedFiles = new HBox(16);
		loadedFiles.getChildren().addAll(fileList, fileContent);
		VBox.setVgrow(loadedFiles, Priority.ALWAYS);
		return loadedFiles;
	}

	private HBox decomposedTestsView() {
		ListView<TestCase> testList = new ListView<>();
		testList.getItems().addAll(decomposedTestCases.testCases());
		testList.setCellFactory(list -> new ListCell<>() {
			@Override
			protected void updateItem(TestCase item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.name());
			}
		});
		testList.setPrefWidth(320);
		if (selectedDecomposedTestCase != null) {
			testList.getSelectionModel().select(selectedDecomposedTestCase);
		}
		testList.getSelectionModel().selectedItemProperty().addListener((observable, previous, selected) -> {
			selectedDecomposedTestCase = selected;
			renderContentArea();
		});

		String bodyText = selectedDecomposedTestCase == null ? "Select a test to inspect its decomposed body." : formatDecomposedTestBody(selectedDecomposedTestCase);
		TextArea bodyArea = new TextArea(bodyText);
		bodyArea.setEditable(false);
		bodyArea.setWrapText(false);
		bodyArea.setStyle(FONT_FAMILY + "-fx-font-family: 'Monospaced'; -fx-font-size: 13px;");
		HBox.setHgrow(bodyArea, Priority.ALWAYS);

		HBox row = new HBox(16);
		row.getChildren().addAll(testList, bodyArea);
		VBox.setVgrow(row, Priority.ALWAYS);
		return row;
	}

	private VBox similarityMatrixView() {
		refreshSimilaritySelectionControls();
		VBox matrixView = new VBox(12);
		if (similarityMatrix.size() == 0) {
			matrixView.getChildren().add(similaritySelectionControls());
			return matrixView;
		}
		HBox rankingAndCode = new HBox(16);
		VBox ranking = rankedSimilarityList();

		VBox sourceColumn = new VBox(6);
		Label sourceTitle = body("Source test");
		sourceTitle.setStyle(sourceTitle.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		sourceTestCombo.setPrefWidth(320);
		sourceTestCombo.setMaxWidth(Double.MAX_VALUE);
		TextArea sourceCode = clusteringCodeTextArea(sourceTestCombo.getSelectionModel().getSelectedItem(), true);
		sourceColumn.getChildren().addAll(sourceTitle, sourceTestCombo, sourceCode);
		VBox.setVgrow(sourceCode, Priority.ALWAYS);

		VBox targetColumn = new VBox(6);
		Label targetTitle = body("Target test");
		targetTitle.setStyle(targetTitle.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		targetTestCombo.setPrefWidth(320);
		targetTestCombo.setMaxWidth(Double.MAX_VALUE);
		TextArea targetCode = clusteringCodeTextArea(targetTestCombo.getSelectionModel().getSelectedItem(), false);
		targetColumn.getChildren().addAll(targetTitle, targetTestCombo, targetCode);
		VBox.setVgrow(targetCode, Priority.ALWAYS);

		rankingAndCode.getChildren().addAll(ranking, sourceColumn, targetColumn);
		HBox.setHgrow(sourceColumn, Priority.ALWAYS);
		HBox.setHgrow(targetColumn, Priority.ALWAYS);
		matrixView.getChildren().add(rankingAndCode);
		VBox.setVgrow(rankingAndCode, Priority.ALWAYS);
		return matrixView;
	}

	private HBox refactoringMergeLevelsView() {
		HBox row = new HBox(16);
		ListView<Integer> levelList = new ListView<>();
		for (int index = 0; index < clusteringLevels.size(); index++) {
			levelList.getItems().add(index);
		}
		levelList.setPrefWidth(72);
		levelList.setMinWidth(Region.USE_PREF_SIZE);
		levelList.setCellFactory(items -> new ListCell<>() {
			@Override
			protected void updateItem(Integer item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : Integer.toString(item + 1));
			}
		});
		int lastIndex = clusteringLevels.size() - 1;
		if (selectedRefactoringLevelIndex < 0 || selectedRefactoringLevelIndex > lastIndex) {
			selectedRefactoringLevelIndex = 0;
		}
		levelList.getSelectionModel().select(selectedRefactoringLevelIndex);
		VBox levelColumn = new VBox(6);
		Label levelTitle = body("Level");
		levelTitle.setStyle(levelTitle.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		levelColumn.getChildren().addAll(levelTitle, levelList);
		VBox.setVgrow(levelList, Priority.ALWAYS);
		ScrollPane clustersPane = new ScrollPane();
		clustersPane.setContent(refactoringLevelClustersPanel(selectedRefactoringLevelIndex, clustersPane));
		clustersPane.setFitToWidth(true);
		clustersPane.setStyle(
				"-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
		levelList.getSelectionModel().selectedIndexProperty().addListener((observable, previous, next) -> {
			if (next == null || next.intValue() < 0 || next.intValue() == selectedRefactoringLevelIndex) {
				return;
			}
			selectedRefactoringLevelIndex = next.intValue();
			clustersPane.setContent(refactoringLevelClustersPanel(selectedRefactoringLevelIndex, clustersPane));
		});
		HBox.setHgrow(clustersPane, Priority.ALWAYS);
		row.getChildren().addAll(levelColumn, clustersPane);
		row.setFillHeight(true);
		return row;
	}

	private VBox refactoringLevelClustersPanel(int levelIndex, ScrollPane clustersScroll) {
		ClusteringLevel level = clusteringLevels.get(levelIndex);
		VBox panel = new VBox(8);
		// FlowPane column count is not fixed: prefWrapLength tracks the scroll viewport width.
		// Each cluster tile uses pref/max width 380 with hgap 8, so columns ≈ floor((wrap + 8) / (380 + 8))
		// (e.g. wrap ~800px → two columns). Fallback 280 is only when viewport width is not yet known.
		var usableWrapWidth = Bindings.createDoubleBinding(
				() -> {
					double w = clustersScroll.getViewportBounds().getWidth();
					if (!Double.isFinite(w) || w <= 0) {
						return 280.0;
					}
					return Math.max(120.0, w - 4.0);
				},
				clustersScroll.viewportBoundsProperty(),
				clustersScroll.widthProperty());
		panel.prefWidthProperty().bind(usableWrapWidth);
		panel.maxWidthProperty().bind(usableWrapWidth);
		Label title = body("Clusters");
		title.setStyle(title.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		title.setMaxWidth(Double.MAX_VALUE);
		panel.getChildren().add(title);
		FlowPane clusterTiles = new FlowPane(Orientation.HORIZONTAL, 8, 8);
		clusterTiles.setRowValignment(VPos.TOP);
		clusterTiles.prefWrapLengthProperty().bind(usableWrapWidth);
		clusterTiles.maxWidthProperty().bind(usableWrapWidth);
		List<TestCaseCluster> clusters = level.clusters();
		Optional<MergeCandidate> merge = levelIndex > 0 ? level.acceptedMerge() : Optional.empty();
		int mergedClusterIndex = -1;
		if (merge.isPresent()) {
			List<Integer> mergedKeys = merge.get().mergedCluster().testCaseIndexes();
			for (int i = 0; i < clusters.size(); i++) {
				if (clusters.get(i).testCaseIndexes().equals(mergedKeys)) {
					mergedClusterIndex = i;
					break;
				}
			}
		}
		List<TestCaseCluster> orderedClusters = new ArrayList<>(clusters.size());
		if (mergedClusterIndex >= 0) {
			orderedClusters.add(clusters.get(mergedClusterIndex));
			for (int i = 0; i < clusters.size(); i++) {
				if (i != mergedClusterIndex) {
					orderedClusters.add(clusters.get(i));
				}
			}
		} else {
			orderedClusters.addAll(clusters);
		}
		for (int i = 0; i < orderedClusters.size(); i++) {
			boolean emphasizeMerge = mergedClusterIndex >= 0 && i == 0;
			TestCaseCluster tileCluster = orderedClusters.get(i);
			Optional<Double> mergeStepSimilarity = merge.map(MergeCandidate::similarity);
			clusterTiles.getChildren().add(clusterBlockView(tileCluster, i, emphasizeMerge, mergeStepSimilarity));
		}
		panel.getChildren().add(clusterTiles);
		return panel;
	}

	private VBox clusterBlockView(TestCaseCluster cluster, int paletteIndex, boolean mergeEmphasis, Optional<Double> mergeStepSimilarity) {
		VBox block = new VBox(4);
		block.setAlignment(Pos.TOP_LEFT);
		block.setMaxHeight(Region.USE_PREF_SIZE);
		block.setPadding(new Insets(8, 10, 8, 10));
		block.setPrefWidth(Region.USE_COMPUTED_SIZE);
		block.setMinWidth(Region.USE_PREF_SIZE);
		block.setMaxWidth(Region.USE_PREF_SIZE);
		String palette = CLUSTER_BLOCK_STYLES[paletteIndex % CLUSTER_BLOCK_STYLES.length];
		String emphasis = mergeEmphasis ? MERGED_CLUSTER_BLOCK_EMPHASIS : "";
		block.setStyle(FONT_FAMILY + palette + emphasis);
		mergeStepSimilarity.ifPresent(similarity -> {
			Label similarityLine = body("Similarity: " + formatSimilarity(similarity));
			similarityLine.setMaxWidth(Region.USE_PREF_SIZE);
			similarityLine.setStyle(similarityLine.getStyle() + "-fx-font-size: 11px; -fx-text-fill: #6b7280;");
			block.getChildren().add(similarityLine);
		});
		for (TestCase testCase : cluster.testCases()) {
			Label line = body(testCase.name());
			line.setWrapText(true);
			line.setMaxWidth(Region.USE_PREF_SIZE);
			line.setStyle(line.getStyle() + "-fx-text-fill: #212121;");
			block.getChildren().add(line);
		}
		return block;
	}

	private HBox similaritySelectionControls() {
		HBox controls = new HBox(16);

		VBox source = new VBox(6);
		Label sourceTitle = body("Source test");
		sourceTitle.setStyle(sourceTitle.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		sourceTestCombo.setPrefWidth(320);
		sourceTestCombo.setMaxWidth(Double.MAX_VALUE);
		source.getChildren().addAll(sourceTitle, sourceTestCombo);
		HBox.setHgrow(source, Priority.ALWAYS);

		VBox target = new VBox(6);
		Label targetTitle = body("Target test");
		targetTitle.setStyle(targetTitle.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		targetTestCombo.setPrefWidth(320);
		targetTestCombo.setMaxWidth(Double.MAX_VALUE);
		target.getChildren().addAll(targetTitle, targetTestCombo);
		HBox.setHgrow(target, Priority.ALWAYS);

		controls.getChildren().addAll(source, target);
		return controls;
	}

	private int selectedSimilaritySourceIndex() {
		int index = sourceTestCombo.getSelectionModel().getSelectedIndex();
		return index >= 0 ? index : 0;
	}

	private int selectedSimilarityTargetIndex() {
		int index = targetTestCombo.getSelectionModel().getSelectedIndex();
		return index >= 0 ? index : 0;
	}

	private VBox rankedSimilarityList() {
		VBox ranking = new VBox(8);
		ranking.setPrefWidth(90);
		Button order = new Button(rankedSimilarityDescending ? "Highest" : "Lowest");
		order.setStyle(secondaryButtonStyle());
		order.setMaxWidth(Double.MAX_VALUE);
		order.setOnAction(event -> {
			rankedSimilarityDescending = !rankedSimilarityDescending;
			renderContentArea();
		});

		ListView<SimilarityRankingItem> list = new ListView<>();
		list.getItems().setAll(rankingItems());
		list.setCellFactory(items -> new ListCell<>() {
			@Override
			protected void updateItem(SimilarityRankingItem item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.label());
			}
		});
		int selectedTargetIndex = selectedSimilarityTargetIndex();
		int selectedSourceIndex = selectedSimilaritySourceIndex();
		int rankingIndex = -1;
		List<SimilarityRankingItem> items = list.getItems();
		for (int index = 0; index < items.size(); index++) {
			SimilarityRankingItem item = items.get(index);
			if (item.sourceIndex() == selectedSourceIndex && item.targetIndex() == selectedTargetIndex) {
				rankingIndex = index;
				break;
			}
		}
		if (rankingIndex >= 0) {
			list.getSelectionModel().select(rankingIndex);
			final int scrollToIndex = rankingIndex;
			Platform.runLater(() -> list.scrollTo(scrollToIndex));
		}
		list.getSelectionModel().selectedItemProperty().addListener((observable, previous, selected) -> {
			if (selected == null) {
				return;
			}
			int sourceIndex = selected.sourceIndex();
			int targetIndex = selected.targetIndex();
			suppressSimilaritySelectionRender = true;
			try {
				sourceTestCombo.getSelectionModel().clearAndSelect(sourceIndex);
				targetTestCombo.getSelectionModel().clearAndSelect(targetIndex);
			} finally {
				suppressSimilaritySelectionRender = false;
			}
			updateSelectedSimilarityCodeBlocks(sourceIndex, targetIndex);
		});
		ranking.getChildren().addAll(order, list);
		VBox.setVgrow(list, Priority.ALWAYS);
		return ranking;
	}

	private List<SimilarityRankingItem> rankingItems() {
		List<SimilarityRankingItem> items = new ArrayList<>();
		for (int source = 0; source < similarityMatrix.size(); source++) {
			for (int target = 0; target < similarityMatrix.size(); target++) {
				if (source != target) {
					items.add(new SimilarityRankingItem(
							source,
							target,
							similarityMatrix.testCaseAt(source),
							similarityMatrix.testCaseAt(target),
							similarityMatrix.similarity(source, target)));
				}
			}
		}
		Comparator<SimilarityRankingItem> comparator = Comparator.comparingDouble(SimilarityRankingItem::similarity)
				.thenComparing(item -> item.sourceTestCase().name())
				.thenComparing(item -> item.targetTestCase().name());
		if (rankedSimilarityDescending) {
			comparator = comparator.reversed();
		}
		items.sort(comparator);
		return items;
	}

	private TextArea clusteringCodeTextArea(TestCase testCase, boolean sourcePanel) {
		TextArea code = new TextArea(testCase == null ? "" : formatDecomposedTestBody(testCase));
		code.setEditable(false);
		code.setWrapText(false);
		code.setStyle(FONT_FAMILY + "-fx-font-family: 'Monospaced'; -fx-font-size: 13px;");
		if (sourcePanel) {
			clusteringSourceCodeArea = code;
		} else {
			clusteringTargetCodeArea = code;
		}
		return code;
	}

	private void updateSelectedSimilarityCodeBlocks(int sourceIndex, int targetIndex) {
		if (similarityMatrix == null || similarityMatrix.size() == 0) {
			return;
		}
		if (sourceIndex < 0 || targetIndex < 0 || sourceIndex >= similarityMatrix.size() || targetIndex >= similarityMatrix.size()) {
			return;
		}
		TestCase source = similarityMatrix.testCaseAt(sourceIndex);
		TestCase target = similarityMatrix.testCaseAt(targetIndex);
		if (clusteringSourceCodeArea != null) {
			clusteringSourceCodeArea.setText(formatDecomposedTestBody(source));
		}
		if (clusteringTargetCodeArea != null) {
			clusteringTargetCodeArea.setText(formatDecomposedTestBody(target));
		}
	}

	private static String formatDecomposedTestBody(TestCase testCase) {
		return testCase.body().statements().stream().map(CodeStatement::originalText).collect(Collectors.joining("\n"));
	}

	private static String formatSimilarity(double similarity) {
		return String.format(Locale.ROOT, "%.4f", similarity);
	}

	private String actionButtonText(PipelineStage selectedStage) {
		return selectedStage.actionLabel();
	}

	private Label configurationRow(String text) {
		Label label = body(text);
		label.setStyle(label.getStyle() + "-fx-background-color: #f3f6f9; -fx-padding: 8; -fx-background-radius: 4;");
		label.setMaxWidth(Double.MAX_VALUE);
		return label;
	}

	private Label body(String text) {
		Label label = new Label(text);
		label.setWrapText(true);
		label.setMaxWidth(Double.MAX_VALUE);
		label.setTextOverrun(OverrunStyle.CLIP);
		label.setStyle(FONT_FAMILY + "-fx-font-size: 14px; -fx-text-fill: #4b5563;");
		return label;
	}

	private String stageButtonStyle(PipelineStageStatus status, boolean selected) {
		if (selected && status == PipelineStageStatus.COMPLETED) {
			return topButtonStyle("#059669", "#ffffff");
		}
		if (selected && status == PipelineStageStatus.CURRENT) {
			return topButtonStyle("#2563eb", "#ffffff");
		}
		if (status == PipelineStageStatus.COMPLETED) {
			return topButtonStyle("#d1fae5", "#065f46");
		}
		if (status == PipelineStageStatus.CURRENT) {
			return topButtonStyle("#dbeafe", "#1d4ed8");
		}
		return topButtonStyle("#eeeeee", "#555555");
	}

	private String topButtonStyle(String background, String text) {
		return FONT_FAMILY + "-fx-background-color: " + background + "; -fx-text-fill: " + text
				+ "; -fx-background-radius: 18; -fx-padding: 8 14 8 14; -fx-font-weight: bold;";
	}

	private String primaryButtonStyle() {
		return FONT_FAMILY + "-fx-background-color: #333333; -fx-text-fill: white; -fx-background-radius: 6; -fx-padding: 10; -fx-font-weight: bold;";
	}

	private String singleLineComboBoxStyle() {
		return FONT_FAMILY + "-fx-min-height: 30px; -fx-pref-height: 30px; -fx-max-height: 30px; -fx-padding: 2 8 2 8;";
	}

	private String secondaryButtonStyle() {
		return FONT_FAMILY + "-fx-background-color: #333333; -fx-text-fill: #ffffff; -fx-background-radius: 6; -fx-padding: 10; -fx-font-weight: bold;";
	}

	private static final class SimilarityRankingItem {

		private final int sourceIndex;
		private final int targetIndex;
		private final TestCase sourceTestCase;
		private final TestCase targetTestCase;
		private final double similarity;

		private SimilarityRankingItem(int sourceIndex, int targetIndex, TestCase sourceTestCase, TestCase targetTestCase, double similarity) {
			this.sourceIndex = sourceIndex;
			this.targetIndex = targetIndex;
			this.sourceTestCase = sourceTestCase;
			this.targetTestCase = targetTestCase;
			this.similarity = similarity;
		}

		private int sourceIndex() {
			return sourceIndex;
		}

		private int targetIndex() {
			return targetIndex;
		}

		private TestCase sourceTestCase() {
			return sourceTestCase;
		}

		private TestCase targetTestCase() {
			return targetTestCase;
		}

		private double similarity() {
			return similarity;
		}

		private String label() {
			return formatSimilarity(similarity);
		}
	}
}
