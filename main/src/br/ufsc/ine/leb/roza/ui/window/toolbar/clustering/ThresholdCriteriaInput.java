package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import javax.swing.JTextField;

import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.toolbar.Toolbar;

public class ThresholdCriteriaInput implements UiComponent {

	private Toolbar toolbar;

	public ThresholdCriteriaInput(Toolbar toolbar) {
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JTextField input = new JTextField();
		input.setText("1");
		toolbar.addComponent(input);
	}

	@Override
	public void createChilds() {}

}
