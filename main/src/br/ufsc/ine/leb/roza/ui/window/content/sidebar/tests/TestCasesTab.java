package br.ufsc.ine.leb.roza.ui.window.content.sidebar.tests;

import java.awt.Component;
import java.util.List;

import javax.swing.JSplitPane;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.Sidebar;

public class TestCasesTab implements UiComponent {

	private JSplitPane panel;
	private Sidebar sidebar;

	public TestCasesTab(Sidebar sidebar) {
		this.sidebar = sidebar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JSplitPane();
		panel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel.setResizeWeight(0.5);
		sidebar.addComponent("Test Cases", panel);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {
		childs.add(new TestCaseList(this));
		childs.add(new TestCaseCode(this));
	}

	@Override
	public void start() {}

	public void addTopComponent(Component component) {
		panel.setTopComponent(component);
	}

	public void addBottomComponent(Component component) {
		panel.setBottomComponent(component);
	}

}
