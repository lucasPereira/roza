package br.ufsc.ine.leb.roza.ui;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class Sidebar implements UiComponent {

	private Hub hub;
	private Content content;
	private JPanel panel;

	public Sidebar(Hub hub, Content content) {
		this.hub = hub;
		this.content = content;
		init();
		createChilds();
	}

	@Override
	public void init() {
		panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);
		panel.setMaximumSize(panel.getPreferredSize());
		content.addRightComponent(panel);
	}

	@Override
	public void createChilds() {
		new TestClassesTab(hub, this);
	}

	public void addComponent(Component component) {
		panel.add(component);
	}

}
