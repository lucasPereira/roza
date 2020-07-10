package br.ufsc.ine.leb.roza.ui.window;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.Content;
import br.ufsc.ine.leb.roza.ui.window.toolbar.Toolbar;

public class Window implements UiComponent {

	private Hub hub;
	private Manager manager;
	private JFrame frame;
	private JSplitPane pane;

	public Window(Hub hub, Manager manager) {
		this.hub = hub;
		this.manager = manager;
		init();
		createChilds();
	}

	@Override
	public void init() {
		frame = new JFrame("Róża");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		frame.add(pane);
	}

	@Override
	public void createChilds() {
		new Toolbar(hub, manager, this);
		new Content(hub, manager, this);
	}

	public void addComponent(Component component) {
		pane.add(component);
	}

	public void show() {
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setVisible(true);
	}

}
