package br.ufsc.ine.leb.roza.ui.modern;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.core.modern.loading.CodeFile;
import br.ufsc.ine.leb.roza.core.modern.loading.FileSystemCodeFileLoader;
import br.ufsc.ine.leb.roza.core.modern.loading.LoadedCodeFiles;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

public final class ModernRozaUi extends Application {

	private static final String FONT_FAMILY = "-fx-font-family: 'Arial';";
	private static final int CONFIGURATION_INNER_SPACING = 10;
	private static final Insets MARGIN_AFTER_CONFIGURATION_GROUP = new Insets(0, 0, 32, 0);
	private static final Insets MARGIN_SECTION_TITLE_AFTER_GROUP = new Insets(8, 0, 0, 0);
	private static final Insets MARGIN_ACTION_BUTTON_TOP = new Insets(28, 0, 0, 0);

	private final PipelineState pipelineState;
	private final HBox pipelineBar;
	private final VBox configurationSidebar;
	private final VBox contentArea;
	private final CheckBox recursiveLoading;
	private final CheckBox javaExtension;
	private final CheckBox txtExtension;
	private final ComboBox<String> parserImplementationCombo;
	private final RadioButton unsupportedFeaturePolicySafe;
	private final RadioButton unsupportedFeaturePolicyUnsafe;
	private final ToggleGroup unsupportedFeaturePolicyGroup;
	private Path sourceFolder;
	private LoadedCodeFiles loadedCodeFiles;
	private CodeFile selectedCodeFile;
	private String loadingError;

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

		unsupportedFeaturePolicyGroup = new ToggleGroup();
		unsupportedFeaturePolicySafe = new RadioButton("Safe");
		unsupportedFeaturePolicyUnsafe = new RadioButton("Unsafe");
		unsupportedFeaturePolicySafe.setToggleGroup(unsupportedFeaturePolicyGroup);
		unsupportedFeaturePolicyUnsafe.setToggleGroup(unsupportedFeaturePolicyGroup);
		unsupportedFeaturePolicySafe.setSelected(true);
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
		VBox.setMargin(selectedFolder, new Insets(4, 0, 32, 0));

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
		VBox.setMargin(parserImplementationCombo, MARGIN_AFTER_CONFIGURATION_GROUP);

		Label policyTitle = body("Unsupported feature policy");
		VBox.setMargin(policyTitle, MARGIN_SECTION_TITLE_AFTER_GROUP);
		policyTitle.setStyle(policyTitle.getStyle() + "-fx-font-weight: bold; -fx-text-fill: #333333;");

		VBox policyRadios = new VBox(8);
		policyRadios.getChildren().addAll(unsupportedFeaturePolicySafe, unsupportedFeaturePolicyUnsafe);

		configuration.getChildren().addAll(parserTitle, parserImplementationCombo, policyTitle, policyRadios);
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
		return true;
	}

	private void runStage(PipelineStage selectedStage) {
		if (selectedStage == PipelineStage.LOADING) {
			loadCodeFiles();
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
			pipelineState.runSelectedStage();
		} catch (RuntimeException exception) {
			loadedCodeFiles = null;
			selectedCodeFile = null;
			loadingError = exception.getMessage();
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
			contentArea.getChildren().add(loadedFilesView());
		} else {
			contentArea.getChildren().add(body(selectedStage.previousStageDataDescription()));
		}
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
