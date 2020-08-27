package br.ufsc.ine.leb.roza.ui.window.content.sidebar.measurements;

import java.awt.Component;

import javax.swing.JSplitPane;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.Sidebar;

public class MeasurementsTab implements UiComponent {

	private Hub hub;
	private Sidebar sidebar;
	private JSplitPane panel;

	public MeasurementsTab(Hub hub, Sidebar sidebar) {
		this.hub = hub;
		this.sidebar = sidebar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		panel = new JSplitPane();
		panel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel.setResizeWeight(0);
		sidebar.addComponent("Measurements", panel);
	}

	@Override
	public void createChilds() {
		new SelectTestCasesMeasurementPanel(hub, this);
		new CompareTestCasesMeasurementPanel(hub, this);
	}

	public void addTopComponent(Component component) {
		panel.setTopComponent(component);
	}

	public void addBottomComponent(Component component) {
		panel.setBottomComponent(component);
	}

}
