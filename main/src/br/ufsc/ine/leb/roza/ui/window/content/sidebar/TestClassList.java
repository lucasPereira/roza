package br.ufsc.ine.leb.roza.ui.window.content.sidebar;

import java.io.File;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

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
		JList<String> list = new JList<>();
		JScrollPane scroller = new JScrollPane(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		hub.loadClassesSubscribe((List<File> classes) -> {
			DefaultListModel<String> model = new DefaultListModel<>();
			list.setModel(model);
			for (File file : classes) {
				model.addElement(file.getName());
			}
		});
		testClassesTab.addTopComponent(scroller);
	}

	@Override
	public void createChilds() {}

}
