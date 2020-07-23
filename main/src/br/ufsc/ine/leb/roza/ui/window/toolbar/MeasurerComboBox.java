package br.ufsc.ine.leb.roza.ui.window.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.measurement.DeckardSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.LccssSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.LcsSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.configuration.deckard.DeckardConfigurations;
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
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				Integer selected = combo.getSelectedIndex();
				if (selected == 0) {
					manager.setSimilarityMeasurer(new LccssSimilarityMeasurer());
				} else if (selected == 1) {
					manager.setSimilarityMeasurer(new LcsSimilarityMeasurer());
				} else if (selected == 2) {
					DeckardConfigurations settings = new DeckardConfigurations();
					manager.setSimilarityMeasurer(new DeckardSimilarityMeasurer(settings));
					hub.selectDeckardMetricPublish(settings);
				}
			}

		});
		toolbar.addComponent(combo);
	}

	@Override
	public void createChilds() {}

}
