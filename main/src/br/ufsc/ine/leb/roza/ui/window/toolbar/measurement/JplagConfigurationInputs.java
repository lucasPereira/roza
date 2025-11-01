package br.ufsc.ine.leb.roza.ui.window.toolbar.measurement;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.model.OnlyNumbersFilter;

public class JplagConfigurationInputs implements UiComponent {

	private final MeasurementTab toolbar;
	private JTextField sensitivityInput;

	public JplagConfigurationInputs(MeasurementTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		sensitivityInput = createInput(hub, new OnlyNumbersFilter());
		toolbar.addComponent(sensitivityInput);
		hub.selectJplagMetricSubscribe(() -> sensitivityInput.setVisible(true));
		hub.unselectJplagMetricSubscribe(() -> sensitivityInput.setVisible(false));
	}

	@Override
	public void addChildren(List<UiComponent> children) {}

	@Override
	public void start() {}

	private JTextField createInput(Hub hub, DocumentFilter filter) {
		JTextField input = new JTextField();
		input.setText("1");
		input.setToolTipText("t: sensitivity of the comparison");
		input.setVisible(false);
		final String original = input.getText();
		input.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent event) {
				if (!input.getText().matches("^[1-9][0-9]*$")) {
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

}
