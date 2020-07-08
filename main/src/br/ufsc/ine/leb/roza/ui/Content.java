package br.ufsc.ine.leb.roza.ui;

import java.awt.Component;

import javax.swing.JSplitPane;

public class Content implements UiComponent {

	private Window window;
	private JSplitPane panel;

	public Content(Window window) {
		this.window = window;
		init();
		createChilds();
	}

	@Override
	public void init() {
		panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		panel.setResizeWeight(0.8);
		window.addComponent(panel);
	}

	@Override
	public void createChilds() {
		new GraphCanvas(this);
		new Sidebar(this);
	}

	public void addLeftComponent(Component component) {
		panel.setLeftComponent(component);
	}

	public void addRightComponent(Component component) {
		panel.setRightComponent(component);
	}

}
