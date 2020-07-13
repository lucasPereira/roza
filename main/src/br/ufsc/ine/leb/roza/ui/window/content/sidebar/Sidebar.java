package br.ufsc.ine.leb.roza.ui.window.content.sidebar;

import java.awt.Component;

import javax.swing.JTabbedPane;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.Content;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.classes.TestClassesTab;

public class Sidebar implements UiComponent {

	private Hub hub;
	private Manager manager;
	private Content content;
	private JTabbedPane panel;

	public Sidebar(Hub hub, Manager manager, Content content) {
		this.hub = hub;
		this.manager = manager;
		this.content = content;
		init();
		createChilds();
	}

	@Override
	public void init() {
		panel = new JTabbedPane();
		panel.setMaximumSize(panel.getPreferredSize());
		content.addRightComponent(panel);
	}

	@Override
	public void createChilds() {
		new TestClassesTab(hub, manager, this);
	}

	public void addComponent(String title, Component component) {
		panel.addTab(title, component);
	}

}
