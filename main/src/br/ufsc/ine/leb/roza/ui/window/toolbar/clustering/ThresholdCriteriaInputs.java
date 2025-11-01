package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import br.ufsc.ine.leb.roza.clustering.LevelBasedCriteria;
import br.ufsc.ine.leb.roza.clustering.NeverStopCriteria;
import br.ufsc.ine.leb.roza.clustering.SimilarityBasedCriteria;
import br.ufsc.ine.leb.roza.clustering.TestsPerClassCriteria;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class ThresholdCriteriaInputs implements UiComponent {

	private Hub hub;
	private Manager manager;

	private final ClusteringTab toolbar;

	private JSpinner levelInput;
	private JSpinner testsPerClassInput;
	private JSpinner similarityInput;

	public ThresholdCriteriaInputs(ClusteringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		this.hub = hub;
		this.manager = manager;
		levelInput = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		testsPerClassInput = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		similarityInput = new JSpinner(new SpinnerNumberModel(0.5, 0.0, 1.0, 0.1));
		similarityInput.setPreferredSize(levelInput.getPreferredSize());
		levelInput.setVisible(false);
		testsPerClassInput.setVisible(false);
		similarityInput.setVisible(false);
		createValueChangedEvents();
		createSelectionEvents();
		toolbar.addComponent(levelInput);
		toolbar.addComponent(testsPerClassInput);
		toolbar.addComponent(similarityInput);
	}

	private BigDecimal getBigDecimalValue(JSpinner spiner) {
		Double value = (Double) spiner.getValue();
		return new BigDecimal(value);
	}

	private Integer getIntegerValue(JSpinner spiner) {
		return (Integer) spiner.getValue();
	}

	private void createValueChangedEvents() {
		levelInput.addChangeListener((evento) -> {
			Integer value = getIntegerValue(levelInput);
			manager.setThresholdCriteria(new LevelBasedCriteria(value));
		});
		testsPerClassInput.addChangeListener((evento) -> {
			Integer value = getIntegerValue(testsPerClassInput);
			manager.setThresholdCriteria(new TestsPerClassCriteria(value));
		});
		similarityInput.addChangeListener((evento) -> {
			BigDecimal converted = getBigDecimalValue(similarityInput);
			manager.setThresholdCriteria(new SimilarityBasedCriteria(converted));
		});
	}

	private void createSelectionEvents() {
		hub.selectLevelBasedCriteriaSubscribe(() -> {
			levelInput.setVisible(true);
			testsPerClassInput.setVisible(false);
			similarityInput.setVisible(false);
			manager.setThresholdCriteria(new LevelBasedCriteria(getIntegerValue(levelInput)));
		});
		hub.selectTestsPerClassCriteriaSubscribe(() -> {
			levelInput.setVisible(false);
			testsPerClassInput.setVisible(true);
			similarityInput.setVisible(false);
			manager.setThresholdCriteria(new TestsPerClassCriteria(getIntegerValue(testsPerClassInput)));
		});
		hub.selectSimilarityBasedCriteriaSubscribe(() -> {
			levelInput.setVisible(false);
			testsPerClassInput.setVisible(false);
			similarityInput.setVisible(true);
			manager.setThresholdCriteria(new SimilarityBasedCriteria(getBigDecimalValue(similarityInput)));
		});
		hub.selectNeverStopCriteriaSubscribe(() -> {
			levelInput.setVisible(false);
			testsPerClassInput.setVisible(false);
			similarityInput.setVisible(false);
			manager.setThresholdCriteria(new NeverStopCriteria());
		});
	}

	@Override
	public void addChildren(List<UiComponent> children) {}

	@Override
	public void start() {}

}
