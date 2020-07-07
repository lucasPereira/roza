package br.ufsc.ine.leb.roza.ui;

import java.awt.Color;

import javax.swing.JPanel;

public class Sidebar {

	public Sidebar(Content content) {
		JPanel panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		content.addRightComponent(panel);
	}

}
