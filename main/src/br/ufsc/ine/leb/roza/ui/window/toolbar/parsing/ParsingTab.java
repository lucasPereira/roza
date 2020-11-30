package br.ufsc.ine.leb.roza.ui.window.toolbar.parsing;

import java.awt.Component;

import javax.swing.JPanel;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.toolbar.Toolbar;

public class ParsingTab implements UiComponent {

	private Hub hub;
	private Manager manager;
	private Toolbar toolbar;
	private JPanel panel;

	public ParsingTab(Hub hub, Manager manager, Toolbar toolbar) {
		this.hub = hub;
		this.manager = manager;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		panel = new JPanel();
		toolbar.addComponent("Parsing", panel);
	}

	@Override
	public void createChilds() {
		new ParserComboBox(manager, this);
		new LoadTestClassesButton(hub, manager, this);
	}

	public void addComponent(Component component) {
		panel.add(component);
	}

}
