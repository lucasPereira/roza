package br.ufsc.ine.leb.roza.ui;

import java.io.File;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class ClassList implements UiComponent {

	private Hub hub;
	private Sidebar sidebar;

	public ClassList(Hub hub, Sidebar sidebar) {
		this.hub = hub;
		this.sidebar = sidebar;
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
		sidebar.addComponent(scroller);
	}

	@Override
	public void createChilds() {}

}
