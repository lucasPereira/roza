package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import br.ufsc.ine.leb.roza.clustering.LevelBasedCriterion;
import br.ufsc.ine.leb.roza.clustering.NeverStopCriterion;
import br.ufsc.ine.leb.roza.clustering.SimilarityBasedCriterion;
import br.ufsc.ine.leb.roza.clustering.TestsPerClassCriterion;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class ThresholdCriterionInputs implements UiComponent {

	private Hub hub;
	private Manager manager;

	private final ClusteringTab toolbar;

	private JSpinner levelInput;
	private JSpinner testsPerClassInput;
	private JSpinner similarityInput;

	public ThresholdCriterionInputs(ClusteringTab toolbar) {
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
			manager.setThresholdCriterion(new LevelBasedCriterion(value));
		});
		testsPerClassInput.addChangeListener((evento) -> {
			Integer value = getIntegerValue(testsPerClassInput);
			manager.setThresholdCriterion(new TestsPerClassCriterion(value));
		});
		similarityInput.addChangeListener((evento) -> {
			BigDecimal converted = getBigDecimalValue(similarityInput);
			manager.setThresholdCriterion(new SimilarityBasedCriterion(converted));
		});
	}

	private void createSelectionEvents() {
		hub.selectLevelBasedCriterionSubscribe(() -> {
			levelInput.setVisible(true);
			testsPerClassInput.setVisible(false);
			similarityInput.setVisible(false);
			manager.setThresholdCriterion(new LevelBasedCriterion(getIntegerValue(levelInput)));
		});
		hub.selectTestsPerClassCriterionSubscribe(() -> {
			levelInput.setVisible(false);
			testsPerClassInput.setVisible(true);
			similarityInput.setVisible(false);
			manager.setThresholdCriterion(new TestsPerClassCriterion(getIntegerValue(testsPerClassInput)));
		});
		hub.selectSimilarityBasedCriterionSubscribe(() -> {
			levelInput.setVisible(false);
			testsPerClassInput.setVisible(false);
			similarityInput.setVisible(true);
			manager.setThresholdCriterion(new SimilarityBasedCriterion(getBigDecimalValue(similarityInput)));
		});
		hub.selectNeverStopCriterionSubscribe(() -> {
			levelInput.setVisible(false);
			testsPerClassInput.setVisible(false);
			similarityInput.setVisible(false);
			manager.setThresholdCriterion(new NeverStopCriterion());
		});
	}

	@Override
	public void addChildren(List<UiComponent> children) {}

	@Override
	public void start() {}

}
