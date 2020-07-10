package br.ufsc.ine.leb.roza.ui;

import java.awt.Component;

import javax.swing.JSplitPane;

public class TestClassesTab implements UiComponent {

	private Hub hub;
	private Sidebar sidebar;
	private JSplitPane panel;

	public TestClassesTab(Hub hub, Sidebar sidebar) {
		this.hub = hub;
		this.sidebar = sidebar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		panel = new JSplitPane();
		panel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel.setResizeWeight(0.5);
		sidebar.addComponent(panel);
	}

	@Override
	public void createChilds() {
		new TestClassList(hub, this);
		new TestClassCode(this);
	}

	public void addTopComponent(Component component) {
		panel.setTopComponent(component);
	}

	public void addBottomComponent(Component component) {
		panel.setBottomComponent(component);
	}

}
