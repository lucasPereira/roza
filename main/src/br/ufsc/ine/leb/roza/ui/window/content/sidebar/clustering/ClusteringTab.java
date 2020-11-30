package br.ufsc.ine.leb.roza.ui.window.content.sidebar.clustering;

import java.util.List;

import javax.swing.JPanel;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.Sidebar;

public class ClusteringTab implements UiComponent {

	private Sidebar sidebar;

	public ClusteringTab(Sidebar sidebar) {
		this.sidebar = sidebar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		JPanel panel = new JPanel();
		sidebar.addComponent("Clustering", panel);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
