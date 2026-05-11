package br.ufsc.ine.leb.roza.ui.legacy.window.toolbar.measurement;

import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;

import br.ufsc.ine.leb.roza.ui.legacy.Hub;
import br.ufsc.ine.leb.roza.ui.legacy.Manager;
import br.ufsc.ine.leb.roza.ui.legacy.UiComponent;
import br.ufsc.ine.leb.roza.ui.legacy.window.toolbar.Toolbar;

public class MeasurementTab implements UiComponent {

	private final Toolbar toolbar;
	private JPanel panel;

	public MeasurementTab(Toolbar toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JPanel();
		toolbar.addComponent("Measurement", panel);
	}

	@Override
	public void addChildren(List<UiComponent> children) {
		children.add(new MeasurerComboBox(this));
		children.add(new DeckardConfigurationInputs(this));
		children.add(new JplagConfigurationInputs(this));
		children.add(new SimianConfigurationInputs(this));
		children.add(new MeasureTestCasesButton(this));
	}

	@Override
	public void start() {}

	public void addComponent(Component component) {
		panel.add(component);
	}

}
