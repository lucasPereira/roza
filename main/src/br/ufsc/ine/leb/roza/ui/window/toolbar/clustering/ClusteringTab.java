package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.toolbar.Toolbar;

public class ClusteringTab implements UiComponent {

	private final Toolbar toolbar;
	private JPanel panel;

	public ClusteringTab(Toolbar toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JPanel();
		toolbar.addComponent("Clustering", panel);
	}

	@Override
	public void addChildren(List<UiComponent> children) {
		children.add(new LinkageComboBox(this));
		children.add(new RefereeComboBox(this));
		children.add(new ThresholdCriteriaComboBox(this));
		children.add(new ThresholdCriteriaInputs(this));
		children.add(new DistributeTestsButton(this));
	}

	@Override
	public void start() {}

	public void addComponent(Component component) {
		panel.add(component);
	}
}
