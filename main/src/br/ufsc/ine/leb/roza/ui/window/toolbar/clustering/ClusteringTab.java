package br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.awt.Component;

import javax.swing.JPanel;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.toolbar.Toolbar;

public class ClusteringTab implements UiComponent {

	private Hub hub;
	private Manager manager;
	private Toolbar toolbar;
	private JPanel panel;

	public ClusteringTab(Hub hub, Manager manager, Toolbar toolbar) {
		this.hub = hub;
		this.manager = manager;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		panel = new JPanel();
		toolbar.addComponent("Clustering", panel);
	}

	@Override
	public void createChilds() {
		new LinkageComboBox(hub, manager, this);
		new RefereeComboBox(hub, manager, this);
		new ThresholdCriteriaComboBox(hub, this);
		new ThresholdCriteriaInputs(this);
		new StartTestsDistributionButton(hub, manager, this);
	}

	public void addComponent(Component component) {
		panel.add(component);
	}

}
