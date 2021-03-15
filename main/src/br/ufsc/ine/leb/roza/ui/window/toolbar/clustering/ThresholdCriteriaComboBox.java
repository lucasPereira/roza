package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.util.List;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.shared.ComboBoxBuilder;

public class ThresholdCriteriaComboBox implements UiComponent {

	private ClusteringTab toolbar;
	private JComboBox<String> combo;

	public ThresholdCriteriaComboBox(ClusteringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		ComboBoxBuilder builder = new ComboBoxBuilder("Theshold Criteria");
		builder.add("Level Based Criteria", () -> hub.selectLevelBasedCriteriaPublish());
		builder.add("Test per Class Criteria", () -> hub.selectTestsPerClassCriteriaPublish());
		builder.add("Similarity Based Criteria", () -> hub.selectSimilarityBasedCriteriaPublish());
		combo = builder.build();
		toolbar.addComponent(combo);
		hub.loadTestClassesSubscribe(classes -> combo.setEnabled(false));
		hub.extractTestCasesSubscribe(testCases -> combo.setEnabled(false));
		hub.measureTestsSubscribe(similarityReport -> combo.setEnabled(true));
		hub.startTestsDistributionPublishSubscribe(() -> combo.setEnabled(false));
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {
		combo.setSelectedIndex(2);
	}

}
