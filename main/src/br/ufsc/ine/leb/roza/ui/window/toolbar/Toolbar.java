package br.ufsc.ine.leb.roza.ui.window.toolbar;

import java.awt.Component;

import javax.swing.JToolBar;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.Window;

public class Toolbar implements UiComponent {

	private Hub hub;
	private Window window;
	private JToolBar bar;

	public Toolbar(Hub hub, Window window) {
		this.hub = hub;
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
		new LoadClassesButton(hub, this);
	}

	public void addComponent(Component component) {
		bar.add(component);
	}

}
