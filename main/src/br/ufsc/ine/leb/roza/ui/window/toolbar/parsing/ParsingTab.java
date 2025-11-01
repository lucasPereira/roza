package br.ufsc.ine.leb.roza.ui.window.toolbar.parsing;

import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.toolbar.Toolbar;

public class ParsingTab implements UiComponent {

	private final Toolbar toolbar;
	private JPanel panel;

	public ParsingTab(Toolbar toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JPanel();
		toolbar.addComponent("Parsing", panel);
	}

	@Override
	public void addChildren(List<UiComponent> children) {
		children.add(new ParserComboBox(this));
		children.add(new LoadTestClassesButton(this));
	}

	@Override
	public void start() {}

	public void addComponent(Component component) {
		panel.add(component);
	}
}
