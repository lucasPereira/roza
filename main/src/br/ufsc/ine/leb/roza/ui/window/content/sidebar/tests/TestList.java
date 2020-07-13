package br.ufsc.ine.leb.roza.ui.window.content.sidebar.tests;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.model.TestCaseRenderer;

public class TestList implements UiComponent {

	private Hub hub;
	private TestsTab testsTab;

	public TestList(Hub hub, TestsTab testsTab) {
		this.hub = hub;
		this.testsTab = testsTab;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JList<TestCase> list = new JList<>();
		JScrollPane scroller = new JScrollPane(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		testsTab.addTopComponent(scroller);
		hub.extractTestCasesSubscribe(testCases -> {
			DefaultListModel<TestCase> model = new DefaultListModel<>();
			list.setModel(model);
			list.setCellRenderer(new TestCaseRenderer());
			testCases.forEach(testCase -> model.addElement(testCase));
		});
	}

	@Override
	public void createChilds() {}

}
