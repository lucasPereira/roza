package br.ufsc.ine.leb.roza.ui.window.content.sidebar.classes;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.model.TestClassFieldsModel;
import br.ufsc.ine.leb.roza.ui.model.TestClassSetupsModel;
import br.ufsc.ine.leb.roza.ui.model.TestClassTestsModel;

public class TestClassInformation implements UiComponent {

	private Hub hub;
	private TestClassesTab testClassesTab;

	public TestClassInformation(Hub hub, TestClassesTab testClassesTab) {
		this.hub = hub;
		this.testClassesTab = testClassesTab;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JTabbedPane panel = new JTabbedPane();
		testClassesTab.addBottomComponent(panel);
		hub.loadTestClassesSubscribe(classes -> reset(panel));
		hub.selectTestClassSubscribe((testClass -> showClass(panel, testClass)));
	}

	@Override
	public void createChilds() {}

	private void reset(JTabbedPane panel) {
		while (panel.getTabCount() > 0) {
			panel.remove(0);
		}
	}

	private void showClass(JTabbedPane panel, TestClass testClass) {
		reset(panel);
		addInformation(panel, "Fields", new TestClassFieldsModel(testClass));
		addInformation(panel, "Setups", new TestClassSetupsModel(testClass));
		addInformation(panel, "Tests", new TestClassTestsModel(testClass));
		panel.setSelectedIndex(panel.getTabCount() - 1);
	}

	private void addInformation(JTabbedPane panel, String title, TableModel model) {
		JTable table = new JTable();
		JScrollPane scroller = new JScrollPane(table);
		table.setModel(model);
		panel.addTab(title, scroller);
	}

}
