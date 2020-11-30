package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import javax.swing.JTextField;

import br.ufsc.ine.leb.roza.ui.UiComponent;

public class ThresholdCriteriaInput implements UiComponent {

	private ClusteringTab toolbar;

	public ThresholdCriteriaInput(ClusteringTab toolbar) {
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
