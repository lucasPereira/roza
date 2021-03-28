package br.ufsc.ine.leb.roza.ui.window.content.sidebar;

import java.awt.Component;
import java.util.List;

import javax.swing.JTabbedPane;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.Content;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.classes.TestClassesTab;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.clustering.ClusteringTab;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.measurements.MeasurementsTab;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.tests.TestCasesTab;

public class Sidebar implements UiComponent {

	private Content content;
	private JTabbedPane panel;

	public Sidebar(Content content) {
		this.content = content;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JTabbedPane();
		panel.setMaximumSize(panel.getPreferredSize());
		content.addRightComponent(panel);
		hub.loadTestClassesSubscribe(classes -> {
			panel.setSelectedIndex(0);
			panel.setEnabledAt(0, true);
			panel.setEnabledAt(1, false);
			panel.setEnabledAt(2, false);
			panel.setEnabledAt(3, false);
		});
		hub.extractTestCasesSubscribe(tests -> {
			panel.setSelectedIndex(1);
			panel.setEnabledAt(1, true);
			panel.setEnabledAt(2, false);
			panel.setEnabledAt(3, false);
		});
		hub.measureTestsSubscribe(similarityReort -> {
			panel.setSelectedIndex(2);
			panel.setEnabledAt(2, true);
			panel.setEnabledAt(3, false);
		});
		hub.distributeTestsSubscribe(levels -> {
			panel.setSelectedIndex(3);
			panel.setEnabledAt(3, true);
		});
	}

	@Override
	public void addChilds(List<UiComponent> childs) {
		childs.add(new TestClassesTab(this));
		childs.add(new TestCasesTab(this));
		childs.add(new MeasurementsTab(this));
		childs.add(new ClusteringTab(this));
	}

	@Override
	public void start() {}

	public void addComponent(String title, Component component) {
		panel.addTab(title, component);
		panel.setEnabledAt(panel.getTabCount() - 1, false);
	}

}
