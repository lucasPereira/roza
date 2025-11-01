package br.ufsc.ine.leb.roza.ui.window.toolbar.parsing;

import java.util.List;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.parsing.Junit4TestClassParser;
import br.ufsc.ine.leb.roza.parsing.Junit5TestClassParser;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.shared.ComboBoxBuilder;

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
