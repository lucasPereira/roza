package br.ufsc.ine.leb.roza.ui.window.content.sidebar.tests;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.shared.CodePanel;

public class TestCaseCode implements UiComponent {

	private Hub hub;
	private TestCasesTab testCasesTab;

	public TestCaseCode(Hub hub, TestCasesTab testCasesTab) {
		this.hub = hub;
		this.testCasesTab = testCasesTab;
		init();
		createChilds();
	}

	@Override
	public void init() {
		CodePanel testCodePanel = new CodePanel();
		testCasesTab.addBottomComponent(testCodePanel);
		hub.loadTestClassesSubscribe(testClasses -> {
			testCodePanel.clearTestCase();
		});
		hub.extractTestCasesSubscribe(testCases -> {
			testCodePanel.clearTestCase();
		});
		hub.selectTestCaseSubscribe(testCase -> {
			testCodePanel.setTestCase(testCase);
		});
	}

	@Override
	public void createChilds() {}

}
