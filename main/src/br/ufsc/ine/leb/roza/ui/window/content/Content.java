package br.ufsc.ine.leb.roza.ui.window.content;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.Window;
import br.ufsc.ine.leb.roza.ui.window.content.graph.GraphCanvas;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.Sidebar;

public class Content implements UiComponent {

	private Window window;
	private JPanel panel;

	public Content(Window window) {
		this.window = window;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JPanel(new GridLayout(1, 2));
		window.addComponent(panel);

	}

	@Override
	public void addChilds(List<UiComponent> childs) {
		childs.add(new GraphCanvas(this));
		childs.add(new Sidebar(this));
	}

	@Override
	public void start() {}

	public void addLeftComponent(Component component) {
		panel.add(component);
	}

	public void addRightComponent(Component component) {
		panel.add(component);
	}

}
