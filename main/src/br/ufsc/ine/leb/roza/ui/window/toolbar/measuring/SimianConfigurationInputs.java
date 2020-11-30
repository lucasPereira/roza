package br.ufsc.ine.leb.roza.ui.window.toolbar.measuring;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.model.OnlyNumbersFilter;

public class SimianConfigurationInputs implements UiComponent {

	private Hub hub;
	private MeasuringTab toolbar;
	private JTextField thresholdInput;

	public SimianConfigurationInputs(Hub hub, MeasuringTab toolbar) {
		this.hub = hub;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		thresholdInput = createInput("6", "threshold: matches will contain at least the specified number of lines", "^([1-2][0-9]+)|([3-9][0-9]*)$", new OnlyNumbersFilter());
		toolbar.addComponent(thresholdInput);
		hub.selectSimianMetricSubscribe(() -> {
			thresholdInput.setVisible(true);
		});
		hub.unselectSimianMetricSubscribe(() -> {
			thresholdInput.setVisible(false);
		});
	}

	private JTextField createInput(String value, String tip, String regex, DocumentFilter filter) {
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
				Integer threshold = Integer.parseInt(thresholdInput.getText());
				hub.changeJplagSettingsPublish(threshold);
			}

			@Override
			public void focusGained(FocusEvent event) {}

		});
		PlainDocument document = (PlainDocument) input.getDocument();
		document.setDocumentFilter(filter);
		return input;
	}

	@Override
	public void createChilds() {}

}
