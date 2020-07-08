package br.ufsc.ine.leb.roza.ui;

import java.awt.Color;
import java.io.File;
import java.util.List;

import javax.swing.JPanel;

public class Sidebar implements UiComponent {

	private Hub hub;
	private Content content;

	public Sidebar(Hub hub, Content content) {
		this.hub = hub;
		this.content = content;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		content.addRightComponent(panel);
		hub.classesLoadedSubscribe((List<File> classes) -> {
			System.out.println(classes);
		});
	}

	@Override
	public void createChilds() {}

}
