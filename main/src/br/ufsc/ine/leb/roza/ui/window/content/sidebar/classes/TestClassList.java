package br.ufsc.ine.leb.roza.ui.window.content.sidebar.classes;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class TestClassList implements UiComponent {

	private Hub hub;
	private TestClassesTab testClassesTab;

	public TestClassList(Hub hub, TestClassesTab testClassesTab) {
		this.hub = hub;
		this.testClassesTab = testClassesTab;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JList<TestClass> list = new JList<>();
		JScrollPane scroller = new JScrollPane(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		testClassesTab.addTopComponent(scroller);
		hub.loadTestClassesSubscribe((List<TestClass> classes) -> {
			listLoadedClasses(list, classes);
		});
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent event) {
				TestClass testClass = list.getSelectedValue();
				if (testClass != null && !event.getValueIsAdjusting()) {
					hub.selectTestClassPublish(testClass);
				}
			}

		});
	}

	@Override
	public void createChilds() {}

	private void listLoadedClasses(JList<TestClass> list, List<TestClass> classes) {
		DefaultListModel<TestClass> model = new DefaultListModel<>();
		list.setModel(model);
		for (TestClass testClass : classes) {
			model.addElement(testClass);
		}
	}

}
