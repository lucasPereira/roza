package br.ufsc.ine.leb.roza.ui.window.content.sidebar.tests;

import java.util.List;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.shared.CodePanel;

public class TestCaseCode implements UiComponent {

	private final TestCasesTab testCasesTab;

	public TestCaseCode(TestCasesTab testCasesTab) {
		this.testCasesTab = testCasesTab;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		CodePanel testCodePanel = new CodePanel();
		testCasesTab.addBottomComponent(testCodePanel);
		hub.loadTestClassesSubscribe(testClasses -> testCodePanel.clearTestCase());
		hub.extractTestCasesSubscribe(testCases -> testCodePanel.clearTestCase());
		hub.selectTestCaseSubscribe(testCodePanel::setTestCase);
	}

	@Override
	public void addChildren(List<UiComponent> children) {}

	@Override
	public void start() {}

}
