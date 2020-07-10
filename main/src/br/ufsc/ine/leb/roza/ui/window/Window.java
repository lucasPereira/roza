package br.ufsc.ine.leb.roza.ui.window;

import java.awt.Component;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.Content;
import br.ufsc.ine.leb.roza.ui.window.toolbar.Toolbar;

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
		hub.loadClassesSubscribe((List<File> files) -> {
			show();
		});
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
