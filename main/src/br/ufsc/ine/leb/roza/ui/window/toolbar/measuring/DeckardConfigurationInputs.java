package br.ufsc.ine.leb.roza.ui.window.toolbar.measuring;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.model.OnlyNumbersAndDots;
import br.ufsc.ine.leb.roza.ui.model.OnlyNumbersFilter;

public class DeckardConfigurationInputs implements UiComponent {

	private MeasuringTab toolbar;
	private JTextField minTokensInput;
	private JTextField strideInput;
	private JTextField similarityInput;

	public DeckardConfigurationInputs(MeasuringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		minTokensInput = createInput(hub, "1", "MIN_TOKENS: minimum token count to suppress vectors for small sub-trees", "^[1-9][0-9]*$", new OnlyNumbersFilter());
		strideInput = createInput(hub, "0", "STRIDE: width of the sliding window and how far it moves in each step", "^[0-9]+$", new OnlyNumbersFilter());
		similarityInput = createInput(hub, "1.0", "SIMILARITY: similarity thresold based on editing distance", "^(0[.][0-9]+)|(1[.]0)$", new OnlyNumbersAndDots());
		toolbar.addComponent(minTokensInput);
		toolbar.addComponent(strideInput);
		toolbar.addComponent(similarityInput);
		hub.selectDeckardMetricSubscribe(() -> {
			minTokensInput.setVisible(true);
			strideInput.setVisible(true);
			similarityInput.setVisible(true);
		});
		hub.unselectDeckardMetricSubscribe(() -> {
			minTokensInput.setVisible(false);
			strideInput.setVisible(false);
			similarityInput.setVisible(false);
		});
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

	private JTextField createInput(Hub hub, String value, String tip, String regex, DocumentFilter filter) {
		JTextField input = new JTextField();
		input.setText(value);
		input.setToolTipText(tip);
		input.setVisible(false);
		final String original = input.getText();
		input.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent event) {
				if (!input.getText().matches(regex)) {
					input.setText(original);
				}
				Integer minTokens = Integer.parseInt(minTokensInput.getText());
				Integer stride = Integer.parseInt(strideInput.getText());
				Double similarity = Double.parseDouble(similarityInput.getText());
				hub.changeDeckardSettingsPublish(minTokens, stride, similarity);
			}

			@Override
			public void focusGained(FocusEvent event) {}

		});
		PlainDocument document = (PlainDocument) input.getDocument();
		document.setDocumentFilter(filter);
		return input;
	}

}
