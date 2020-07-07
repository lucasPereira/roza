package br.ufsc.ine.leb.roza.ui;

import javax.swing.JButton;

public class LoaderButton {

	private JButton button;

	public LoaderButton(Toolbar toolbar) {
		button = new JButton("Load classes");
		toolbar.addComponent(button);
	}

}
