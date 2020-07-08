package br.ufsc.ine.leb.roza.ui;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class Window implements UiComponent {

	private Hub hub;
	private JFrame frame;
	private JSplitPane pane;

	public Window(Hub hub) {
		this.hub = hub;
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
		new Toolbar(hub, this);
		new Content(hub, this);
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
