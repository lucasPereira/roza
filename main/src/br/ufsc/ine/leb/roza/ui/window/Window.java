package br.ufsc.ine.leb.roza.ui.window;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.Content;
import br.ufsc.ine.leb.roza.ui.window.messages.Messages;
import br.ufsc.ine.leb.roza.ui.window.toolbar.Toolbar;

public class Window implements UiComponent {

	private JFrame frame;
	private JPanel panel;
	private JPanel top;
	private JTabbedPane middle;
	private JPanel bottom;

	@Override
	public void init(Hub hub, Manager manager) {
		setLookAndFeel();
		frame = new JFrame("Róża");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		frame.add(panel);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {
		childs.add(new Messages(this));
		childs.add(new Toolbar(this));
		childs.add(new Content(this));
	}

	@Override
	public void start() {
		GroupLayout group = new GroupLayout(panel);
		panel.setLayout(group);
		group.setVerticalGroup(group.createSequentialGroup().addComponent(top, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(middle, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(bottom));
		group.setHorizontalGroup(group.createParallelGroup().addComponent(top).addComponent(middle).addComponent(bottom));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setVisible(true);
	}

	public void addTopComponent(JPanel top) {
		this.top = top;
	}

	public void addMiddleComponent(JTabbedPane middle) {
		this.middle = middle;
	}

	public void addBottomComponent(JPanel bottom) {
		this.bottom = bottom;
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
