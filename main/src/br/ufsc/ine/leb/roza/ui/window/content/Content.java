package br.ufsc.ine.leb.roza.ui.window.content;

import java.awt.Component;

import javax.swing.JSplitPane;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.Window;
import br.ufsc.ine.leb.roza.ui.window.content.graphCanvas.GraphCanvas;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.Sidebar;

public class Content implements UiComponent {

	private Hub hub;
	private Manager manager;
	private Window window;
	private JSplitPane panel;

	public Content(Hub hub, Manager manager, Window window) {
		this.hub = hub;
		this.manager = manager;
		this.window = window;
		init();
		createChilds();
	}

	@Override
	public void init() {
		panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		panel.setResizeWeight(1);
		window.addComponent(panel);
	}

	@Override
	public void createChilds() {
		new GraphCanvas(this);
		new Sidebar(hub, manager, this);
	}

	public void addLeftComponent(Component component) {
		panel.setLeftComponent(component);
	}

	public void addRightComponent(Component component) {
		panel.setRightComponent(component);
	}

}
