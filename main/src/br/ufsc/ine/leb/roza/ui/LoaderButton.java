package br.ufsc.ine.leb.roza.ui;

import javax.swing.JButton;

public class LoaderButton implements UiComponent {

	private Toolbar toolbar;
	private JButton button;

	public LoaderButton(Toolbar toolbar) {
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		button = new JButton("Load classes");
		toolbar.addComponent(button);
	}

	@Override
	public void createChilds() {}

}
