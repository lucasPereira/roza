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

public class SimianConfigurationInputs implements UiComponent {

	private final MeasurementTab toolbar;
	private JTextField thresholdInput;

	public SimianConfigurationInputs(MeasurementTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		thresholdInput = createInput(hub, new OnlyNumbersFilter());
		toolbar.addComponent(thresholdInput);
		hub.selectSimianMetricSubscribe(() -> thresholdInput.setVisible(true));
		hub.unselectSimianMetricSubscribe(() -> thresholdInput.setVisible(false));
	}

	@Override
	public void addChildren(List<UiComponent> children) {}

	@Override
	public void start() {}

	private JTextField createInput(Hub hub, DocumentFilter filter) {
		JTextField input = new JTextField();
		input.setText("6");
		input.setToolTipText("threshold: matches will contain at least the specified number of lines");
		input.setVisible(false);
		final String original = input.getText();
		input.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent event) {
				if (!input.getText().matches("^([1-2][0-9]+)|([3-9][0-9]*)$")) {
					input.setText(original);
				}
				Integer threshold = Integer.parseInt(thresholdInput.getText());
				hub.changeSimianSettingsPublish(threshold);
			}

			@Override
			public void focusGained(FocusEvent event) {}

		});
		PlainDocument document = (PlainDocument) input.getDocument();
		document.setDocumentFilter(filter);
		return input;
	}
}
