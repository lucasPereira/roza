package br.ufsc.ine.leb.roza.ui.window.toolbar.measuring;

import java.awt.Component;

import javax.swing.JPanel;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.toolbar.Toolbar;

public class MeasuringTab implements UiComponent {

	private Hub hub;
	private Manager manager;
	private Toolbar toolbar;
	private JPanel panel;

	public MeasuringTab(Hub hub, Manager manager, Toolbar toolbar) {
		this.hub = hub;
		this.manager = manager;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		panel = new JPanel();
		toolbar.addComponent("Measuring", panel);
	}

	@Override
	public void createChilds() {
		new MeasurerComboBox(hub, manager, this);
		new DeckardConfigurationInputs(hub, this);
		new JplagConfigurationInputs(hub, this);
		new SimianConfigurationInputs(hub, this);
		new MeasureTestCasesButton(hub, manager, this);
	}

	public void addComponent(Component component) {
		panel.add(component);
	}

}
