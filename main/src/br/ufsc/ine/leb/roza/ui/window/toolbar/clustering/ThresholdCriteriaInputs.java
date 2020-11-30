package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.util.List;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class ThresholdCriteriaInputs implements UiComponent {

	private ClusteringTab toolbar;

	public ThresholdCriteriaInputs(ClusteringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		JSpinner levelBaseInput = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		JSpinner testsPerClassInput = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		JSpinner similarityBaseInput = new JSpinner(new SpinnerNumberModel(0.5, 0.0, 1.0, 0.1));
		similarityBaseInput.setPreferredSize(levelBaseInput.getPreferredSize());
		levelBaseInput.setVisible(false);
		testsPerClassInput.setVisible(false);
		similarityBaseInput.setVisible(false);
		toolbar.addComponent(levelBaseInput);
		toolbar.addComponent(testsPerClassInput);
		toolbar.addComponent(similarityBaseInput);
		hub.selectLevelBasedCriteriaSubscribe(() -> {
			levelBaseInput.setVisible(true);
			testsPerClassInput.setVisible(false);
			similarityBaseInput.setVisible(false);
		});
		hub.selectTestsPerClassCriteriaSubscribe(() -> {
			levelBaseInput.setVisible(false);
			testsPerClassInput.setVisible(true);
			similarityBaseInput.setVisible(false);
		});
		hub.selectSimilarityBasedCriteriaSubscribe(() -> {
			levelBaseInput.setVisible(false);
			testsPerClassInput.setVisible(false);
			similarityBaseInput.setVisible(true);
		});
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
