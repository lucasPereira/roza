package br.ufsc.ine.leb.roza.ui.window.toolbar.decomposition;

import java.util.List;

import javax.swing.JButton;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;

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
