package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class ThresholdCriteriaInputs implements UiComponent {

	private Hub hub;
	private ClusteringTab toolbar;

	public ThresholdCriteriaInputs(Hub hub, ClusteringTab toolbar) {
		this.hub = hub;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JSpinner levelBaseInput = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		JSpinner testsPerClassInput = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		JSpinner similarityBaseInput = new JSpinner(new SpinnerNumberModel(0.5, 0.0, 1.0, 0.1));
		similarityBaseInput.setPreferredSize(levelBaseInput.getPreferredSize());
		levelBaseInput.setVisible(false);
		testsPerClassInput.setVisible(false);
		similarityBaseInput.setVisible(false);
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
		toolbar.addComponent(levelBaseInput);
		toolbar.addComponent(testsPerClassInput);
		toolbar.addComponent(similarityBaseInput);
	}

	@Override
	public void createChilds() {}

}
