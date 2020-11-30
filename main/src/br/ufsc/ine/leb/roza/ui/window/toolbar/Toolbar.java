package br.ufsc.ine.leb.roza.ui.window.toolbar;

import java.awt.Component;

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

	private Hub hub;
	private Manager manager;
	private Window window;
	private JTabbedPane bar;

	public Toolbar(Hub hub, Manager manager, Window window) {
		this.hub = hub;
		this.manager = manager;
		this.window = window;
		init();
		createChilds();
	}

	@Override
	public void init() {
		bar = new JTabbedPane();
		hub.loadTestClassesSubscribe(classes -> {
			bar.setSelectedIndex(1);
		});
		hub.extractTestCasesSubscribe(tests -> {
			bar.setSelectedIndex(2);
		});
		hub.measureTestsSubscribe(similarityReort -> {
			bar.setSelectedIndex(3);
		});
		window.addComponent(bar);
	}

	@Override
	public void createChilds() {
		new ParsingTab(hub, manager, this);
		new ExtractionTab(hub, manager, this);
		new MeasuringTab(hub, manager, this);
		new ClusteringTab(hub, manager, this);
	}

	public void addComponent(String title, Component component) {
		bar.add(title, component);
	}

}
