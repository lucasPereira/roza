package br.ufsc.ine.leb.roza.ui.window.content.sidebar.tests;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.model.TestCaseRenderer;

public class TestCaseList implements UiComponent {

	private Hub hub;
	private TestCasesTab testCasesTab;

	public TestCaseList(Hub hub, TestCasesTab testsTab) {
		this.hub = hub;
		this.testCasesTab = testsTab;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JList<TestCase> list = new JList<>();
		JScrollPane scroller = new JScrollPane(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		testCasesTab.addTopComponent(scroller);
		hub.loadTestClassesSubscribe(testClasses -> {
			list.setModel(new DefaultListModel<>());
		});
		hub.extractTestCasesSubscribe(testCases -> {
			DefaultListModel<TestCase> model = new DefaultListModel<>();
			list.setModel(model);
			list.setCellRenderer(new TestCaseRenderer());
			testCases.forEach(testCase -> model.addElement(testCase));
		});
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent event) {
				TestCase testCase = list.getSelectedValue();
				if (testCase != null && !event.getValueIsAdjusting()) {
					hub.selectTestCasePublish(testCase);
				}
			}

		});
	}

	@Override
	public void createChilds() {}

}
