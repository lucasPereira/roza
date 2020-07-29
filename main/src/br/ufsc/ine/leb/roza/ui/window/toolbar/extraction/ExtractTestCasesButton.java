package br.ufsc.ine.leb.roza.ui.window.toolbar.extraction;

import java.util.List;

import javax.swing.JButton;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.toolbar.Toolbar;

public class ExtractTestCasesButton implements UiComponent {

	private Hub hub;
	private Manager manager;
	private Toolbar toolbar;

	public ExtractTestCasesButton(Hub hub, Manager manager, Toolbar toolbar) {
		this.hub = hub;
		this.manager = manager;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JButton button = new JButton("Extract Test Cases");
		button.setEnabled(false);
		toolbar.addComponent(button);
		hub.loadTestClassesSubscribe(classes -> button.setEnabled(true));
		button.addActionListener(listner -> {
			List<TestCase> testCases = manager.extractTestCases();
			hub.extractTestCasesPublish(testCases);
		});
	}

	@Override
	public void createChilds() {}

}
