package br.ufsc.ine.leb.roza.ui.window.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.parsing.Junit4TestClassParser;
import br.ufsc.ine.leb.roza.parsing.Junit5TestClassParser;
import br.ufsc.ine.leb.roza.parsing.TestClassParser;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class ParserComboBox implements UiComponent {

	private Manager manager;
	private Toolbar toolbar;

	public ParserComboBox(Manager manager, Toolbar toolbar) {
		this.manager = manager;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JComboBox<String> combo = new JComboBox<String>();
		combo.setToolTipText("Parser");
		combo.addItem("JUnit 4");
		combo.addItem("JUnit 5");
		combo.setSelectedIndex(1);
		combo.setMaximumSize(combo.getPreferredSize());
		manager.setTestClassParser(new Junit5TestClassParser());
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				Integer selected = combo.getSelectedIndex();
				switch (selected) {
					case 0:
						selectParser(new Junit4TestClassParser());
						break;
					case 1:
						selectParser(new Junit5TestClassParser());
					default:
						break;
				}
			}

			private void selectParser(TestClassParser parser) {
				manager.setTestClassParser(parser);
			}

		});
		toolbar.addComponent(combo);
	}

	@Override
	public void createChilds() {}

}
