package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import br.ufsc.ine.leb.roza.clustering.dendrogram.LevelBasedCriteria;
import br.ufsc.ine.leb.roza.clustering.dendrogram.SimilarityBasedCriteria;
import br.ufsc.ine.leb.roza.clustering.dendrogram.TestsPerClassCriteria;
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
		hub.loadTestClassesSubscribe(classes -> {
			levelBaseInput.setEnabled(false);
			testsPerClassInput.setEnabled(false);
			similarityBaseInput.setEnabled(false);
		});
		hub.extractTestCasesSubscribe(testCases -> {
			levelBaseInput.setEnabled(false);
			testsPerClassInput.setEnabled(false);
			similarityBaseInput.setEnabled(false);
		});
		hub.measureTestsSubscribe(similarityReport -> {
			levelBaseInput.setEnabled(true);
			testsPerClassInput.setEnabled(true);
			similarityBaseInput.setEnabled(true);
		});
		hub.startTestsDistributionPublishSubscribe(() -> {
			levelBaseInput.setEnabled(false);
			testsPerClassInput.setEnabled(false);
			similarityBaseInput.setEnabled(false);
		});
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
		hub.startTestsDistributionPublishSubscribe(() -> {
			levelBaseInput.setEnabled(false);
			testsPerClassInput.setEnabled(false);
			similarityBaseInput.setEnabled(false);
		});
		levelBaseInput.addChangeListener((evento) -> {
			Integer valor = (Integer) levelBaseInput.getValue();
			manager.setThresholdCriteria(new LevelBasedCriteria(valor));
		});
		testsPerClassInput.addChangeListener((evento) -> {
			Integer valor = (Integer) testsPerClassInput.getValue();
			manager.setThresholdCriteria(new TestsPerClassCriteria(valor));
		});
		similarityBaseInput.addChangeListener((evento) -> {
			Double valor = (Double) similarityBaseInput.getValue();
			BigDecimal convertido = new BigDecimal(valor);
			manager.setThresholdCriteria(new SimilarityBasedCriteria(convertido));
		});
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
