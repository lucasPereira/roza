package br.ufsc.ine.leb.roza.ui.modern;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.analytics.DefaultTestCodeAnalytics;
import br.ufsc.ine.leb.roza.core.modern.analytics.OriginalTestCodeMetrics;
import br.ufsc.ine.leb.roza.core.modern.analytics.TestClassMetrics;
import br.ufsc.ine.leb.roza.core.modern.analytics.TestClassMetricsCalculator;
import br.ufsc.ine.leb.roza.core.modern.analytics.TestCodeAnalytics;
import br.ufsc.ine.leb.roza.core.modern.analytics.TestCodeAnalyticsReport;
import br.ufsc.ine.leb.roza.core.modern.analytics.TestCodeMetricComparison;
import br.ufsc.ine.leb.roza.core.modern.clustering.AgglomerativeHierarchicalTestCaseClusterer;
import br.ufsc.ine.leb.roza.core.modern.clustering.ClusteringLevel;
import br.ufsc.ine.leb.roza.core.modern.clustering.CompositeMergeTieBreaker;
import br.ufsc.ine.leb.roza.core.modern.clustering.CompositeStopCriterion;
import br.ufsc.ine.leb.roza.core.modern.clustering.LinkageMethod;
import br.ufsc.ine.leb.roza.core.modern.clustering.MergeCandidate;
import br.ufsc.ine.leb.roza.core.modern.clustering.MergeTieBreaker;
import br.ufsc.ine.leb.roza.core.modern.clustering.MergeTieBreakerKind;
import br.ufsc.ine.leb.roza.core.modern.clustering.StopCriterion;
import br.ufsc.ine.leb.roza.core.modern.clustering.StopCriterionKind;
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
import br.ufsc.ine.leb.roza.core.modern.measurement.GreedyAdmissiblePrefixSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.JplagMeasurementConfiguration;
import br.ufsc.ine.leb.roza.core.modern.measurement.JplagTestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.LccssTestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.LcsTestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.MaxAdmissiblePrefixSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.SetupExtractionPotentialTestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.SimianMeasurementConfiguration;
import br.ufsc.ine.leb.roza.core.modern.measurement.SimianTestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMatrix;
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureKind;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.JunitTestClassParser;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsingException;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClassParser;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestCodeViolation;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.UnsupportedFeatureException;
import br.ufsc.ine.leb.roza.core.modern.parsing.ViolationContextExtractor;
import br.ufsc.ine.leb.roza.core.modern.parsing.ViolationScope;
import br.ufsc.ine.leb.roza.core.modern.refactoring.ImplicitSetupTestClassRefactorer;
import br.ufsc.ine.leb.roza.core.modern.refactoring.JunitTestClassRenderer;
import br.ufsc.ine.leb.roza.core.modern.refactoring.RefactoredTestClasses;
import br.ufsc.ine.leb.roza.core.modern.writing.FileSystemTestClassWriter;
import br.ufsc.ine.leb.roza.core.modern.writing.TestClassWriter;
import javafx.beans.binding.Bindings;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;

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
	private static final int TOP_REFACTORING_LEVEL_LIMIT = 10;
	/** Intragroup spacing (label + control, controls within the same block). */
	private static final int SPACING_X = 8;
	/** Extragroup spacing (between separate configuration blocks or major sections). */
	private static final int SPACING_4X = SPACING_X * 4;
	private static final int LIST_WIDTH_LARGE = 300;
	private static final int LIST_WIDTH_SMALL = 100;
	private static final int SIDEBAR_WIDTH = 320;

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
	private final List<SelectedStopCriterion> selectedStopCriteria;
	private final List<MergeTieBreakerKind> selectedTieBreakerKinds;
	private Path sourceFolder;
	private LoadedCodeFiles loadedCodeFiles;
	private CodeFile selectedCodeFile;
	private String loadingError;
	private ParsedTestClasses parsedTestClasses;
	private String parsingError;
	private DecomposedTestCases decomposedTestCases;
	private String decompositionError;
	private TestCase selectedDecomposedTestCase;
	private TestClass selectedParsedTestClass;
	private String selectedClassDetailsTab = "Summary";
	private TestMethod selectedParsedTestMethod;
	private FixtureMethod selectedParsedFixture;
	private TestCodeViolation selectedClassViolation;
	private TestCodeViolation selectedViolation;
	private TestCaseSimilarityMatrix similarityMatrix;
	private String measurementError;
	private TestCaseClusters testCaseClusters;
	private List<ClusteringLevel> clusteringLevels;
	private String clusteringError;
	private int selectedRefactoringLevelIndex;
	private List<Integer> cachedTopRefactoringLevelIndices;
	private boolean topRefactoringLevelsComputing;
	private int topRefactoringRankCycleIndex;
	private ListView<Integer> refactoringLevelList;
	private ScrollPane refactoringClustersPane;
	private Button refactoringTopLevelButton;
	private RefactoredTestClasses refactoredTestClasses;
	private TestClass selectedRefactoredTestClass;
	private String refactoringError;
	private Path outputFolder;
	private String writingError;
	private boolean rankedSimilarityDescending;
	private boolean suppressSimilaritySelectionRender;
	private boolean suppressSimilarityComboListener;
	private HBox decompositionClassesRow;
	private TextArea measurementTestBodyArea;
	private TextArea parsingFileContentArea;
	private TextArea clusteringSourceCodeArea;
	private TextArea clusteringTargetCodeArea;
	private final ViolationContextExtractor violationContextExtractor = new ViolationContextExtractor();

	public ModernRozaUi() {
		pipelineState = new PipelineState();
		pipelineBar = new HBox(SPACING_X);
		configurationSidebar = new VBox(SPACING_4X);
		contentArea = new VBox(SPACING_4X);
		recursiveLoading = new CheckBox("Enabled");
		javaExtension = new CheckBox(".java");
		txtExtension = new CheckBox(".txt");
		recursiveLoading.setSelected(true);
		javaExtension.setSelected(true);
		txtExtension.setSelected(false);

		metricCombo = new ComboBox<>();
		metricCombo.getItems().add("LCCSS");
		metricCombo.getItems().add("GAP");
		metricCombo.getItems().add("MAP");
		metricCombo.getItems().add("SEP");
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
		selectedStopCriteria = new ArrayList<>();
		selectedTieBreakerKinds = new ArrayList<>();

		sourceFolder = defaultSourceFolder();
		outputFolder = defaultOutputFolder();
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

	private Path defaultOutputFolder() {
		Path fromRozaProject = Path.of("output", "writer").toAbsolutePath().normalize();
		if (Files.isDirectory(Path.of("src", "main", "java"))) {
			return fromRozaProject;
		}
		Path fromWorkspaceRoot = Path.of("roza", "output", "writer").toAbsolutePath().normalize();
		if (Files.isDirectory(Path.of("roza", "src", "main", "java"))) {
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

		stage.setTitle("Modern Róża UI");
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
		pipelineBar.setPadding(new Insets(SPACING_4X));
		pipelineBar.setAlignment(Pos.CENTER_LEFT);
		pipelineBar.setStyle(FONT_FAMILY + "-fx-background-color: #333333;");

		for (PipelineStage stage : pipelineState.stages()) {
			if (stage == PipelineStage.ANALYTICS && !pipelineState.canSelect(stage)) {
				continue;
			}
			Button stageButton = new Button(stage.displayName());
			boolean selectable = pipelineState.canSelect(stage);
			stageButton.setMouseTransparent(!selectable);
			stageButton.setFocusTraversable(selectable);
			stageButton.setStyle(stageButtonStyle(stage, pipelineState.status(stage), pipelineState.isSelected(stage)));
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
		configurationSidebar.setPadding(new Insets(SPACING_4X));
		configurationSidebar.setPrefWidth(SIDEBAR_WIDTH);
		configurationSidebar.setStyle(FONT_FAMILY + "-fx-background-color: #edf1f5;");

		VBox configuration = configurationFor(selectedStage);

		if (selectedStage == PipelineStage.ANALYTICS) {
			if (configuration.getChildren().isEmpty()) {
				configurationSidebar.getChildren().add(body("Pipeline statistics after writing completes."));
			} else {
				configurationSidebar.getChildren().add(configuration);
			}
		} else if (selectedStage == PipelineStage.CLUSTERING || selectedStage == PipelineStage.REFACTORING) {
			configurationSidebar.getChildren().add(configuration);
		} else {
			Button actionButton = new Button(actionButtonText(selectedStage));
			actionButton.setMaxWidth(Double.MAX_VALUE);
			actionButton.setDisable(!stageActionEnabled(selectedStage));
			actionButton.setStyle(primaryButtonStyle());
			actionButton.setOnAction(event -> {
				runStage(selectedStage);
			});

			if (selectedStage == PipelineStage.PARSING) {
				VBox parsingBlock = parsingActionBlock(actionButton);
				if (configuration.getChildren().isEmpty()) {
					configurationSidebar.getChildren().add(parsingBlock);
				} else {
					configurationSidebar.getChildren().addAll(configuration, parsingBlock);
				}
			} else if (selectedStage == PipelineStage.DECOMPOSITION) {
				VBox decompositionBlock = decompositionActionBlock(actionButton);
				if (configuration.getChildren().isEmpty()) {
					configurationSidebar.getChildren().add(decompositionBlock);
				} else {
					configurationSidebar.getChildren().addAll(configuration, decompositionBlock);
				}
			} else if (configuration.getChildren().isEmpty()) {
				configurationSidebar.getChildren().add(actionButton);
			} else {
				configurationSidebar.getChildren().addAll(configuration, actionButton);
			}
		}
	}

	private VBox parsingActionBlock(Button parseButton) {
		VBox block = new VBox(SPACING_4X);
		block.setMaxWidth(Double.MAX_VALUE);
		VBox summary = loadedFilesSummary();
		block.getChildren().addAll(parseButton, summary);
		return block;
	}

	private VBox loadedFilesSummary() {
		VBox summary = new VBox(SPACING_X);
		summary.setMaxWidth(Double.MAX_VALUE);
		int loadedFileCount = loadedCodeFiles == null ? 0 : loadedCodeFiles.codeFiles().size();
		summary.getChildren().add(sidebarStatisticsTable(List.of(List.of("Loaded files", formatNumber(loadedFileCount)))));
		return summary;
	}

	private VBox decompositionActionBlock(Button decomposeButton) {
		VBox block = new VBox(SPACING_4X);
		block.setMaxWidth(Double.MAX_VALUE);
		block.getChildren().add(decomposeButton);
		if (parsedTestClasses != null) {
			block.getChildren().add(decompositionSummary());
		}
		return block;
	}

	private void showConfigurationHelpDialog(String title, String introduction, List<ConfigurationHelpEntry> entries) {
		Dialog<Void> dialog = new Dialog<>();
		dialog.initModality(Modality.APPLICATION_MODAL);
		if (configurationSidebar.getScene() != null) {
			dialog.initOwner(configurationSidebar.getScene().getWindow());
		}
		dialog.setTitle(title);
		dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

		VBox content = new VBox(SPACING_4X);
		content.setPadding(new Insets(SPACING_X, 0, SPACING_X, 0));
		content.setMaxWidth(Double.MAX_VALUE);
		if (introduction != null && !introduction.isBlank()) {
			Label introductionLabel = body(introduction);
			introductionLabel.setWrapText(true);
			introductionLabel.setStyle(introductionLabel.getStyle() + "-fx-font-size: 13px; -fx-text-fill: #4b5563;");
			content.getChildren().add(introductionLabel);
		}
		for (ConfigurationHelpEntry entry : entries) {
			content.getChildren().add(configurationHelpEntry(entry));
		}

		ScrollPane scrollPane = new ScrollPane(content);
		scrollPane.setFitToWidth(true);
		scrollPane.setPrefViewportWidth(520);
		scrollPane.setPrefViewportHeight(480);
		scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

		dialog.getDialogPane().setContent(scrollPane);
		dialog.getDialogPane().setPrefWidth(560);
		dialog.showAndWait();
	}

	private void showMeasurementMetricHelpDialog() {
		showConfigurationHelpDialog("Measurement metrics", null, measurementMetricHelpEntries());
	}

	private void showLinkageMethodHelpDialog() {
		showConfigurationHelpDialog(
				"Linkage methods",
				"Róża builds clusters bottom up, repeatedly merging the two most similar groups. Linkage decides how group similarity is computed from the pairwise similarities between individual tests.",
				linkageMethodHelpEntries());
	}

	private void showStopCriteriaHelpDialog() {
		showConfigurationHelpDialog(
				"Stop criteria",
				"Stop criteria tell clustering when to stop merging. You can add several; clustering stops as soon as any one of them is satisfied. With none configured, merging continues until no candidates remain.",
				stopCriterionHelpEntries());
	}

	private void showMergeTieBreakersHelpDialog() {
		showConfigurationHelpDialog(
				"Merge tie breakers",
				"Sometimes two merges reach the same similarity score. Tie breakers pick one. They are tried in list order; the first breaker that selects a single merge wins. With none configured, an unresolved tie stops clustering.",
				mergeTieBreakerHelpEntries());
	}

	private List<ConfigurationHelpEntry> measurementMetricHelpEntries() {
		return List.of(
				new ConfigurationHelpEntry(
						"LCCSS",
						"Longest Common Contiguous Start Subsequence. Looks at how much setup code two tests share at the beginning, before either one starts asserting. The more statements match in order from the top, the higher the score. Normalized with Dice. Useful when you care about shared setup at the very start of each test."),
				new ConfigurationHelpEntry(
						"GAP",
						"Greedy Admissible Prefix. Like LCCSS, but willing to shuffle one test's arrange when dependencies allow, trying to line up with the other test's order as it goes. Fast, but may stop short of the longest prefix a deeper search could find. Normalized with Dice. Useful when you care about similarity despite reorderable arrange statements and want a fast answer."),
				new ConfigurationHelpEntry(
						"MAP",
						"Maximum Admissible Prefix. Can reorder arrange statements when dependencies allow, and searches matching choices for the longest admissible prefix instead of stopping at the first greedy fit. Search is limited to 50,000 states; if that limit is hit, the score uses the greedy result instead. Normalized with Dice. Useful when you care about finding the longest dependency-respecting shared prefix."),
				new ConfigurationHelpEntry(
						"SEP",
						"Setup Extraction Potential. Same shared opening as LCCSS, but reports the raw count of matching statements instead of a normalized score. Ten shared statements beat two, even if the shorter pair looks proportionally closer under LCCSS. Useful when you care about how much setup you can actually extract."),
				new ConfigurationHelpEntry(
						"LCS",
						"Longest Common Subsequence. Finds the longest stretch of arrange statements both tests have in common, keeping order but allowing gaps. Unlike LCCSS, a match in the middle still counts. Normalized with Dice. Useful when you care about similar arrange code even when it is not a clean prefix match."),
				new ConfigurationHelpEntry(
						"Deckard",
						"External tree-based clone detector. Parses Java into syntax trees, fingerprints similar subtrees, and reports clones. Róża runs it on arrange projections and scores how much of the source arrange is covered by fragments that match the target. Useful when you care about structural similarity from an external clone detector."),
				new ConfigurationHelpEntry(
						"JPlag",
						"External program similarity detector. Turns each arrange projection into tokens and looks for long matching runs between the two using Greedy String Tiling. Róża reads the directional coverage from JPlag's HTML report. Sensitivity sets the minimum run length. Useful when you care about token-level overlap from an external similarity tool."),
				new ConfigurationHelpEntry(
						"Simian",
						"External duplicate-code detector. Finds identical blocks of consecutive lines above a minimum size. Róża runs it on arrange projections and scores how much of the source arrange falls inside blocks reported as duplicates with the target. Threshold is the minimum block size in lines. Useful when you care about exact duplicate lines from an external duplicate-code detector."));
	}

	private List<ConfigurationHelpEntry> linkageMethodHelpEntries() {
		return List.of(
				new ConfigurationHelpEntry(
						"Single",
						"Looks at the strongest link between the two groups. Group similarity is the highest pairwise similarity between any test in one group and any test in the other. Tends to form loose, chain-like clusters."),
				new ConfigurationHelpEntry(
						"Complete",
						"Looks at the weakest link between the two groups. Group similarity is the lowest pairwise similarity across every test pair joining the two groups. Tends to keep clusters compact and internally similar."),
				new ConfigurationHelpEntry(
						"Average",
						"Averages pairwise similarities across all test pairs joining the two groups. A middle ground between single and complete linkage."));
	}

	private List<ConfigurationHelpEntry> stopCriterionHelpEntries() {
		return List.of(
				new ConfigurationHelpEntry(
						"Minimum similarity",
						"Stops before the next merge when that merge's similarity falls to this value or below. Default 0.0 only stops at zero similarity, so clustering keeps going until similarities run out or another criterion stops it."),
				new ConfigurationHelpEntry(
						"Maximum tests per cluster",
						"Stops when the next merge would create a cluster larger than the limit. Default 1 allows only single-test clusters, so no merge can happen."),
				new ConfigurationHelpEntry(
						"Minimum tests per cluster",
						"Stops once every current cluster already has at least this many tests. Default 1 is already satisfied before the first merge, so raise it when you want clustering to continue until clusters grow."),
				new ConfigurationHelpEntry(
						"Maximum merge level",
						"Stops after a fixed number of merge steps. Level 1 is the starting point, before any merge. Default 1 stops before the first merge."),
				new ConfigurationHelpEntry(
						"Target cluster count",
						"Stops when the number of clusters reaches this count or fewer. Each cluster becomes a test class after refactoring. Default 1 keeps merging until every test case is grouped into a single test class."),
				new ConfigurationHelpEntry(
						"Minimum shared prefix",
						"Stops when the merged cluster's common contiguous prefix of test-body statements falls to this size or below. Default 0 stops only when the candidate merge would leave no shared prefix at all."));
	}

	private List<ConfigurationHelpEntry> mergeTieBreakerHelpEntries() {
		return List.of(
				new ConfigurationHelpEntry(
						"Largest merged cluster",
						"Prefers the merge that would create the bigger resulting cluster."),
				new ConfigurationHelpEntry(
						"Smallest merged cluster",
						"Prefers the merge that would create the smaller resulting cluster."),
				new ConfigurationHelpEntry(
						"Stable test order",
						"Prefers the merge whose resulting test index order comes first. Keeps results repeatable when other breakers still leave a tie."));
	}

	private HBox configurationTitleRow(String title, Runnable helpAction) {
		Label titleLabel = body(title);
		titleLabel.setStyle(titleLabel.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		Button helpButton = configurationHelpButton(helpAction);
		HBox titleRow = new HBox(SPACING_X, titleLabel, helpButton);
		titleRow.setAlignment(Pos.CENTER_LEFT);
		HBox.setHgrow(titleLabel, Priority.ALWAYS);
		return titleRow;
	}

	private VBox configurationHelpEntry(ConfigurationHelpEntry entry) {
		VBox block = new VBox(SPACING_X);
		Label nameLabel = body(entry.name());
		nameLabel.setStyle(nameLabel.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333; -fx-font-size: 14px;");
		Label descriptionLabel = body(entry.description());
		descriptionLabel.setStyle(descriptionLabel.getStyle() + "-fx-font-size: 13px; -fx-text-fill: #4b5563;");
		block.getChildren().addAll(nameLabel, descriptionLabel);
		return block;
	}

	private static final class ConfigurationHelpEntry {

		private final String name;
		private final String description;

		private ConfigurationHelpEntry(String name, String description) {
			this.name = name;
			this.description = description;
		}

		private String name() {
			return name;
		}

		private String description() {
			return description;
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
		if (selectedStage == PipelineStage.WRITING) {
			return writingConfiguration();
		}
		VBox configuration = new VBox(SPACING_X);
		for (String item : selectedStage.configurationItems()) {
			configuration.getChildren().add(configurationRow(item));
		}
		return configuration;
	}

	private VBox loadingConfiguration() {
		Button sourceFolderButton = new Button("Source folder");
		sourceFolderButton.setMaxWidth(Double.MAX_VALUE);
		sourceFolderButton.setStyle(secondaryButtonStyle());
		sourceFolderButton.setOnAction(event -> chooseSourceFolder());

		Label selectedFolder = body(sourceFolderText());
		VBox sourceFolderGroup = new VBox(SPACING_X, sourceFolderButton, selectedFolder);

		Label recursiveSectionTitle = body("Recursive loading");
		recursiveSectionTitle.setStyle(recursiveSectionTitle.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		VBox recursiveGroup = new VBox(SPACING_X, recursiveSectionTitle, recursiveLoading);

		Label acceptedExtensions = body("Accepted extensions");
		acceptedExtensions.setStyle(acceptedExtensions.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		VBox extensionsGroup = new VBox(SPACING_X, acceptedExtensions, javaExtension, txtExtension);

		VBox configuration = new VBox(SPACING_4X, sourceFolderGroup, recursiveGroup, extensionsGroup);
		return configuration;
	}

	private VBox refactoringConfiguration() {
		VBox configuration = new VBox(SPACING_X);

		Button refactorButton = new Button(PipelineStage.REFACTORING.actionLabel());
		refactorButton.setMaxWidth(Double.MAX_VALUE);
		refactorButton.setStyle(primaryButtonStyle());
		refactorButton.setDisable(!stageActionEnabled(PipelineStage.REFACTORING));
		refactorButton.setOnAction(event -> runRefactoring());

		Button refactorCurrentLevelButton = new Button("Refactor selected level clusters");
		refactorCurrentLevelButton.setMaxWidth(Double.MAX_VALUE);
		refactorCurrentLevelButton.setStyle(secondaryButtonStyle());
		refactorCurrentLevelButton.setDisable(!stageActionEnabled(PipelineStage.REFACTORING));
		refactorCurrentLevelButton.setOnAction(event -> runRefactoringCurrentLevel());

		configuration.getChildren().addAll(refactorButton, refactorCurrentLevelButton);
		return configuration;
	}

	private VBox writingConfiguration() {
		Button outputFolderButton = new Button("Output folder");
		outputFolderButton.setMaxWidth(Double.MAX_VALUE);
		outputFolderButton.setStyle(secondaryButtonStyle());
		outputFolderButton.setOnAction(event -> chooseOutputFolder());

		Label selectedFolder = body(outputFolderText());
		VBox configuration = new VBox(SPACING_X, outputFolderButton, selectedFolder);
		return configuration;
	}

	private VBox parsingConfiguration() {
		return new VBox(SPACING_X);
	}

	private VBox decompositionConfiguration() {
		return new VBox(SPACING_X);
	}

	private VBox measurementConfiguration() {
		VBox metricBlock = new VBox(SPACING_X);
		HBox metricTitleRow = configurationTitleRow("Measurement metric", this::showMeasurementMetricHelpDialog);
		metricCombo.setMaxWidth(Double.MAX_VALUE);
		metricBlock.getChildren().addAll(metricTitleRow, metricCombo);

		VBox configuration = new VBox(SPACING_4X);
		configuration.getChildren().add(metricBlock);
		if ("Deckard".equals(metricCombo.getSelectionModel().getSelectedItem())) {
			VBox deckardFields = new VBox(SPACING_X);
			deckardFields.getChildren().addAll(
					configurationInput("Minimum tokens", deckardMinTokensInput),
					configurationInput("Stride", deckardStrideInput),
					configurationInput("Similarity", deckardSimilarityInput));
			configuration.getChildren().add(deckardFields);
		}
		if ("JPlag".equals(metricCombo.getSelectionModel().getSelectedItem())) {
			VBox jplagFields = new VBox(SPACING_X);
			jplagFields.getChildren().add(configurationInput("Sensitivity", jplagSensitivityInput));
			configuration.getChildren().add(jplagFields);
		}
		if ("Simian".equals(metricCombo.getSelectionModel().getSelectedItem())) {
			VBox simianFields = new VBox(SPACING_X);
			simianFields.getChildren().add(configurationInput("Threshold", simianThresholdInput));
			configuration.getChildren().add(simianFields);
		}
		return configuration;
	}

	private Button configurationHelpButton(Runnable action) {
		Button helpButton = new Button("?");
		helpButton.setMinWidth(Region.USE_PREF_SIZE);
		helpButton.setMaxWidth(Region.USE_PREF_SIZE);
		helpButton.setStyle(secondaryButtonStyle() + "-fx-font-size: 12px; -fx-padding: 2 8;");
		helpButton.setOnAction(event -> action.run());
		return helpButton;
	}

	private VBox clusteringConfiguration() {
		VBox linkageBlock = new VBox(SPACING_X);
		linkageMethodCombo.setMaxWidth(Double.MAX_VALUE);
		linkageBlock.getChildren().addAll(configurationTitleRow("Linkage method", this::showLinkageMethodHelpDialog), linkageMethodCombo);

		VBox stopCriteriaBlock = new VBox(SPACING_X);
		stopCriteriaBlock.getChildren().addAll(configurationTitleRow("Stop criteria", this::showStopCriteriaHelpDialog), stopCriterionEditor());

		VBox tieBreakersBlock = new VBox(SPACING_X);
		tieBreakersBlock.getChildren().addAll(configurationTitleRow("Merge tie breakers", this::showMergeTieBreakersHelpDialog), tieBreakerEditor());

		Button clusterButton = new Button(PipelineStage.CLUSTERING.actionLabel());
		clusterButton.setMaxWidth(Double.MAX_VALUE);
		clusterButton.setStyle(primaryButtonStyle());
		clusterButton.setDisable(!stageActionEnabled(PipelineStage.CLUSTERING));
		clusterButton.setOnAction(event -> runStage(PipelineStage.CLUSTERING));

		VBox configuration = new VBox(SPACING_4X);
		configuration.getChildren().addAll(linkageBlock, stopCriteriaBlock, tieBreakersBlock, clusterButton);
		return configuration;
	}

	private VBox stopCriterionEditor() {
		VBox editor = new VBox(SPACING_X);
		for (int index = 0; index < selectedStopCriteria.size(); index++) {
			editor.getChildren().add(stopCriterionRow(index));
		}
		Button add = new Button("Add stop criterion");
		add.setStyle(secondaryButtonStyle());
		add.setMaxWidth(Double.MAX_VALUE);
		add.setOnAction(event -> {
			selectedStopCriteria.add(new SelectedStopCriterion(StopCriterionKind.MINIMUM_SIMILARITY));
			renderConfigurationSidebar();
		});
		editor.getChildren().add(add);
		return editor;
	}

	private HBox stopCriterionRow(int index) {
		SelectedStopCriterion entry = selectedStopCriteria.get(index);
		ComboBox<StopCriterionKind> comboBox = stopCriterionComboBox();
		comboBox.getSelectionModel().select(entry.kind);
		comboBox.valueProperty().addListener((observable, previous, selected) -> {
			if (selected == null) {
				return;
			}
			entry.kind = selected;
			if (previous != selected) {
				entry.value = selected.defaultValue();
				renderConfigurationSidebar();
			}
		});
		comboBox.setMinWidth(0);
		comboBox.setMaxWidth(Double.MAX_VALUE);
		TextField valueInput = metricConfigurationInput(entry.value);
		valueInput.setPrefWidth(72);
		valueInput.setMinWidth(Region.USE_PREF_SIZE);
		valueInput.setMaxWidth(Region.USE_PREF_SIZE);
		valueInput.textProperty().addListener((observable, previous, text) -> entry.value = text);
		Button remove = new Button("Remove");
		remove.setStyle(secondaryButtonStyle());
		remove.setMinWidth(Region.USE_PREF_SIZE);
		remove.setOnAction(event -> {
			selectedStopCriteria.remove(index);
			renderConfigurationSidebar();
		});
		HBox row = new HBox(SPACING_X);
		row.getChildren().addAll(comboBox, valueInput, remove);
		HBox.setHgrow(comboBox, Priority.ALWAYS);
		HBox.setHgrow(valueInput, Priority.NEVER);
		HBox.setHgrow(remove, Priority.NEVER);
		return row;
	}

	private ComboBox<StopCriterionKind> stopCriterionComboBox() {
		ComboBox<StopCriterionKind> comboBox = new ComboBox<>();
		comboBox.getItems().addAll(StopCriterionKind.values());
		comboBox.getSelectionModel().selectFirst();
		comboBox.setStyle(singleLineComboBoxStyle());
		comboBox.setCellFactory(list -> new ListCell<>() {
			@Override
			protected void updateItem(StopCriterionKind item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.displayName());
			}
		});
		comboBox.setButtonCell(new ListCell<>() {
			@Override
			protected void updateItem(StopCriterionKind item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.displayName());
			}
		});
		return comboBox;
	}

	private VBox tieBreakerEditor() {
		VBox editor = new VBox(SPACING_X);
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
		HBox row = new HBox(SPACING_X);
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
		VBox row = new VBox(SPACING_X);
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

	private void chooseOutputFolder() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Output folder");
		File selectedFolder = directoryChooser.showDialog(configurationSidebar.getScene().getWindow());
		if (selectedFolder != null) {
			outputFolder = selectedFolder.toPath();
			writingError = null;
			render();
		}
	}

	private String sourceFolderText() {
		if (sourceFolder == null) {
			return "No source folder selected.";
		}
		return sourceFolder.toString();
	}

	private String outputFolderText() {
		if (outputFolder == null) {
			return "No output folder selected.";
		}
		return outputFolder.toString();
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
		if (selectedStage == PipelineStage.REFACTORING) {
			return testCaseClusters != null;
		}
		if (selectedStage == PipelineStage.WRITING) {
			return refactoredTestClasses != null && outputFolder != null;
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
		} else if (selectedStage == PipelineStage.REFACTORING) {
			runRefactoring();
		} else if (selectedStage == PipelineStage.WRITING) {
			runWriting();
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
		decomposedTestCases = null;
		decompositionError = null;
		selectedDecomposedTestCase = null;
		selectedParsedTestClass = null;
		selectedClassDetailsTab = "Summary";
		selectedParsedTestMethod = null;
		selectedParsedFixture = null;
		selectedClassViolation = null;
		selectedViolation = null;
		clearMeasurementResults();
	}

	private void clearMeasurementResults() {
		similarityMatrix = null;
		measurementError = null;
		sourceTestCombo.getItems().clear();
		targetTestCombo.getItems().clear();
		sourceTestCombo.getSelectionModel().clearSelection();
		targetTestCombo.getSelectionModel().clearSelection();
		selectedStopCriteria.clear();
		selectedTieBreakerKinds.clear();
		clearClusteringResults();
	}

	private void clearClusteringResults() {
		testCaseClusters = null;
		clusteringLevels = null;
		clusteringError = null;
		selectedRefactoringLevelIndex = 0;
		cachedTopRefactoringLevelIndices = null;
		topRefactoringLevelsComputing = false;
		topRefactoringRankCycleIndex = 0;
		clearRefactoringResults();
	}

	private void clearRefactoringResults() {
		refactoredTestClasses = null;
		selectedRefactoredTestClass = null;
		refactoringError = null;
		writingError = null;
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

	private void runParsing() {
		try {
			TestClassParser parser = new JunitTestClassParser();
			parsedTestClasses = parser.parse(loadedCodeFiles);
			parsingError = null;
			initializeDecompositionSelection();
			decomposedTestCases = null;
			decompositionError = null;
			selectedDecomposedTestCase = null;
			clearMeasurementResults();
			pipelineState.runSelectedStage();
		} catch (RuntimeException exception) {
			parsingError = exception.getMessage() != null ? exception.getMessage() : exception.toString();
			selectCodeFileForParseFailure(exception);
			parsedTestClasses = null;
			decomposedTestCases = null;
			decompositionError = null;
			selectedDecomposedTestCase = null;
			selectedParsedTestClass = null;
			selectedClassDetailsTab = "Summary";
			selectedParsedTestMethod = null;
			selectedParsedFixture = null;
			selectedClassViolation = null;
			selectedViolation = null;
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
			clearRefactoringResults();
			scheduleBestRefactoringLevelComputation();
			pipelineState.runSelectedStage();
		} catch (RuntimeException exception) {
			testCaseClusters = null;
			clusteringLevels = null;
			clusteringError = exception.getMessage() != null ? exception.getMessage() : exception.toString();
			clearRefactoringResults();
		}
		render();
	}

	private void runRefactoring() {
		refactor(testCaseClusters);
	}

	private void runRefactoringCurrentLevel() {
		if (clusteringLevels == null || clusteringLevels.isEmpty()) {
			refactoringError = "No clustering level selected.";
			render();
			return;
		}
		int levelIndex = selectedRefactoringLevelIndex;
		if (levelIndex < 0 || levelIndex >= clusteringLevels.size()) {
			levelIndex = clusteringLevels.size() - 1;
		}
		refactor(new TestCaseClusters(clusteringLevels.get(levelIndex).clusters()));
	}

	private void refactor(TestCaseClusters clusters) {
		try {
			refactoredTestClasses = new ImplicitSetupTestClassRefactorer().refactor(clusters);
			selectedRefactoredTestClass = refactoredTestClasses.testClasses().isEmpty() ? null : refactoredTestClasses.testClasses().get(0);
			refactoringError = null;
			writingError = null;
			pipelineState.runSelectedStage();
		} catch (RuntimeException exception) {
			refactoredTestClasses = null;
			selectedRefactoredTestClass = null;
			refactoringError = exception.getMessage() != null ? exception.getMessage() : exception.toString();
			writingError = null;
		}
		render();
	}

	private void runWriting() {
		try {
			TestClassWriter writer = new FileSystemTestClassWriter(outputFolder);
			writer.write(refactoredTestClasses);
			writingError = null;
			pipelineState.runSelectedStage();
		} catch (RuntimeException exception) {
			writingError = exception.getMessage() != null ? exception.getMessage() : exception.toString();
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
		if ("GAP".equals(metricCombo.getSelectionModel().getSelectedItem())) {
			return new GreedyAdmissiblePrefixSimilarityMeasurer();
		}
		if ("MAP".equals(metricCombo.getSelectionModel().getSelectedItem())) {
			return new MaxAdmissiblePrefixSimilarityMeasurer();
		}
		if ("SEP".equals(metricCombo.getSelectionModel().getSelectedItem())) {
			return new SetupExtractionPotentialTestCaseSimilarityMeasurer();
		}
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
			return selectedStopCriteria.stream()
					.map(entry -> entry.kind.create(entry.value.trim()))
					.collect(Collectors.toList());
		} catch (NumberFormatException exception) {
			throw new IllegalArgumentException("Clustering stop criterion values must be numeric.", exception);
		}
	}

	private static final class SelectedStopCriterion {
		private StopCriterionKind kind;
		private String value;

		private SelectedStopCriterion(StopCriterionKind kind) {
			this.kind = kind;
			this.value = kind.defaultValue();
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
		decompositionClassesRow = null;
		measurementTestBodyArea = null;
		parsingFileContentArea = null;
		refactoringLevelList = null;
		refactoringClustersPane = null;
		refactoringTopLevelButton = null;
		contentArea.getChildren().clear();
		contentArea.setPadding(new Insets(SPACING_4X));
		contentArea.setStyle(FONT_FAMILY + "-fx-background-color: #f4f6f8;");

		if (selectedStage == PipelineStage.LOADING && loadingError != null) {
			Label error = body("Loading failed: " + loadingError);
			error.setStyle(error.getStyle() + "-fx-text-fill: #991b1b;");
			contentArea.getChildren().add(error);
		} else if (selectedStage == PipelineStage.PARSING && loadedCodeFiles != null) {
			VBox parsingColumn = new VBox(SPACING_4X);
			if (parsingError != null) {
				Label error = body("Parsing failed: " + parsingError);
				error.setStyle(error.getStyle() + "-fx-text-fill: #991b1b;");
				parsingColumn.getChildren().add(error);
			}
			parsingColumn.getChildren().add(loadedFilesView());
			contentArea.getChildren().add(parsingColumn);
			VBox.setVgrow(parsingColumn, Priority.ALWAYS);
		} else if (selectedStage == PipelineStage.DECOMPOSITION) {
			VBox decompositionColumn = new VBox(SPACING_4X);
			if (decompositionError != null) {
				Label error = body("Decomposition failed: " + decompositionError);
				error.setStyle(error.getStyle() + "-fx-text-fill: #991b1b;");
				decompositionColumn.getChildren().add(error);
			}
			if (parsedTestClasses != null) {
				TabPane decompositionTabs = decompositionContentTabs();
				decompositionColumn.getChildren().add(decompositionTabs);
				VBox.setVgrow(decompositionTabs, Priority.ALWAYS);
			}
			contentArea.getChildren().add(decompositionColumn);
			VBox.setVgrow(decompositionColumn, Priority.ALWAYS);
		} else if (selectedStage == PipelineStage.MEASUREMENT && decomposedTestCases != null) {
			VBox measurementColumn = new VBox(SPACING_4X);
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
			VBox clusteringColumn = new VBox(SPACING_4X);
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
			VBox refactoringColumn = new VBox(SPACING_4X);
			if (clusteringError != null) {
				Label error = body("Clustering failed: " + clusteringError);
				error.setStyle(error.getStyle() + "-fx-text-fill: #991b1b;");
				refactoringColumn.getChildren().add(error);
			}
			if (refactoringError != null) {
				Label error = body("Refactoring failed: " + refactoringError);
				error.setStyle(error.getStyle() + "-fx-text-fill: #991b1b;");
				refactoringColumn.getChildren().add(error);
			}
			HBox levelsView = refactoringMergeLevelsView();
			refactoringColumn.getChildren().add(levelsView);
			VBox.setVgrow(levelsView, Priority.ALWAYS);
			contentArea.getChildren().add(refactoringColumn);
			VBox.setVgrow(refactoringColumn, Priority.ALWAYS);
		} else if (selectedStage == PipelineStage.WRITING && refactoredTestClasses != null) {
			VBox writingColumn = new VBox(SPACING_4X);
			if (writingError != null) {
				Label error = body("Writing failed: " + writingError);
				error.setStyle(error.getStyle() + "-fx-text-fill: #991b1b;");
				writingColumn.getChildren().add(error);
			}
			HBox writingView = refactoredTestClassesView();
			writingColumn.getChildren().add(writingView);
			contentArea.getChildren().add(writingColumn);
			VBox.setVgrow(writingView, Priority.ALWAYS);
			VBox.setVgrow(writingColumn, Priority.ALWAYS);
		} else if (selectedStage == PipelineStage.ANALYTICS && parsedTestClasses != null && refactoredTestClasses != null) {
			VBox analyticsColumn = analyticsView();
			contentArea.getChildren().add(analyticsColumn);
			VBox.setVgrow(analyticsColumn, Priority.ALWAYS);
		} else {
			contentArea.getChildren().add(body(selectedStage.previousStageDataDescription()));
		}
	}

	private VBox decompositionSummary() {
		VBox summary = new VBox(SPACING_X);
		summary.setMaxWidth(Double.MAX_VALUE);
		summary.getChildren().add(sidebarStatisticsTable(
				List.of(
						List.of("Class-level violations", formatNumber(classViolationCount())),
						List.of("Method-level violations", formatNumber(methodLevelViolationTestCount())),
						List.of("Test classes", formatNumber(testClassCount())),
						List.of("Tests", formatNumber(totalTestCount())),
						List.of("Tests with violations", formatNumber(excludedTestCount())),
						List.of("Accepted tests", formatNumber(acceptedTestCount())))));
		return summary;
	}

	private VBox analyticsView() {
		VBox analytics = new VBox(SPACING_4X);
		TestCodeAnalytics analyticsGenerator = new DefaultTestCodeAnalytics();
		TestCodeAnalyticsReport report = analyticsGenerator.analyze(parsedTestClasses, decomposedTestCases, refactoredTestClasses);
		HBox tables = new HBox(SPACING_4X);
		VBox original = originalCodeStatisticsTable(report.original());
		VBox comparison = eligibleVsRefactoredStatisticsTable(report.comparison());
		tables.getChildren().addAll(original, comparison);
		HBox.setHgrow(original, Priority.ALWAYS);
		HBox.setHgrow(comparison, Priority.ALWAYS);
		analytics.getChildren().add(tables);
		return analytics;
	}

	private VBox originalCodeStatisticsTable(OriginalTestCodeMetrics metrics) {
		VBox section = analyticsSection("Original test code");
		section.getChildren().add(statisticsTable(
				List.of("Metric", "Value"),
				List.of(
						List.of("Test classes", formatNumber(metrics.testClasses())),
						List.of("Test classes without violations", formatNumber(metrics.testClassesWithoutViolations())),
						List.of("Test classes with violations", formatNumber(metrics.testClassesWithViolations())),
						List.of("Test methods", formatNumber(metrics.testMethods())),
						List.of("Test methods without violations", formatNumber(metrics.testMethodsWithoutViolations())),
						List.of("Test methods with violations", formatNumber(metrics.testMethodsWithViolations())))));
		return section;
	}

	private VBox eligibleVsRefactoredStatisticsTable(TestCodeMetricComparison comparison) {
		VBox section = analyticsSection("Eligible vs. refactored");
		TestClassMetrics eligible = comparison.original();
		TestClassMetrics refactored = comparison.refactored();
		section.getChildren().add(statisticsTable(
				List.of("Metric", "Eligible", "Refactored"),
				List.of(
						List.of("Test classes", formatNumber(eligible.testClasses()), formatNumber(refactored.testClasses())),
						List.of("Test methods", formatNumber(eligible.testMethods()), formatNumber(refactored.testMethods())),
						List.of("Setup methods", formatNumber(eligible.setupMethods()), formatNumber(refactored.setupMethods())),
						List.of("Attributes", formatNumber(eligible.attributes()), formatNumber(refactored.attributes())),
						List.of("Total statements", formatNumber(eligible.totalStatements()), formatNumber(refactored.totalStatements())),
						List.of("Duplicated statements", formatNumber(eligible.duplicatedStatements()), formatNumber(refactored.duplicatedStatements())),
						List.of("Duplication rate", formatDuplicationRate(eligible.duplicationRate()), formatDuplicationRate(refactored.duplicationRate())))));
		VBox glossary = statementMetricsGlossary();
		VBox.setMargin(glossary, new Insets(SPACING_4X, 0, 0, 0));
		section.getChildren().add(glossary);
		return section;
	}

	private VBox statementMetricsGlossary() {
		VBox glossary = new VBox(SPACING_X);
		glossary.getChildren().addAll(
				glossaryEntry(
						"Total statements",
						"Counts non-assertion statements in setup and test method bodies, normalized for comparison. "
								+ "For example, a setup with one statement and a test with two yields 3."),
				glossaryEntry(
						"Duplicated statements",
						"Counts each copy after the first of a normalized non-assertion statement "
								+ "that appears more than once across all test classes. "
								+ "For example, if sut = new Sut(); appears in three tests and two setups, it contributes 4."),
				glossaryEntry(
						"Duplication rate",
						"Divides duplicated statements by total statements. "
								+ "For example, 4 duplicated statements out of 10 total statements yields 40%."));
		return glossary;
	}

	private VBox glossaryEntry(String term, String definition) {
		VBox entry = new VBox(SPACING_X);
		Label termLabel = body(term);
		termLabel.setStyle(termLabel.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #374151;");
		Label definitionLabel = body(definition);
		definitionLabel.setStyle(definitionLabel.getStyle() + "-fx-font-size: 13px;");
		entry.getChildren().addAll(termLabel, definitionLabel);
		return entry;
	}

	private VBox analyticsSection(String titleText) {
		VBox section = new VBox(SPACING_X);
		Label title = body(titleText);
		title.setStyle(title.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		section.getChildren().add(title);
		return section;
	}

	private GridPane sidebarStatisticsTable(List<List<String>> rows) {
		GridPane table = statisticsTable(List.of("Metric", "Value"), rows, 156, 76);
		stretchTableHorizontally(table, 156, 76);
		return table;
	}

	private void stretchTableHorizontally(GridPane table, double... minColumnWidths) {
		table.setMaxWidth(Double.MAX_VALUE);
		table.getColumnConstraints().clear();
		for (int column = 0; column < minColumnWidths.length; column++) {
			ColumnConstraints constraints = new ColumnConstraints();
			constraints.setMinWidth(minColumnWidths[column]);
			constraints.setHgrow(column == 0 ? Priority.ALWAYS : Priority.NEVER);
			table.getColumnConstraints().add(constraints);
		}
		for (var child : table.getChildren()) {
			GridPane.setHgrow(child, Priority.ALWAYS);
		}
	}

	private GridPane statisticsTable(List<String> headers, List<List<String>> rows) {
		return statisticsTable(headers, rows, 180);
	}

	private GridPane statisticsTable(List<String> headers, List<List<String>> rows, double... minColumnWidths) {
		GridPane table = new GridPane();
		table.setMaxWidth(Double.MAX_VALUE);
		int columnCount = headers.size();
		double[] columnWidths = columnWidths(columnCount, minColumnWidths);
		for (int column = 0; column < columnCount; column++) {
			Label headerCell = tableCell(headers.get(column), true, columnWidths[column]);
			configureTableCell(headerCell, column, columnCount, true);
			table.add(headerCell, column, 0);
		}
		for (int row = 0; row < rows.size(); row++) {
			List<String> values = rows.get(row);
			for (int column = 0; column < values.size(); column++) {
				Label cell = tableCell(values.get(column), false, columnWidths[column]);
				configureTableCell(cell, column, columnCount, false);
				table.add(cell, column, row + 1);
			}
		}
		return table;
	}

	private double[] columnWidths(int columnCount, double... minColumnWidths) {
		double[] widths = new double[columnCount];
		if (minColumnWidths.length == 0) {
			for (int column = 0; column < columnCount; column++) {
				widths[column] = 180;
			}
			return widths;
		}
		for (int column = 0; column < columnCount; column++) {
			widths[column] = column < minColumnWidths.length ? minColumnWidths[column] : minColumnWidths[minColumnWidths.length - 1];
		}
		return widths;
	}

	private void configureTableCell(Label cell, int column, int columnCount, boolean header) {
		GridPane.setVgrow(cell, Priority.ALWAYS);
		GridPane.setFillHeight(cell, true);
		cell.setMaxHeight(Double.MAX_VALUE);
		if (!header && column == columnCount - 1 && columnCount == 2) {
			cell.setAlignment(Pos.TOP_RIGHT);
		} else {
			cell.setAlignment(Pos.TOP_LEFT);
		}
	}

	private Label tableCell(String text, boolean header, double minWidth) {
		Label cell = body(text);
		cell.setMinWidth(minWidth);
		cell.setMaxWidth(Double.MAX_VALUE);
		String background = header ? "#ede9fe" : "#ffffff";
		String color = header ? "#4c1d95" : "#374151";
		String weight = header ? "-fx-font-weight: bold;" : "";
		cell.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-text-fill: " + color + "; -fx-background-color: " + background
				+ "; -fx-border-color: #d1d5db; -fx-border-width: 0 1 1 0; -fx-padding: 8;" + weight);
		return cell;
	}

	private long testClassCount() {
		return parsedTestClasses.testClasses().size();
	}

	private long totalTestCount() {
		return parsedTestClasses.testClasses().stream().mapToInt(testClass -> testClass.testMethods().size()).sum();
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
				.filter(testClass -> excludedClasses.contains(testClass.qualifiedName()))
				.flatMap(testClass -> testClass.testMethods().stream().map(testMethod -> testKey(testClass, testMethod)))
				.forEach(excludedTests::add);
		return excludedTests.size();
	}

	private long acceptedTestCount() {
		Set<String> excludedClasses = parsedTestClasses.violations().stream()
				.filter(violation -> violation.scope() == ViolationScope.TEST_CLASS)
				.map(TestCodeViolation::testClassName)
				.collect(Collectors.toSet());
		Set<String> excludedTests = parsedTestClasses.violations().stream()
				.filter(violation -> violation.scope() == ViolationScope.TEST_METHOD)
				.map(this::violationTestKey)
				.collect(Collectors.toSet());
		return parsedTestClasses.testClasses().stream()
				.filter(testClass -> !excludedClasses.contains(testClass.qualifiedName()))
				.flatMap(testClass -> testClass.testMethods()
						.stream()
						.filter(testMethod -> !excludedTests.contains(testKey(testClass, testMethod))))
				.count();
	}

	private String testKey(TestClass testClass, TestMethod testMethod) {
		return testClass.qualifiedName() + "." + testMethod.name();
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

	private void initializeDecompositionSelection() {
		if (parsedTestClasses == null) {
			selectedParsedTestClass = null;
			selectedParsedTestMethod = null;
			selectedParsedFixture = null;
			selectedClassViolation = null;
			selectedViolation = null;
			return;
		}
		selectedParsedTestClass = parsedTestClasses.testClasses().isEmpty() ? null : parsedTestClasses.testClasses().get(0);
		selectedClassDetailsTab = "Summary";
		selectedParsedTestMethod = null;
		selectedParsedFixture = null;
		selectedClassViolation = null;
		selectedViolation = parsedTestClasses.violations().isEmpty() ? null : parsedTestClasses.violations().get(0);
	}

	private TabPane decompositionContentTabs() {
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		tabPane.setMaxWidth(Double.MAX_VALUE);
		tabPane.setMaxHeight(Double.MAX_VALUE);

		Tab classesTab = new Tab("Classes", decompositionClassesView());
		Tab violationsTab = new Tab("Violations", decompositionViolationsView());
		tabPane.getTabs().addAll(classesTab, violationsTab);
		return tabPane;
	}

	private HBox decompositionClassesView() {
		ListView<TestClass> classList = new ListView<>();
		classList.getItems().addAll(parsedTestClasses.testClasses());
		configureLargeList(classList);
		classList.setCellFactory(list -> new ListCell<>() {
			@Override
			protected void updateItem(TestClass item, boolean empty) {
				super.updateItem(item, empty);
				updateDecompositionClassListCell(this, item, empty);
			}
		});
		if (selectedParsedTestClass != null) {
			classList.getSelectionModel().select(selectedParsedTestClass);
		}
		classList.getSelectionModel().selectedItemProperty().addListener((observable, previous, selected) -> {
			selectedParsedTestClass = selected;
			selectedParsedTestMethod = null;
			selectedParsedFixture = null;
			selectedClassViolation = null;
			refreshDecompositionClassDetails();
		});

		TabPane classDetailsTabs = classDetailsTabPane(selectedParsedTestClass);
		HBox.setHgrow(classDetailsTabs, Priority.ALWAYS);
		classDetailsTabs.setMaxWidth(Double.MAX_VALUE);
		classDetailsTabs.setMaxHeight(Double.MAX_VALUE);

		HBox row = new HBox(SPACING_4X);
		row.setMaxWidth(Double.MAX_VALUE);
		row.setMaxHeight(Double.MAX_VALUE);
		row.getChildren().addAll(classList, classDetailsTabs);
		HBox.setHgrow(classList, Priority.NEVER);
		HBox.setHgrow(row, Priority.ALWAYS);
		decompositionClassesRow = row;
		return row;
	}

	private void refreshDecompositionClassDetails() {
		if (decompositionClassesRow == null || decompositionClassesRow.getChildren().size() < 2) {
			renderContentArea();
			return;
		}
		TabPane replacement = classDetailsTabPane(selectedParsedTestClass);
		HBox.setHgrow(replacement, Priority.ALWAYS);
		replacement.setMaxWidth(Double.MAX_VALUE);
		replacement.setMaxHeight(Double.MAX_VALUE);
		decompositionClassesRow.getChildren().set(1, replacement);
	}

	private void updateDecompositionClassListCell(ListCell<TestClass> cell, TestClass item, boolean empty) {
		if (empty || item == null) {
			cell.setText(null);
			cell.setStyle(null);
			return;
		}
		List<TestCodeViolation> violations = violationsForClass(item);
		if (violations.isEmpty()) {
			cell.setText(item.qualifiedName());
			cell.setStyle(null);
			return;
		}
		boolean classViolation = violations.stream().anyMatch(violation -> violation.scope() == ViolationScope.TEST_CLASS);
		cell.setText(item.qualifiedName() + " (" + violations.size() + ")");
		if (cell.isSelected()) {
			cell.setStyle(null);
			return;
		}
		String color = classViolation ? "#991b1b" : "#a16207";
		cell.setStyle(FONT_FAMILY + "-fx-text-fill: " + color + ";");
	}

	private TabPane classDetailsTabPane(TestClass testClass) {
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		tabPane.setMaxWidth(Double.MAX_VALUE);
		tabPane.setMaxHeight(Double.MAX_VALUE);
		if (testClass == null) {
			Tab placeholderTab = new Tab("Summary");
			placeholderTab.setContent(body("Select a class to inspect its summary."));
			tabPane.getTabs().add(placeholderTab);
			return tabPane;
		}
		Tab summaryTab = new Tab("Summary", growable(classSummaryView(testClass)));
		Tab testsTab = new Tab("Tests", growable(classTestsView(testClass)));
		Tab attributesTab = new Tab("Attributes", growable(classAttributesView(testClass)));
		Tab setupsTab = new Tab("Setups", growable(classSetupsView(testClass)));
		Tab codeTab = new Tab("Code", growable(classCodeView(testClass)));
		Tab violationsTab = new Tab("Violations", classViolationsView(testClass));
		tabPane.getTabs().addAll(summaryTab, attributesTab, setupsTab, testsTab, violationsTab, codeTab);
		bindClassDetailsTabPersistence(tabPane);
		restoreClassDetailsTabSelection(tabPane);
		return tabPane;
	}

	private void bindClassDetailsTabPersistence(TabPane tabPane) {
		tabPane.getSelectionModel().selectedItemProperty().addListener((observable, previous, selected) -> {
			if (selected != null) {
				selectedClassDetailsTab = selected.getText();
			}
		});
	}

	private void restoreClassDetailsTabSelection(TabPane tabPane) {
		tabPane.getTabs().stream()
				.filter(tab -> selectedClassDetailsTab.equals(tab.getText()))
				.findFirst()
				.ifPresent(tab -> tabPane.getSelectionModel().select(tab));
	}

	private VBox classSummaryView(TestClass testClass) {
		VBox summary = new VBox(SPACING_X);
		summary.setMaxWidth(Double.MAX_VALUE);
		GridPane table = statisticsTable(List.of("Metric", "Value"), classSummaryRows(testClass));
		stretchTableHorizontally(table, 220, 120);
		summary.getChildren().add(table);
		return summary;
	}

	private List<List<String>> classSummaryRows(TestClass testClass) {
		long tests = testClass.testMethods().size();
		List<TestCodeViolation> violations = violationsForClass(testClass);
		long classLevelViolations = violations.stream().filter(violation -> violation.scope() == ViolationScope.TEST_CLASS).count();
		long methodLevelViolations = violations.stream()
				.filter(violation -> violation.scope() == ViolationScope.TEST_METHOD)
				.map(this::violationTestKey)
				.distinct()
				.count();
		return List.of(
				List.of("Attributes", formatNumber(testClass.fields().size())),
				List.of("Setups", formatNumber(setupMethodsInClass(testClass).size())),
				List.of("Tests", formatNumber(tests)),
				List.of("Helper methods", formatNumber(testClass.helperMethods().size())),
				List.of("Class-level violations", formatNumber(classLevelViolations)),
				List.of("Method-level violations", formatNumber(methodLevelViolations)));
	}

	private VBox classAttributesView(TestClass testClass) {
		VBox attributes = new VBox(SPACING_X);
		attributes.setMaxWidth(Double.MAX_VALUE);
		List<List<String>> rows = testClass.fields()
				.stream()
				.map(field -> List.of(field.name(), field.type(), field.initialization().map(statement -> statement.normalizedText()).orElse("")))
				.collect(Collectors.toList());
		if (rows.isEmpty()) {
			attributes.getChildren().add(body("This class has no attributes."));
			return attributes;
		}
		GridPane table = statisticsTable(List.of("Name", "Type", "Initialization"), rows);
		stretchTableHorizontally(table, 160, 160, 280);
		attributes.getChildren().add(table);
		return attributes;
	}

	private HBox classSetupsView(TestClass testClass) {
		List<FixtureMethod> setups = setupMethodsInClass(testClass);
		ListView<FixtureMethod> setupList = new ListView<>();
		setupList.getItems().addAll(setups);
		configureSmallList(setupList);
		setupList.setCellFactory(list -> new ListCell<>() {
			@Override
			protected void updateItem(FixtureMethod item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.name());
			}
		});

		FixtureMethod selectedSetup = resolveSelectedFixture(setups);
		selectedParsedFixture = selectedSetup;
		if (selectedSetup != null) {
			setupList.getSelectionModel().select(selectedSetup);
		}

		TextArea setupCode = monospaceTextArea(formatClassSetupMethodCode(testClass, selectedSetup));
		HBox.setHgrow(setupCode, Priority.ALWAYS);
		setupList.getSelectionModel().selectedItemProperty().addListener((observable, previous, selected) -> {
			selectedParsedFixture = selected;
			setupCode.setText(formatClassSetupMethodCode(testClass, selected));
		});

		HBox row = new HBox(SPACING_4X);
		row.setMaxWidth(Double.MAX_VALUE);
		row.setMaxHeight(Double.MAX_VALUE);
		row.getChildren().addAll(setupList, setupCode);
		HBox.setHgrow(setupList, Priority.NEVER);
		HBox.setHgrow(row, Priority.ALWAYS);
		return row;
	}

	private List<FixtureMethod> setupMethodsInClass(TestClass testClass) {
		return testClass.fixtures()
				.stream()
				.filter(fixture -> fixture.kind() == FixtureKind.BEFORE)
				.collect(Collectors.toList());
	}

	private FixtureMethod resolveSelectedFixture(List<FixtureMethod> setups) {
		if (selectedParsedFixture != null) {
			for (FixtureMethod setup : setups) {
				if (setup == selectedParsedFixture) {
					return setup;
				}
			}
		}
		return setups.isEmpty() ? null : setups.get(0);
	}

	private long excludedTestsInClass(TestClass testClass) {
		boolean classExcluded = parsedTestClasses.violations()
				.stream()
				.anyMatch(violation -> violation.scope() == ViolationScope.TEST_CLASS
						&& violation.testClassName().equals(testClass.qualifiedName()));
		if (classExcluded) {
			return testClass.testMethods().size();
		}
		Set<String> excludedMethods = parsedTestClasses.violations()
				.stream()
				.filter(violation -> violation.scope() == ViolationScope.TEST_METHOD
						&& violation.testClassName().equals(testClass.qualifiedName()))
				.map(this::violationTestKey)
				.collect(Collectors.toSet());
		return testClass.testMethods()
				.stream()
				.filter(testMethod -> excludedMethods.contains(testKey(testClass, testMethod)))
				.count();
	}

	private String formatClassSetupMethodCode(TestClass testClass, FixtureMethod setup) {
		if (setup == null || loadedCodeFiles == null) {
			return "";
		}
		return violationContextExtractor.extractMethodCode(loadedCodeFiles, testClass.qualifiedName(), setup.name()).orElse("");
	}

	private HBox classTestsView(TestClass testClass) {
		ListView<TestMethod> testList = new ListView<>();
		testList.getItems().addAll(testClass.testMethods());
		configureSmallList(testList);
		testList.setCellFactory(list -> new ListCell<>() {
			@Override
			protected void updateItem(TestMethod item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.name());
			}
		});

		TestMethod selectedTestMethod = resolveSelectedTestMethod(testClass);
		selectedParsedTestMethod = selectedTestMethod;
		if (selectedTestMethod != null) {
			testList.getSelectionModel().select(selectedTestMethod);
		}

		TextArea methodCode = monospaceTextArea(formatClassTestMethodCode(testClass, selectedTestMethod));
		HBox.setHgrow(methodCode, Priority.ALWAYS);
		testList.getSelectionModel().selectedItemProperty().addListener((observable, previous, selected) -> {
			selectedParsedTestMethod = selected;
			methodCode.setText(formatClassTestMethodCode(testClass, selected));
		});

		HBox row = new HBox(SPACING_4X);
		row.setMaxWidth(Double.MAX_VALUE);
		row.setMaxHeight(Double.MAX_VALUE);
		row.getChildren().addAll(testList, methodCode);
		HBox.setHgrow(testList, Priority.NEVER);
		HBox.setHgrow(row, Priority.ALWAYS);
		return row;
	}

	private TextArea classCodeView(TestClass testClass) {
		String code = loadedCodeFiles == null
				? ""
				: violationContextExtractor.extractClassCode(loadedCodeFiles, testClass.qualifiedName()).orElse("");
		TextArea codeArea = monospaceTextArea(code);
		codeArea.setMaxWidth(Double.MAX_VALUE);
		codeArea.setMaxHeight(Double.MAX_VALUE);
		return codeArea;
	}

	private HBox classViolationsView(TestClass testClass) {
		List<TestCodeViolation> violations = violationsForClass(testClass);
		TestCodeViolation selected = resolveClassViolationSelection(violations);
		selectedClassViolation = selected;
		return violationInspectionView(violations, selected, violation -> selectedClassViolation = violation);
	}

	private VBox growable(javafx.scene.Node content) {
		VBox container = new VBox(content);
		container.setMaxWidth(Double.MAX_VALUE);
		container.setMaxHeight(Double.MAX_VALUE);
		VBox.setVgrow(content, Priority.ALWAYS);
		return container;
	}

	private void configureLargeList(ListView<?> listView) {
		configureListWidth(listView, LIST_WIDTH_LARGE);
	}

	private void configureSmallList(ListView<?> listView) {
		configureListWidth(listView, LIST_WIDTH_SMALL);
	}

	private void configureListWidth(ListView<?> listView, int width) {
		listView.setPrefWidth(width);
		listView.setMinWidth(width);
		listView.setMaxWidth(width);
	}

	private TestMethod resolveSelectedTestMethod(TestClass testClass) {
		if (selectedParsedTestMethod != null) {
			for (TestMethod testMethod : testClass.testMethods()) {
				if (testMethod == selectedParsedTestMethod) {
					return testMethod;
				}
			}
		}
		return testClass.testMethods().isEmpty() ? null : testClass.testMethods().get(0);
	}

	private TestCodeViolation resolveClassViolationSelection(List<TestCodeViolation> violations) {
		if (selectedClassViolation != null && violations.stream().anyMatch(violation -> violation == selectedClassViolation)) {
			return selectedClassViolation;
		}
		return violations.isEmpty() ? null : violations.get(0);
	}

	private List<TestCodeViolation> violationsForClass(TestClass testClass) {
		return parsedTestClasses.violations()
				.stream()
				.filter(violation -> violation.testClassName().equals(testClass.qualifiedName()))
				.collect(Collectors.toList());
	}

	private String formatClassTestMethodCode(TestClass testClass, TestMethod testMethod) {
		if (testMethod == null || loadedCodeFiles == null) {
			return "";
		}
		return violationContextExtractor.extractMethodCode(loadedCodeFiles, testClass.qualifiedName(), testMethod.name()).orElse("");
	}

	private HBox decompositionViolationsView() {
		return violationInspectionView(parsedTestClasses.violations(), selectedViolation, violation -> selectedViolation = violation);
	}

	private HBox violationInspectionView(List<TestCodeViolation> violations, TestCodeViolation selectedViolation, Consumer<TestCodeViolation> onSelectionChanged) {
		ListView<TestCodeViolation> violationList = new ListView<>();
		violationList.getItems().addAll(violations);
		configureLargeList(violationList);
		violationList.setCellFactory(list -> new ListCell<>() {
			@Override
			protected void updateItem(TestCodeViolation item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.description());
			}
		});
		if (selectedViolation != null) {
			violationList.getSelectionModel().select(selectedViolation);
		}

		Label violationIdentifier = body(formatViolationIdentifier(selectedViolation));
		violationIdentifier.setStyle(violationIdentifier.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");

		TextArea violationCode = monospaceTextArea(formatViolationCode(selectedViolation));
		TextArea contextCode = monospaceTextArea(formatViolationContext(selectedViolation));
		SplitPane violationSplit = new SplitPane(violationCode, contextCode);
		violationSplit.setOrientation(Orientation.VERTICAL);
		violationSplit.setDividerPositions(0.3);
		violationSplit.setMaxWidth(Double.MAX_VALUE);
		violationSplit.setMaxHeight(Double.MAX_VALUE);

		VBox violationDetails = new VBox(SPACING_X);
		violationDetails.getChildren().addAll(violationIdentifier, violationSplit);
		VBox.setVgrow(violationSplit, Priority.ALWAYS);
		HBox.setHgrow(violationDetails, Priority.ALWAYS);
		violationList.getSelectionModel().selectedItemProperty().addListener((observable, previous, selected) -> {
			onSelectionChanged.accept(selected);
			violationIdentifier.setText(formatViolationIdentifier(selected));
			violationCode.setText(formatViolationCode(selected));
			contextCode.setText(formatViolationContext(selected));
		});

		HBox row = new HBox(SPACING_4X);
		row.setMaxWidth(Double.MAX_VALUE);
		row.setMaxHeight(Double.MAX_VALUE);
		row.getChildren().addAll(violationList, violationDetails);
		HBox.setHgrow(violationList, Priority.NEVER);
		HBox.setHgrow(violationDetails, Priority.ALWAYS);
		HBox.setHgrow(row, Priority.ALWAYS);
		return row;
	}

	private String formatViolationIdentifier(TestCodeViolation violation) {
		if (violation == null) {
			return "No violations to inspect.";
		}
		return violation.testClassName() + violation.testMethodName().map(method -> "." + method).orElse("");
	}

	private String formatViolationCode(TestCodeViolation violation) {
		if (violation == null) {
			return "";
		}
		return violation.codeSnippet();
	}

	private String formatViolationContext(TestCodeViolation violation) {
		if (violation == null || loadedCodeFiles == null) {
			return "";
		}
		return violationContextExtractor.extractContext(loadedCodeFiles, violation).orElse("");
	}

	private TextArea monospaceTextArea(String text) {
		TextArea area = new TextArea(text);
		area.setEditable(false);
		area.setWrapText(false);
		area.setStyle(FONT_FAMILY + "-fx-font-family: 'Monospaced'; -fx-font-size: 13px;");
		return area;
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
		configureLargeList(fileList);
		if (selectedCodeFile != null) {
			fileList.getSelectionModel().select(selectedCodeFile);
		}
		fileList.getSelectionModel().selectedItemProperty().addListener((observable, previous, selected) -> {
			selectedCodeFile = selected;
			refreshParsingFileContent();
		});

		String contentText = selectedCodeFile == null
				? "Select a loaded file to inspect its content."
				: selectedCodeFile.content();
		TextArea fileContent = new TextArea(contentText);
		fileContent.setEditable(false);
		fileContent.setWrapText(false);
		fileContent.setStyle(FONT_FAMILY + "-fx-font-family: 'Monospaced'; -fx-font-size: 13px;");
		HBox.setHgrow(fileContent, Priority.ALWAYS);
		parsingFileContentArea = fileContent;

		HBox loadedFiles = new HBox(SPACING_4X);
		loadedFiles.getChildren().addAll(fileList, fileContent);
		HBox.setHgrow(fileList, Priority.NEVER);
		VBox.setVgrow(loadedFiles, Priority.ALWAYS);
		return loadedFiles;
	}

	private void refreshParsingFileContent() {
		if (parsingFileContentArea == null) {
			renderContentArea();
			return;
		}
		parsingFileContentArea.setText(selectedCodeFile == null
				? "Select a loaded file to inspect its content."
				: selectedCodeFile.content());
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
		configureLargeList(testList);
		if (selectedDecomposedTestCase != null) {
			testList.getSelectionModel().select(selectedDecomposedTestCase);
		}
		testList.getSelectionModel().selectedItemProperty().addListener((observable, previous, selected) -> {
			selectedDecomposedTestCase = selected;
			refreshMeasurementTestBody();
		});

		String bodyText = selectedDecomposedTestCase == null ? "Select a test to inspect its decomposed body." : formatDecomposedTestBody(selectedDecomposedTestCase);
		TextArea bodyArea = new TextArea(bodyText);
		bodyArea.setEditable(false);
		bodyArea.setWrapText(false);
		bodyArea.setStyle(FONT_FAMILY + "-fx-font-family: 'Monospaced'; -fx-font-size: 13px;");
		HBox.setHgrow(bodyArea, Priority.ALWAYS);
		measurementTestBodyArea = bodyArea;

		HBox row = new HBox(SPACING_4X);
		row.getChildren().addAll(testList, bodyArea);
		HBox.setHgrow(testList, Priority.NEVER);
		VBox.setVgrow(row, Priority.ALWAYS);
		return row;
	}

	private void refreshMeasurementTestBody() {
		if (measurementTestBodyArea == null) {
			renderContentArea();
			return;
		}
		measurementTestBodyArea.setText(selectedDecomposedTestCase == null
				? "Select a test to inspect its decomposed body."
				: formatDecomposedTestBody(selectedDecomposedTestCase));
	}

	private VBox similarityMatrixView() {
		refreshSimilaritySelectionControls();
		VBox matrixView = new VBox(SPACING_4X);
		if (similarityMatrix.size() == 0) {
			matrixView.getChildren().add(similaritySelectionControls());
			return matrixView;
		}
		HBox rankingAndCode = new HBox(SPACING_4X);
		VBox ranking = rankedSimilarityList();

		VBox sourceColumn = new VBox(SPACING_X);
		Label sourceTitle = body("Source test");
		sourceTitle.setStyle(sourceTitle.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		sourceTestCombo.setPrefWidth(320);
		sourceTestCombo.setMaxWidth(Double.MAX_VALUE);
		TextArea sourceCode = clusteringCodeTextArea(sourceTestCombo.getSelectionModel().getSelectedItem(), true);
		sourceColumn.getChildren().addAll(sourceTitle, sourceTestCombo, sourceCode);
		VBox.setVgrow(sourceCode, Priority.ALWAYS);

		VBox targetColumn = new VBox(SPACING_X);
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
		HBox row = new HBox(SPACING_4X);
		ListView<Integer> levelList = new ListView<>();
		for (int index = 0; index < clusteringLevels.size(); index++) {
			levelList.getItems().add(index);
		}
		configureSmallList(levelList);
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
		Button topLevelButton = new Button(refactoringTopLevelButtonLabel());
		topLevelButton.setStyle(secondaryButtonStyle());
		topLevelButton.setMaxWidth(Double.MAX_VALUE);
		topLevelButton.setDisable(topRefactoringLevelsComputing
				|| cachedTopRefactoringLevelIndices == null
				|| cachedTopRefactoringLevelIndices.isEmpty());
		topLevelButton.setOnAction(event -> cycleTopRefactoringLevel());
		refactoringTopLevelButton = topLevelButton;
		VBox levelColumn = new VBox(SPACING_X);
		Label levelTitle = body("Level");
		levelTitle.setStyle(levelTitle.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		levelColumn.getChildren().addAll(levelTitle, topLevelButton, levelList);
		VBox.setVgrow(levelList, Priority.ALWAYS);
		ScrollPane clustersPane = new ScrollPane();
		clustersPane.setContent(refactoringLevelClustersPanel(selectedRefactoringLevelIndex, clustersPane));
		clustersPane.setFitToWidth(true);
		clustersPane.setStyle(
				"-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent; -fx-border-width: 0;");
		refactoringLevelList = levelList;
		refactoringClustersPane = clustersPane;
		levelList.getSelectionModel().selectedIndexProperty().addListener((observable, previous, next) -> {
			if (next == null || next.intValue() < 0 || next.intValue() == selectedRefactoringLevelIndex) {
				return;
			}
			topRefactoringRankCycleIndex = 0;
			updateRefactoringTopLevelButton();
			selectedRefactoringLevelIndex = next.intValue();
			clustersPane.setContent(refactoringLevelClustersPanel(selectedRefactoringLevelIndex, clustersPane));
		});
		HBox.setHgrow(clustersPane, Priority.ALWAYS);
		row.getChildren().addAll(levelColumn, clustersPane);
		row.setFillHeight(true);
		Platform.runLater(() -> levelList.scrollTo(selectedRefactoringLevelIndex));
		return row;
	}

	private void scheduleBestRefactoringLevelComputation() {
		cachedTopRefactoringLevelIndices = null;
		topRefactoringRankCycleIndex = 0;
		if (clusteringLevels == null || clusteringLevels.isEmpty()) {
			topRefactoringLevelsComputing = false;
			updateRefactoringTopLevelButton();
			return;
		}
		topRefactoringLevelsComputing = true;
		updateRefactoringTopLevelButton();
		List<ClusteringLevel> levels = List.copyOf(clusteringLevels);
		Thread worker = new Thread(() -> {
			List<Integer> topLevelIndices = computeTopRefactoringLevelIndices(levels, TOP_REFACTORING_LEVEL_LIMIT);
			Platform.runLater(() -> {
				cachedTopRefactoringLevelIndices = topLevelIndices;
				topRefactoringLevelsComputing = false;
				updateRefactoringTopLevelButton();
			});
		}, "top-refactoring-levels");
		worker.setDaemon(true);
		worker.start();
	}

	private static List<Integer> computeTopRefactoringLevelIndices(List<ClusteringLevel> levels, int limit) {
		ImplicitSetupTestClassRefactorer refactorer = new ImplicitSetupTestClassRefactorer();
		List<int[]> rankedLevels = new ArrayList<>();
		for (int levelIndex = 0; levelIndex < levels.size(); levelIndex++) {
			TestClassMetrics metrics = TestClassMetricsCalculator.forSetupCode(
					refactorer.refactor(new TestCaseClusters(levels.get(levelIndex).clusters())).testClasses());
			rankedLevels.add(new int[] { levelIndex, metrics.duplicatedStatements() });
		}
		rankedLevels.sort(Comparator
				.comparingInt((int[] entry) -> entry[1])
				.thenComparingInt(entry -> entry[0]));
		int resultSize = Math.min(limit, rankedLevels.size());
		List<Integer> topLevelIndices = new ArrayList<>(resultSize);
		for (int index = 0; index < resultSize; index++) {
			topLevelIndices.add(rankedLevels.get(index)[0]);
		}
		return topLevelIndices;
	}

	private void cycleTopRefactoringLevel() {
		if (cachedTopRefactoringLevelIndices == null || cachedTopRefactoringLevelIndices.isEmpty()) {
			return;
		}
		int levelIndex = cachedTopRefactoringLevelIndices.get(topRefactoringRankCycleIndex);
		selectRefactoringLevel(levelIndex);
		topRefactoringRankCycleIndex = (topRefactoringRankCycleIndex + 1) % cachedTopRefactoringLevelIndices.size();
		updateRefactoringTopLevelButton();
	}

	private String refactoringTopLevelButtonLabel() {
		if (cachedTopRefactoringLevelIndices == null || cachedTopRefactoringLevelIndices.isEmpty()) {
			return "Top 1";
		}
		return "Top " + (topRefactoringRankCycleIndex + 1);
	}

	private void selectRefactoringLevel(int levelIndex) {
		if (clusteringLevels == null || levelIndex < 0 || levelIndex >= clusteringLevels.size()) {
			return;
		}
		selectedRefactoringLevelIndex = levelIndex;
		if (refactoringLevelList != null) {
			refactoringLevelList.getSelectionModel().select(levelIndex);
			int scrollToIndex = levelIndex;
			Platform.runLater(() -> refactoringLevelList.scrollTo(scrollToIndex));
		}
		if (refactoringClustersPane != null) {
			refactoringClustersPane.setContent(refactoringLevelClustersPanel(levelIndex, refactoringClustersPane));
		}
	}

	private void updateRefactoringTopLevelButton() {
		if (refactoringTopLevelButton == null) {
			return;
		}
		refactoringTopLevelButton.setText(refactoringTopLevelButtonLabel());
		refactoringTopLevelButton.setDisable(topRefactoringLevelsComputing
				|| cachedTopRefactoringLevelIndices == null
				|| cachedTopRefactoringLevelIndices.isEmpty());
	}

	/** Refactoring tab: per-level cluster tiles for the selected merge level. */
	private VBox refactoringLevelClustersPanel(int levelIndex, ScrollPane clustersScroll) {
		return levelClustersPanel(levelIndex, clustersScroll);
	}

	private VBox levelClustersPanel(int levelIndex, ScrollPane clustersScroll) {
		ClusteringLevel level = clusteringLevels.get(levelIndex);
		VBox panel = new VBox(SPACING_X);
		// FlowPane column count is not fixed: prefWrapLength tracks the scroll viewport width.
		// Each cluster tile uses pref/max width 380 with hgap SPACING_X, so columns ≈ floor((wrap + SPACING_X) / (380 + SPACING_X))
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
		Optional<MergeCandidate> merge = levelIndex > 0 ? level.acceptedMerge() : Optional.empty();
		FlowPane clusterTiles = new FlowPane(Orientation.HORIZONTAL, SPACING_X, SPACING_X);
		clusterTiles.setRowValignment(VPos.TOP);
		clusterTiles.prefWrapLengthProperty().bind(usableWrapWidth);
		clusterTiles.maxWidthProperty().bind(usableWrapWidth);
		List<TestCaseCluster> clusters = level.clusters();
		int mergedClusterIndex = -1;
		if (merge.isPresent()) {
			Set<Integer> mergedKeys = clusterKey(merge.get().mergedCluster());
			for (int i = 0; i < clusters.size(); i++) {
				if (clusterKey(clusters.get(i)).equals(mergedKeys)) {
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
		Map<Set<Integer>, Double> formationSimilaritiesByCluster = clusterFormationSimilaritiesByCluster(clusteringLevels, levelIndex);
		for (int i = 0; i < orderedClusters.size(); i++) {
			boolean emphasizeMerge = mergedClusterIndex >= 0 && i == 0;
			TestCaseCluster tileCluster = orderedClusters.get(i);
			Optional<Double> formationSimilarity = clusterFormationSimilarity(tileCluster, formationSimilaritiesByCluster);
			clusterTiles.getChildren().add(clusterBlockView(tileCluster, i, emphasizeMerge, formationSimilarity));
		}
		panel.getChildren().add(clusterTiles);
		return panel;
	}

	static Map<Set<Integer>, Double> clusterFormationSimilaritiesByCluster(List<ClusteringLevel> levels, int levelIndex) {
		Map<Set<Integer>, Double> similaritiesByCluster = new HashMap<>();
		for (int i = 1; i <= Math.min(levelIndex, levels.size() - 1); i++) {
			Optional<MergeCandidate> merge = levels.get(i).acceptedMerge();
			merge.ifPresent(candidate -> similaritiesByCluster.put(clusterKey(candidate.mergedCluster()), candidate.similarity()));
		}
		return similaritiesByCluster;
	}

	static Optional<Double> clusterFormationSimilarity(TestCaseCluster cluster, Map<Set<Integer>, Double> similaritiesByCluster) {
		Double formationSimilarity = similaritiesByCluster.get(clusterKey(cluster));
		if (formationSimilarity != null) {
			return Optional.of(formationSimilarity);
		}
		if (cluster.size() == 1) {
			return Optional.of(1.0);
		}
		return Optional.empty();
	}

	static Set<Integer> clusterKey(TestCaseCluster cluster) {
		return Set.copyOf(cluster.testCaseIndexes());
	}

	private VBox clusterBlockView(TestCaseCluster cluster, int paletteIndex, boolean mergeEmphasis, Optional<Double> formationSimilarity) {
		VBox block = new VBox(SPACING_X);
		block.setAlignment(Pos.TOP_LEFT);
		block.setMaxHeight(Region.USE_PREF_SIZE);
		block.setPadding(new Insets(SPACING_X));
		block.setPrefWidth(Region.USE_COMPUTED_SIZE);
		block.setMinWidth(Region.USE_PREF_SIZE);
		block.setMaxWidth(Region.USE_PREF_SIZE);
		String palette = CLUSTER_BLOCK_STYLES[paletteIndex % CLUSTER_BLOCK_STYLES.length];
		String emphasis = mergeEmphasis ? MERGED_CLUSTER_BLOCK_EMPHASIS : "";
		block.setStyle(FONT_FAMILY + palette + emphasis);
		formationSimilarity.ifPresent(similarity -> {
			String scoreName = setupExtractionPotentialSelected() ? "Setup extraction potential" : "Similarity";
			Label similarityLine = body(scoreName + ": " + formatMeasurementScore(similarity));
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

	private HBox refactoredTestClassesView() {
		ListView<TestClass> classList = new ListView<>();
		classList.getItems().addAll(refactoredTestClasses.testClasses());
		configureLargeList(classList);
		classList.setCellFactory(list -> new ListCell<>() {
			@Override
			protected void updateItem(TestClass item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.name());
			}
		});
		if (selectedRefactoredTestClass != null) {
			classList.getSelectionModel().select(selectedRefactoredTestClass);
		}

		TextArea codeArea = new TextArea(renderSelectedRefactoredTestClassCode());
		codeArea.setEditable(false);
		codeArea.setWrapText(false);
		codeArea.setStyle(FONT_FAMILY + "-fx-font-family: 'Monospaced'; -fx-font-size: 13px;");
		HBox.setHgrow(codeArea, Priority.ALWAYS);
		classList.getSelectionModel().selectedItemProperty().addListener((observable, previous, selected) -> {
			selectedRefactoredTestClass = selected;
			codeArea.setText(renderSelectedRefactoredTestClassCode());
		});

		HBox row = new HBox(SPACING_4X);
		row.getChildren().addAll(classList, codeArea);
		HBox.setHgrow(classList, Priority.NEVER);
		VBox.setVgrow(row, Priority.ALWAYS);
		return row;
	}

	private String renderSelectedRefactoredTestClassCode() {
		return selectedRefactoredTestClass == null
				? "Select a refactored test class to inspect its code."
				: new JunitTestClassRenderer().render(selectedRefactoredTestClass);
	}

	private HBox similaritySelectionControls() {
		HBox controls = new HBox(SPACING_4X);

		VBox source = new VBox(SPACING_X);
		Label sourceTitle = body("Source test");
		sourceTitle.setStyle(sourceTitle.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");
		sourceTestCombo.setPrefWidth(320);
		sourceTestCombo.setMaxWidth(Double.MAX_VALUE);
		source.getChildren().addAll(sourceTitle, sourceTestCombo);
		HBox.setHgrow(source, Priority.ALWAYS);

		VBox target = new VBox(SPACING_X);
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
		VBox ranking = new VBox(SPACING_X);
		ranking.setPrefWidth(LIST_WIDTH_SMALL);
		ranking.setMinWidth(LIST_WIDTH_SMALL);
		ranking.setMaxWidth(LIST_WIDTH_SMALL);
		Button order = new Button(rankedSimilarityDescending ? "Highest" : "Lowest");
		order.setStyle(secondaryButtonStyle());
		order.setMaxWidth(Double.MAX_VALUE);
		order.setOnAction(event -> {
			rankedSimilarityDescending = !rankedSimilarityDescending;
			renderContentArea();
		});

		ListView<SimilarityRankingItem> list = new ListView<>();
		configureSmallList(list);
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

	private boolean setupExtractionPotentialSelected() {
		return "SEP".equals(metricCombo.getSelectionModel().getSelectedItem());
	}

	private String formatMeasurementScore(double score) {
		return setupExtractionPotentialSelected() ? String.format(Locale.ROOT, "%.0f", score) : formatSimilarity(score);
	}

	private static String formatDuplicationRate(double rate) {
		return String.format(Locale.ROOT, "%.1f%%", rate * 100.0);
	}

	private static String formatNumber(int value) {
		return Integer.toString(value);
	}

	private static String formatNumber(long value) {
		return Long.toString(value);
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

	private String stageButtonStyle(PipelineStage stage, PipelineStageStatus status, boolean selected) {
		if (stage == PipelineStage.ANALYTICS) {
			return topButtonStyle(selected ? "#6d28d9" : "#ddd6fe", selected ? "#ffffff" : "#4c1d95");
		}
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

	private final class SimilarityRankingItem {

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
			return formatMeasurementScore(similarity);
		}
	}
}
