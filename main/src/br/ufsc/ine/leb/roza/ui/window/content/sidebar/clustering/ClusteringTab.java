package br.ufsc.ine.leb.roza.ui.window.content.sidebar.clustering;

import javax.swing.JPanel;

import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.Sidebar;

public class ClusteringTab implements UiComponent {

	private Sidebar sidebar;

	public ClusteringTab(Sidebar sidebar) {
		this.sidebar = sidebar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JPanel panel = new JPanel();
		sidebar.addComponent("Clustering", panel);
	}

	@Override
	public void createChilds() {}

}
