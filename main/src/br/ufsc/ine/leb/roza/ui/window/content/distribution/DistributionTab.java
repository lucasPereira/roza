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
	private JTabbedPane panel;

	public DistributionTab(Content content) {
		this.content = content;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JTabbedPane();
		content.addLeftComponent(panel);
		hub.loadTestClassesSubscribe(classes -> {
			panel.setEnabledAt(0, false);
			panel.setEnabledAt(1, false);
		});
		hub.extractTestCasesSubscribe(tests -> {
			panel.setEnabledAt(0, false);
			panel.setEnabledAt(1, false);
		});
		hub.measureTestsSubscribe(similarityReport -> {
			panel.setEnabledAt(0, false);
			panel.setEnabledAt(1, false);
		});
		hub.distributeTestsSubscribe(levels -> {
			panel.setEnabledAt(0, true);
			panel.setEnabledAt(1, true);
			panel.setSelectedIndex(0);
		});
	}

	@Override
	public void addChilds(List<UiComponent> childs) {
		childs.add(new ClustersPanel(this));
		childs.add(new GraphCanvas(this));
	}

	@Override
	public void start() {}

	public void addComponent(String title, Component component) {
		panel.add(title, component);
		panel.setEnabledAt(panel.getTabCount() - 1, false);
	}

}
