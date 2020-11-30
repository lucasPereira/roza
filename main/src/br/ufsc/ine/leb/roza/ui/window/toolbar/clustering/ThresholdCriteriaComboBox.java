package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.shared.ComboBoxBuilder;

public class ThresholdCriteriaComboBox implements UiComponent {

	private Hub hub;
	private ClusteringTab toolbar;

	public ThresholdCriteriaComboBox(Hub hub, ClusteringTab toolbar) {
		this.hub = hub;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JComboBox<String> combo = new ComboBoxBuilder("Theshold Criteria").add("Level Based Criteria", () -> {
			hub.selectLevelBasedCriteriaPublish();
		}).add("Test Per Class Criteria", () -> {
			hub.selectTestsPerClassCriteriaPublish();
		}).add("Similarity Based Criteria", () -> {
			hub.selectSimilarityBasedCriteriaPublish();
		}).build();
		combo.setSelectedIndex(2);
		hub.loadTestClassesSubscribe(classes -> combo.setEnabled(false));
		hub.extractTestCasesSubscribe(testCases -> combo.setEnabled(false));
		hub.measureTestsSubscribe(similarityReport -> combo.setEnabled(true));
		toolbar.addComponent(combo);
	}

	@Override
	public void createChilds() {}

}
