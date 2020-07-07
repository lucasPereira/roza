package br.ufsc.ine.leb.roza.ui;

import java.awt.Component;

import javax.swing.JSplitPane;

public class Content {

	private JSplitPane panel;

	public Content(Window window) {
		panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		panel.setResizeWeight(0.8);
		window.addComponent(panel);
	}

	public void addLeftComponent(Component component) {
		panel.setLeftComponent(component);
	}

	public void addRightComponent(Component component) {
		panel.setRightComponent(component);
	}

}
