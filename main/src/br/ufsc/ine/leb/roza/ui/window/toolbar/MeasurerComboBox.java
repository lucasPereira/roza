package br.ufsc.ine.leb.roza.ui.window.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.measurement.DeckardSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.JplagSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.LccssSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.LcsSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.SimianSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.configuration.deckard.DeckardConfigurations;
import br.ufsc.ine.leb.roza.measurement.configuration.jplag.JplagConfigurations;
import br.ufsc.ine.leb.roza.measurement.configuration.simian.SimianConfigurations;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class MeasurerComboBox implements UiComponent {

	private Hub hub;
	private Manager manager;
	private Toolbar toolbar;

	public MeasurerComboBox(Hub hub, Manager manager, Toolbar toolbar) {
		this.hub = hub;
		this.manager = manager;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JComboBox<String> combo = new JComboBox<>();
		combo.setToolTipText("Metric");
		combo.addItem("LCCSS");
		combo.addItem("LCS");
		combo.addItem("Deckard");
		combo.addItem("JPlag");
		combo.addItem("Simian");
		combo.setMaximumSize(combo.getPreferredSize());
		combo.setEnabled(false);
		hub.loadTestClassesSubscribe(classes -> combo.setEnabled(false));
		hub.extractTestCasesSubscribe(tests -> combo.setEnabled(true));
		manager.setSimilarityMeasurer(new LccssSimilarityMeasurer());
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				Integer selected = combo.getSelectedIndex();
				if (selected == 0) {
					manager.setSimilarityMeasurer(new LccssSimilarityMeasurer());
					hub.unselectDeckardMetricPublish();
					hub.unselectJplagMetricPublish();
					hub.unselectSimianMetricPublish();
				} else if (selected == 1) {
					manager.setSimilarityMeasurer(new LcsSimilarityMeasurer());
				} else if (selected == 2) {
					DeckardConfigurations settings = new DeckardConfigurations();
					manager.setSimilarityMeasurer(new DeckardSimilarityMeasurer(settings));
					hub.unselectJplagMetricPublish();
					hub.unselectSimianMetricPublish();
					hub.selectDeckardMetricPublish();
					hub.changeDeckardSettingsSubscribe((minTokens, stride, similarity) -> {
						settings.minTokens(minTokens);
						settings.stride(stride);
						settings.similarity(similarity);
					});
				} else if (selected == 3) {
					JplagConfigurations settings = new JplagConfigurations();
					manager.setSimilarityMeasurer(new JplagSimilarityMeasurer(settings));
					hub.unselectDeckardMetricPublish();
					hub.unselectSimianMetricPublish();
					hub.selectJplagMetricPublish();
					hub.changeJplagSettingsSubscribe(sensitivity -> {
						settings.sensitivity(sensitivity);
					});
				} else if (selected == 4) {
					SimianConfigurations settings = new SimianConfigurations();
					manager.setSimilarityMeasurer(new SimianSimilarityMeasurer(settings));
					hub.unselectDeckardMetricPublish();
					hub.unselectJplagMetricPublish();
					hub.selectSimianMetricPublish();
					hub.changeSimianSettingsSubscribe(threshold -> {
						settings.threshold(threshold);
					});
				}
			}

		});
		toolbar.addComponent(combo);
	}

	@Override
	public void createChilds() {}

}
