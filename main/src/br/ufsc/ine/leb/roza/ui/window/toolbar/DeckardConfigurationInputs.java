package br.ufsc.ine.leb.roza.ui.window.toolbar;

import javax.swing.JTextField;

import br.ufsc.ine.leb.roza.measurement.configuration.deckard.DeckardConfigurations;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class DeckardConfigurationInputs implements UiComponent {

	private Hub hub;
	private Toolbar toolbar;

	public DeckardConfigurationInputs(Hub hub, Toolbar toolbar) {
		this.hub = hub;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JTextField minTokens = createInput("1", "MIN_TOKENS: minimum token count to suppress vectors for small sub-trees");
		JTextField stride = createInput("0", "STRIDE: width of the sliding window and how far it moves in each step");
		JTextField similarity = createInput("1.0", "SIMILARITY: similarity thresold based on editing distance");
		toolbar.addComponent(minTokens);
		toolbar.addComponent(stride);
		toolbar.addComponent(similarity);
		hub.selectDeckardMetricSubscribe((DeckardConfigurations settings) -> {
			minTokens.setVisible(true);
			stride.setVisible(true);
			similarity.setVisible(true);
		});
	}

	private JTextField createInput(String value, String tip) {
		JTextField input = new JTextField();
		input.setText(value);
		input.setToolTipText(tip);
		input.setVisible(false);
		return input;
	}

	@Override
	public void createChilds() {}

}
