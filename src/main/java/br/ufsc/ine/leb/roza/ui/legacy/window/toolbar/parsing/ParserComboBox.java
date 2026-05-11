package br.ufsc.ine.leb.roza.ui.legacy.window.toolbar.parsing;

import java.util.List;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.core.legacy.parsing.Junit4TestClassParser;
import br.ufsc.ine.leb.roza.core.legacy.parsing.Junit5TestClassParser;
import br.ufsc.ine.leb.roza.ui.legacy.Hub;
import br.ufsc.ine.leb.roza.ui.legacy.Manager;
import br.ufsc.ine.leb.roza.ui.legacy.UiComponent;
import br.ufsc.ine.leb.roza.ui.legacy.shared.ComboBoxBuilder;

public class ParserComboBox implements UiComponent {

	private final ParsingTab toolbar;
	private JComboBox<String> combo;

	public ParserComboBox(ParsingTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		combo = new ComboBoxBuilder("Parser").add("JUnit 4", () -> manager.setTestClassParser(new Junit4TestClassParser())).add("JUnit 5", () -> manager.setTestClassParser(new Junit5TestClassParser())).build();
		toolbar.addComponent(combo);
	}

	@Override
	public void addChildren(List<UiComponent> children) {}

	@Override
	public void start() {
		combo.setSelectedIndex(1);
	}

}
