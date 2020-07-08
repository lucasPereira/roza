package br.ufsc.ine.leb.roza.ui;

import java.awt.Component;

import javax.swing.JToolBar;

public class Toolbar implements UiComponent {

	private Window window;
	private JToolBar bar;

	public Toolbar(Window window) {
		this.window = window;
		init();
		createChilds();
	}

	@Override
	public void init() {
		bar = new JToolBar();
		window.addComponent(bar);
	}

	@Override
	public void createChilds() {
		new LoaderButton(this);
	}

	public void addComponent(Component component) {
		bar.add(component);
	}

}
