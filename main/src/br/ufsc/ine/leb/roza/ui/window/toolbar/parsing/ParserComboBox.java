package br.ufsc.ine.leb.roza.ui.window.toolbar.parsing;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.parsing.Junit4TestClassParser;
import br.ufsc.ine.leb.roza.parsing.Junit5TestClassParser;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.shared.ComboBoxBuilder;

public class ParserComboBox implements UiComponent {

	private Manager manager;
	private ParsingTab toolbar;

	public ParserComboBox(Manager manager, ParsingTab toolbar) {
		this.manager = manager;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JComboBox<String> combo = new ComboBoxBuilder("Parser").add("JUnit 4", () -> {
			manager.setTestClassParser(new Junit4TestClassParser());
		}).add("JUnit 5", () -> {
			manager.setTestClassParser(new Junit5TestClassParser());
		}).build();
		combo.setSelectedIndex(1);
		combo.setEnabled(true);
		toolbar.addComponent(combo);
	}

	@Override
	public void createChilds() {}

}
