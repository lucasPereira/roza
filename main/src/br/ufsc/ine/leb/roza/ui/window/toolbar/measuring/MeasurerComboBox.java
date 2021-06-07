package br.ufsc.ine.leb.roza.ui.window.toolbar.measuring;

import java.util.List;

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
import br.ufsc.ine.leb.roza.ui.shared.ComboBoxBuilder;

public class MeasurerComboBox implements UiComponent {

	private MeasuringTab toolbar;
	private JComboBox<String> combo;

	public MeasurerComboBox(MeasuringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		combo = new ComboBoxBuilder("Measurer").add("LCCSS", () -> {
			manager.setSimilarityMeasurer(new LccssSimilarityMeasurer());
			hub.unselectDeckardMetricPublish();
			hub.unselectJplagMetricPublish();
			hub.unselectSimianMetricPublish();
		}).add("LCS", () -> {
			manager.setSimilarityMeasurer(new LcsSimilarityMeasurer());
		}).add("Deckard", () -> {
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
		}).add("JPlag", () -> {
			JplagConfigurations settings = new JplagConfigurations();
			manager.setSimilarityMeasurer(new JplagSimilarityMeasurer(settings));
			hub.unselectDeckardMetricPublish();
			hub.unselectSimianMetricPublish();
			hub.selectJplagMetricPublish();
			hub.changeJplagSettingsSubscribe(sensitivity -> {
				settings.sensitivity(sensitivity);
			});
		}).add("Simian", () -> {
			SimianConfigurations settings = new SimianConfigurations();
			manager.setSimilarityMeasurer(new SimianSimilarityMeasurer(settings));
			hub.unselectDeckardMetricPublish();
			hub.unselectJplagMetricPublish();
			hub.selectSimianMetricPublish();
			hub.changeSimianSettingsSubscribe(threshold -> {
				settings.threshold(threshold);
			});
		}).build();
		toolbar.addComponent(combo);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {
		combo.setSelectedIndex(0);
	}

}
