package br.ufsc.ine.leb.roza.ui.window.toolbar;

import java.awt.Component;
import java.util.List;

import javax.swing.JTabbedPane;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.Window;
import br.ufsc.ine.leb.roza.ui.window.toolbar.clustering.ClusteringTab;
import br.ufsc.ine.leb.roza.ui.window.toolbar.decomposition.DecompositionTab;
import br.ufsc.ine.leb.roza.ui.window.toolbar.measurement.MeasurementTab;
import br.ufsc.ine.leb.roza.ui.window.toolbar.parsing.ParsingTab;
import br.ufsc.ine.leb.roza.ui.window.toolbar.refactoring.RefactoringTab;

public class Toolbar implements UiComponent {

	private final Window window;
	private JTabbedPane panel;

	public Toolbar(Window window) {
		this.window = window;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JTabbedPane();
		window.addMiddleComponent(panel);
		hub.loadTestClassesSubscribe(classes -> {
			panel.setEnabledAt(1, true);
			panel.setEnabledAt(2, false);
			panel.setEnabledAt(3, false);
			panel.setEnabledAt(4, false);
			panel.setSelectedIndex(1);
		});
		hub.extractTestCasesSubscribe(tests -> {
			panel.setEnabledAt(2, true);
			panel.setEnabledAt(3, false);
			panel.setEnabledAt(4, false);
			panel.setSelectedIndex(2);
		});
		hub.measureTestsSubscribe(similarityReport -> {
			panel.setEnabledAt(3, true);
			panel.setEnabledAt(4, false);
			panel.setSelectedIndex(3);
		});
		hub.distributeTestsSubscribe(levels -> {
			panel.setEnabledAt(4, true);
			panel.setSelectedIndex(4);
		});
	}

	@Override
	public void addChildren(List<UiComponent> children) {
		children.add(new ParsingTab(this));
		children.add(new DecompositionTab(this));
		children.add(new MeasurementTab(this));
		children.add(new ClusteringTab(this));
		children.add(new RefactoringTab(this));
	}

	@Override
	public void start() {
		panel.setEnabledAt(0, true);
	}

	public void addComponent(String title, Component component) {
		panel.add(title, component);
		panel.setEnabledAt(panel.getTabCount() - 1, false);
	}

}
