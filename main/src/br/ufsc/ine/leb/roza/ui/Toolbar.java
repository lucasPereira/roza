package br.ufsc.ine.leb.roza.ui;

import java.awt.Component;

import javax.swing.JToolBar;

public class Toolbar {

	private JToolBar bar;

	public Toolbar(Window window) {
		bar = new JToolBar();
		window.addComponent(bar);
	}

	public void addComponent(Component component) {
		bar.add(component);
	}

}
