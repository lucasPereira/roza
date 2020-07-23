package br.ufsc.ine.leb.roza.ui.window.toolbar;

import java.awt.Component;

import javax.swing.JToolBar;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.Window;

public class Toolbar implements UiComponent {

	private Hub hub;
	private Manager manager;
	private Window window;
	private JToolBar bar;

	public Toolbar(Hub hub, Manager manager, Window window) {
		this.hub = hub;
		this.manager = manager;
		this.window = window;
		init();
		createChilds();
	}

	@Override
	public void init() {
		bar = new JToolBar();
		window.addComponent(bar);
	}

	@Override
	public void createChilds() {
		new ParserComboBox(manager, this);
		new LoadTestClassesButton(hub, manager, this);
		addComponent(new JToolBar.Separator());
		new ExtractorComboBox(hub, manager, this);
		new ExtractTestCasesButton(hub, manager, this);
		addComponent(new JToolBar.Separator());
		new MeasurerComboBox(hub, manager, this);
		new DeckardConfigurationInputs(hub, this);
	}

	public void addComponent(Component component) {
		bar.add(component);
	}

}
