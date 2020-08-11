package br.ufsc.ine.leb.roza.ui.window.content.sidebar;

import java.awt.Component;

import javax.swing.JTabbedPane;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.Content;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.classes.TestClassesTab;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.measurements.MeasurementsTab;
import br.ufsc.ine.leb.roza.ui.window.content.sidebar.tests.TestCasesTab;

public class Sidebar implements UiComponent {

	private Hub hub;
	private Manager manager;
	private Content content;
	private JTabbedPane panel;

	public Sidebar(Hub hub, Manager manager, Content content) {
		this.hub = hub;
		this.manager = manager;
		this.content = content;
		init();
		createChilds();
	}

	@Override
	public void init() {
		panel = new JTabbedPane();
		panel.setMaximumSize(panel.getPreferredSize());
		content.addRightComponent(panel);
		hub.loadTestClassesSubscribe(classes -> {
			panel.setSelectedIndex(0);
			panel.setEnabledAt(0, true);
			panel.setEnabledAt(1, false);
			panel.setEnabledAt(2, false);
		});
		hub.extractTestCasesSubscribe(tests -> {
			panel.setSelectedIndex(1);
			panel.setEnabledAt(1, true);
			panel.setEnabledAt(2, false);
		});
		hub.measureTestsSubscribe(similarityReort -> {
			panel.setSelectedIndex(2);
			panel.setEnabledAt(2, true);
		});

	}

	@Override
	public void createChilds() {
		new TestClassesTab(hub, manager, this);
		new TestCasesTab(hub, manager, this);
		new MeasurementsTab(hub, this);
		panel.setEnabledAt(0, false);
		panel.setEnabledAt(1, false);
		panel.setEnabledAt(2, false);
	}

	public void addComponent(String title, Component component) {
		panel.addTab(title, component);
	}

}
