package br.ufsc.ine.leb.roza.ui.window.content.sidebar.measurements;

import java.awt.Component;

import javax.swing.JPanel;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.Sidebar;

public class MeasurementsTab implements UiComponent {

	private Hub hub;
	private Sidebar sidebar;
	private JPanel panel;

	public MeasurementsTab(Hub hub, Sidebar sidebar) {
		this.hub = hub;
		this.sidebar = sidebar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		panel = new JPanel();
		sidebar.addComponent("Measurements", panel);
	}

	@Override
	public void createChilds() {
		new SelectTestCasesMeasurementPanel(hub, this);
	}

	public void addComponent(Component component) {
		panel.add(component);
	}

}
