package br.ufsc.ine.leb.roza.ui.legacy.window.toolbar.decomposition;

import java.util.List;

import javax.swing.JButton;

import br.ufsc.ine.leb.roza.core.legacy.TestCase;
import br.ufsc.ine.leb.roza.ui.legacy.Hub;
import br.ufsc.ine.leb.roza.ui.legacy.Manager;
import br.ufsc.ine.leb.roza.ui.legacy.UiComponent;

public class ExtractTestCasesButton implements UiComponent {

	private final DecompositionTab toolbar;

		public ExtractTestCasesButton(DecompositionTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		JButton button = new JButton("Extract Test Cases");
		toolbar.addComponent(button);
		button.addActionListener(listener -> {
			List<TestCase> testCases = manager.extractTestCases();
			hub.extractTestCasesPublish(testCases);
			hub.infoMessagePublish(String.format("Extracted tests: %d", testCases.size()));
		});
	}

	@Override
	public void addChildren(List<UiComponent> children) {}

	@Override
	public void start() {}

}
