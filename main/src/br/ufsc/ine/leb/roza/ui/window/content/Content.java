package br.ufsc.ine.leb.roza.ui.window.content;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JPanel;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.Window;
import br.ufsc.ine.leb.roza.ui.window.content.graph.GraphCanvas;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.Sidebar;

public class Content implements UiComponent {

	private Hub hub;
	private Manager manager;
	private Window window;
	private JPanel panel;

	public Content(Hub hub, Manager manager, Window window) {
		this.hub = hub;
		this.manager = manager;
		this.window = window;
		init();
		createChilds();
	}

	@Override
	public void init() {
		panel = new JPanel(new GridLayout(1, 2));
		window.addComponent(panel);
	}

	@Override
	public void createChilds() {
		new GraphCanvas(this);
		new Sidebar(hub, manager, this);
	}

	public void addLeftComponent(Component component) {
		panel.add(component);
	}

	public void addRightComponent(Component component) {
		panel.add(component);
	}

}
