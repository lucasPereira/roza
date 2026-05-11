package br.ufsc.ine.leb.roza.ui.modern;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public final class ModernRozaUi extends Application {

	private static final String FONT_FAMILY = "-fx-font-family: 'Arial';";

	private final PipelineState pipelineState;
	private final HBox pipelineBar;
	private final VBox configurationSidebar;
	private final VBox contentArea;

	public ModernRozaUi() {
		pipelineState = new PipelineState();
		pipelineBar = new HBox(8);
		configurationSidebar = new VBox(14);
		contentArea = new VBox(14);
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

		VBox configuration = new VBox(10);
		for (String item : selectedStage.configurationItems()) {
			configuration.getChildren().add(configurationRow(item));
		}

		Button actionButton = new Button(actionButtonText(selectedStage));
		actionButton.setMaxWidth(Double.MAX_VALUE);
		actionButton.setDisable(!pipelineState.selectedStageCanRun());
		actionButton.setStyle(primaryButtonStyle());
		actionButton.setOnAction(event -> {
			pipelineState.runSelectedStage();
			render();
		});

		configurationSidebar.getChildren().addAll(configuration, actionButton);
	}

	private void renderContentArea() {
		PipelineStage selectedStage = pipelineState.selectedStage();
		contentArea.getChildren().clear();
		contentArea.setPadding(new Insets(24));
		contentArea.setStyle(FONT_FAMILY + "-fx-background-color: #f4f6f8;");

		contentArea.getChildren().add(body(selectedStage.previousStageDataDescription()));
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
}
