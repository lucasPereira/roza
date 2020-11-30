package br.ufsc.ine.leb.roza.ui.window.toolbar;

import java.awt.Component;
import java.util.List;

import javax.swing.JTabbedPane;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.Window;
import br.ufsc.ine.leb.roza.ui.window.toolbar.clustering.ClusteringTab;
import br.ufsc.ine.leb.roza.ui.window.toolbar.extraction.ExtractionTab;
import br.ufsc.ine.leb.roza.ui.window.toolbar.measuring.MeasuringTab;
import br.ufsc.ine.leb.roza.ui.window.toolbar.parsing.ParsingTab;

public class Toolbar implements UiComponent {

	private Window window;
	private JTabbedPane panel;

	public Toolbar(Window window) {
		this.window = window;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JTabbedPane();
		window.addComponent(panel);
		hub.loadTestClassesSubscribe(classes -> panel.setSelectedIndex(1));
		hub.extractTestCasesSubscribe(tests -> panel.setSelectedIndex(2));
		hub.measureTestsSubscribe(similarityReport -> panel.setSelectedIndex(3));
	}

	@Override
	public void addChilds(List<UiComponent> childs) {
		childs.add(new ParsingTab(this));
		childs.add(new ExtractionTab(this));
		childs.add(new MeasuringTab(this));
		childs.add(new ClusteringTab(this));
	}

	@Override
	public void start() {}

	public void addComponent(String title, Component component) {
		panel.add(title, component);
	}

}
