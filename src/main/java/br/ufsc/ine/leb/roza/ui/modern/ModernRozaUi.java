package br.ufsc.ine.leb.roza.ui.modern;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DefaultTestCaseDecomposer;
import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCaseDecomposer;
import br.ufsc.ine.leb.roza.core.modern.loading.CodeFile;
import br.ufsc.ine.leb.roza.core.modern.loading.FileSystemCodeFileLoader;
import br.ufsc.ine.leb.roza.core.modern.loading.LoadedCodeFiles;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.JunitTestClassParser;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsingException;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClassParser;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestCodeViolation;
import br.ufsc.ine.leb.roza.core.modern.parsing.UnsupportedFeatureException;
import br.ufsc.ine.leb.roza.core.modern.parsing.ViolationScope;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

public final class ModernRozaUi extends Application {

	private static final String FONT_FAMILY = "-fx-font-family: 'Arial';";
	private static final int CONFIGURATION_INNER_SPACING = 10;
	private static final Insets MARGIN_AFTER_CONFIGURATION_GROUP = new Insets(0, 0, 12, 0);
	private static final Insets MARGIN_SECTION_TITLE_AFTER_GROUP = new Insets(4, 0, 0, 0);
	private static final Insets MARGIN_ACTION_BUTTON_TOP = new Insets(12, 0, 0, 0);

	private final PipelineState pipelineState;
	private final HBox pipelineBar;
	private final VBox configurationSidebar;
	private final VBox contentArea;
	private final CheckBox recursiveLoading;
	private final CheckBox javaExtension;
	private final CheckBox txtExtension;
	private final ComboBox<String> parserImplementationCombo;
	private final ComboBox<String> decomposerImplementationCombo;
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

	public ModernRozaUi() {
		pipelineState = new PipelineState();
		pipelineBar = new HBox(8);
		configurationSidebar = new VBox(14);
		contentArea = new VBox(14);
		recursiveLoading = new CheckBox("Enabled");
		javaExtension = new CheckBox(".java");
		txtExtension = new CheckBox(".txt");
		recursiveLoading.setSelected(true);
		javaExtension.setSelected(true);
		txtExtension.setSelected(false);

		parserImplementationCombo = new ComboBox<>();
		parserImplementationCombo.getItems().add("JUnit");
		parserImplementationCombo.getSelectionModel().selectFirst();
		parserImplementationCombo.setStyle(singleLineComboBoxStyle());

		decomposerImplementationCombo = new ComboBox<>();
		decomposerImplementationCombo.getItems().add("Default");
		decomposerImplementationCombo.getSelectionModel().selectFirst();
		decomposerImplementationCombo.setStyle(singleLineComboBoxStyle());

		selectedViolationIndex = -1;
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

		Button actionButton = new Button(actionButtonText(selectedStage));
		actionButton.setMaxWidth(Double.MAX_VALUE);
		actionButton.setDisable(!stageActionEnabled(selectedStage));
		actionButton.setStyle(primaryButtonStyle());
		actionButton.setOnAction(event -> {
			runStage(selectedStage);
		});

		configurationSidebar.getChildren().addAll(configuration, actionButton);
		VBox.setMargin(actionButton, MARGIN_ACTION_BUTTON_TOP);
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
		VBox configuration = new VBox(CONFIGURATION_INNER_SPACING);
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

	private VBox parsingConfiguration() {
		VBox configuration = new VBox(CONFIGURATION_INNER_SPACING);
		configuration.setPadding(new Insets(0, 0, 4, 0));

		Label parserTitle = body("Parser implementation");
		parserTitle.setStyle(parserTitle.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");

		parserImplementationCombo.setMaxWidth(Double.MAX_VALUE);

		configuration.getChildren().addAll(parserTitle, parserImplementationCombo);
		return configuration;
	}

	private VBox decompositionConfiguration() {
		VBox configuration = new VBox(CONFIGURATION_INNER_SPACING);
		configuration.setPadding(new Insets(0, 0, 4, 0));

		Label decomposerTitle = body("Decomposer implementation");
		decomposerTitle.setStyle(decomposerTitle.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");

		decomposerImplementationCombo.setMaxWidth(Double.MAX_VALUE);

		configuration.getChildren().addAll(decomposerTitle, decomposerImplementationCombo);
		return configuration;
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
		return true;
	}

	private void runStage(PipelineStage selectedStage) {
		if (selectedStage == PipelineStage.LOADING) {
			loadCodeFiles();
		} else if (selectedStage == PipelineStage.PARSING) {
			runParsing();
		} else if (selectedStage == PipelineStage.DECOMPOSITION) {
			runDecomposition();
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
			pipelineState.runSelectedStage();
		} catch (RuntimeException exception) {
			parsingError = exception.getMessage() != null ? exception.getMessage() : exception.toString();
			selectCodeFileForParseFailure(exception);
			parsedTestClasses = null;
			selectedViolationIndex = -1;
			decomposedTestCases = null;
			decompositionError = null;
			selectedDecomposedTestCase = null;
		}
		render();
	}

	private void runDecomposition() {
		try {
			TestCaseDecomposer decomposer = new DefaultTestCaseDecomposer();
			decomposedTestCases = decomposer.decompose(parsedTestClasses);
			decompositionError = null;
			pipelineState.runSelectedStage();
		} catch (RuntimeException exception) {
			decompositionError = exception.getMessage() != null ? exception.getMessage() : exception.toString();
			decomposedTestCases = null;
		}
		render();
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

	private static String formatDecomposedTestBody(TestCase testCase) {
		return testCase.body().statements().stream().map(CodeStatement::originalText).collect(Collectors.joining("\n"));
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
		return FONT_FAMILY + "-fx-background-color: #ffffff; -fx-text-fill: #333333; -fx-background-radius: 6; -fx-padding: 10; -fx-font-weight: bold;";
	}
}
