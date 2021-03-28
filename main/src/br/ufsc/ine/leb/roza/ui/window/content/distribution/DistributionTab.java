package br.ufsc.ine.leb.roza.ui.window.content.distribution;

import java.awt.Component;
import java.util.List;

import javax.swing.JTabbedPane;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.Content;

public class DistributionTab implements UiComponent {

	private Content content;
	private JTabbedPane tab;

	public DistributionTab(Content content) {
		this.content = content;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		tab = new JTabbedPane();
		content.addLeftComponent(tab);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {
		childs.add(new GraphCanvas(this));
	}

	@Override
	public void start() {}

	public void addComponent(String title, Component component) {
		tab.add(title, component);
	}

}
