package br.ufsc.ine.leb.roza.ui.window.toolbar.extraction;

import java.util.List;

import javax.swing.JButton;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class ExtractTestCasesButton implements UiComponent {

	private ExtractionTab toolbar;
	private JButton button;

	public ExtractTestCasesButton(ExtractionTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		button = new JButton("Extract Test Cases");
		button.setEnabled(false);
		toolbar.addComponent(button);
		button.addActionListener(listner -> {
			List<TestCase> testCases = manager.extractTestCases();
			hub.extractTestCasesPublish(testCases);
		});
		hub.loadTestClassesSubscribe(classes -> button.setEnabled(true));
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
