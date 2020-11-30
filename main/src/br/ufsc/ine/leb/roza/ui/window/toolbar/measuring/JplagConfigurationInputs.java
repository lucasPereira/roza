package br.ufsc.ine.leb.roza.ui.window.toolbar.measuring;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.model.OnlyNumbersFilter;

public class JplagConfigurationInputs implements UiComponent {

	private Hub hub;
	private MeasuringTab toolbar;
	private JTextField sensitivityInput;

	public JplagConfigurationInputs(Hub hub, MeasuringTab toolbar) {
		this.hub = hub;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		sensitivityInput = createInput("1", "t: sensitivity of the comparison", "^[1-9][0-9]*$", new OnlyNumbersFilter());
		toolbar.addComponent(sensitivityInput);
		hub.selectJplagMetricSubscribe(() -> {
			sensitivityInput.setVisible(true);
		});
		hub.unselectJplagMetricSubscribe(() -> {
			sensitivityInput.setVisible(false);
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
				Integer sensitivity = Integer.parseInt(sensitivityInput.getText());
				hub.changeJplagSettingsPublish(sensitivity);
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
