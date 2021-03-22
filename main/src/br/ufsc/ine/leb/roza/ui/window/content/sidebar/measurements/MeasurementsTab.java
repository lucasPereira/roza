package br.ufsc.ine.leb.roza.ui.window.content.sidebar.measurements;

import java.awt.Component;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.Sidebar;

public class MeasurementsTab implements UiComponent {

	private Sidebar sidebar;
	private JPanel panel;
	private Component bottom;
	private Component middle;
	private Component top;

	public MeasurementsTab(Sidebar sidebar) {
		this.sidebar = sidebar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JPanel();
		sidebar.addComponent("Measurements", panel);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {
		childs.add(new SelectTestCasesMeasurementPanel(this));
		childs.add(new MatrixMeasurementPanel(this));
		childs.add(new CompareTestCasesMeasurementPanel(this));
	}

	@Override
	public void start() {
		GroupLayout group = new GroupLayout(panel);
		panel.setLayout(group);
		group.setVerticalGroup(group.createSequentialGroup().addComponent(top, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(middle).addComponent(bottom));
		group.setHorizontalGroup(group.createParallelGroup().addComponent(top).addComponent(middle).addComponent(bottom));
	}

	public void addTopComponent(Component component) {
		this.top = component;
	}

	public void addMiddleComponent(Component component) {
		this.middle = component;
	}

	public void addBottomComponent(Component component) {
		this.bottom = component;
	}

}
