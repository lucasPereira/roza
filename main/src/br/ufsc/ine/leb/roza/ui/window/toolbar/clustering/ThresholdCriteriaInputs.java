package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import br.ufsc.ine.leb.roza.clustering.dendrogram.LevelBasedCriteria;
import br.ufsc.ine.leb.roza.clustering.dendrogram.NeverStopCriteria;
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
		createToolbarEvents(hub, levelBaseInput, testsPerClassInput, similarityBaseInput);
		createSelectionEvents(hub, manager, levelBaseInput, testsPerClassInput, similarityBaseInput);
		createValueChangedEvents(manager, levelBaseInput, testsPerClassInput, similarityBaseInput);
		toolbar.addComponent(levelBaseInput);
		toolbar.addComponent(testsPerClassInput);
		toolbar.addComponent(similarityBaseInput);
	}

	private void createValueChangedEvents(Manager manager, JSpinner levelBaseInput, JSpinner testsPerClassInput, JSpinner similarityBaseInput) {
		levelBaseInput.addChangeListener((evento) -> {
			Integer valor = getIntegerValue(levelBaseInput);
			manager.setThresholdCriteria(new LevelBasedCriteria(valor));
		});
		testsPerClassInput.addChangeListener((evento) -> {
			Integer valor = getIntegerValue(testsPerClassInput);
			manager.setThresholdCriteria(new TestsPerClassCriteria(valor));
		});
		similarityBaseInput.addChangeListener((evento) -> {
			BigDecimal convertido = getBigDecimalValue(similarityBaseInput);
			manager.setThresholdCriteria(new SimilarityBasedCriteria(convertido));
		});
	}

	private BigDecimal getBigDecimalValue(JSpinner spiner) {
		Double valor = (Double) spiner.getValue();
		BigDecimal convertido = new BigDecimal(valor);
		return convertido;
	}

	private Integer getIntegerValue(JSpinner spiner) {
		Integer valor = (Integer) spiner.getValue();
		return valor;
	}

	private void createSelectionEvents(Hub hub, Manager manager, JSpinner levelBaseInput, JSpinner testsPerClassInput, JSpinner similarityBaseInput) {
		hub.selectLevelBasedCriteriaSubscribe(() -> {
			levelBaseInput.setVisible(true);
			testsPerClassInput.setVisible(false);
			similarityBaseInput.setVisible(false);
			manager.setThresholdCriteria(new LevelBasedCriteria(getIntegerValue(levelBaseInput)));
		});
		hub.selectTestsPerClassCriteriaSubscribe(() -> {
			levelBaseInput.setVisible(false);
			testsPerClassInput.setVisible(true);
			similarityBaseInput.setVisible(false);
			manager.setThresholdCriteria(new TestsPerClassCriteria(getIntegerValue(testsPerClassInput)));
		});
		hub.selectSimilarityBasedCriteriaSubscribe(() -> {
			levelBaseInput.setVisible(false);
			testsPerClassInput.setVisible(false);
			similarityBaseInput.setVisible(true);
			manager.setThresholdCriteria(new SimilarityBasedCriteria(getBigDecimalValue(similarityBaseInput)));
		});
		hub.selectNeverStopCriteriaSubscribe(() -> {
			levelBaseInput.setVisible(false);
			testsPerClassInput.setVisible(false);
			similarityBaseInput.setVisible(true);
			manager.setThresholdCriteria(new NeverStopCriteria());
		});
	}

	private void createToolbarEvents(Hub hub, JSpinner levelBaseInput, JSpinner testsPerClassInput, JSpinner similarityBaseInput) {
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
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
