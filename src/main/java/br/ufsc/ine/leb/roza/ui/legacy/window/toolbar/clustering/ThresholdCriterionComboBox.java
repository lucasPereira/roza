package br.ufsc.ine.leb.roza.ui.legacy.window.toolbar.clustering;

import java.util.List;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.ui.legacy.Hub;
import br.ufsc.ine.leb.roza.ui.legacy.Manager;
import br.ufsc.ine.leb.roza.ui.legacy.UiComponent;
import br.ufsc.ine.leb.roza.ui.legacy.shared.ComboBoxBuilder;

public class ThresholdCriterionComboBox implements UiComponent {

	private final ClusteringTab toolbar;
	private JComboBox<String> combo;

	public ThresholdCriterionComboBox(ClusteringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		ComboBoxBuilder builder = new ComboBoxBuilder("Theshold Criterion");
		builder.add("Level Based Criterion", hub::selectLevelBasedCriterionPublish);
		builder.add("Test per Class Criterion", hub::selectTestsPerClassCriterionPublish);
		builder.add("Similarity Based Criterion", hub::selectSimilarityBasedCriterionPublish);
		builder.add("Never stop", hub::selectNeverStopCriterionPublish);
		combo = builder.build();
		toolbar.addComponent(combo);
	}

	@Override
	public void addChildren(List<UiComponent> children) {}

	@Override
	public void start() {
		combo.setSelectedIndex(3);
	}

}
