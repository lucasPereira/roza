package br.ufsc.ine.leb.roza.ui;

import java.awt.Color;

import javax.swing.JPanel;

public class Sidebar implements UiComponent {

	private Content content;

	public Sidebar(Content content) {
		this.content = content;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		content.addRightComponent(panel);
	}

	@Override
	public void createChilds() {}

}
