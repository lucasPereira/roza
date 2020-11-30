package br.ufsc.ine.leb.roza.ui.window;

import java.awt.Component;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.Content;
import br.ufsc.ine.leb.roza.ui.window.toolbar.Toolbar;

public class Window implements UiComponent {

	private JFrame frame;
	private JSplitPane pane;

	@Override
	public void init(Hub hub, Manager manager) {
		setLookAndFeel();
		frame = new JFrame("Róża");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		frame.add(pane);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {
		childs.add(new Toolbar(this));
		childs.add(new Content(this));
	}

	@Override
	public void start() {
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setVisible(true);
	}

	public void addComponent(Component component) {
		pane.add(component);
	}

	private void setLookAndFeel() {
		try {
			UIManager.getInstalledLookAndFeels();
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
