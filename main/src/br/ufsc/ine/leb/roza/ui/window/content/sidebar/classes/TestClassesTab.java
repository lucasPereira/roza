package br.ufsc.ine.leb.roza.ui.window.content.sidebar.classes;

import java.awt.Component;

import javax.swing.JSplitPane;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.Sidebar;

public class TestClassesTab implements UiComponent {

	private Hub hub;
	private Sidebar sidebar;
	private JSplitPane panel;

	public TestClassesTab(Hub hub, Manager manager, Sidebar sidebar) {
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
		sidebar.addComponent("Test Classes", panel);
	}

	@Override
	public void createChilds() {
		new TestClassList(hub, this);
		new TestClassInformation(hub, this);
	}

	public void addTopComponent(Component component) {
		panel.setTopComponent(component);
	}

	public void addBottomComponent(Component component) {
		panel.setBottomComponent(component);
	}

}
