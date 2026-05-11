package br.ufsc.ine.leb.roza.ui.legacy.window.content.sidebar.classes;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import br.ufsc.ine.leb.roza.core.legacy.TestClass;
import br.ufsc.ine.leb.roza.ui.legacy.Hub;
import br.ufsc.ine.leb.roza.ui.legacy.Manager;
import br.ufsc.ine.leb.roza.ui.legacy.UiComponent;
import br.ufsc.ine.leb.roza.ui.legacy.model.TestClassFieldsModel;
import br.ufsc.ine.leb.roza.ui.legacy.model.TestClassSetupsModel;
import br.ufsc.ine.leb.roza.ui.legacy.model.TestClassTestsModel;

public class TestClassInformation implements UiComponent {

	private final TestClassesTab testClassesTab;

	public TestClassInformation(TestClassesTab testClassesTab) {
		this.testClassesTab = testClassesTab;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		JTabbedPane panel = new JTabbedPane();
		testClassesTab.addBottomComponent(panel);
		hub.loadTestClassesSubscribe(classes -> reset(panel));
		hub.selectTestClassSubscribe((testClass -> showClass(panel, testClass)));
	}

	@Override
	public void addChildren(List<UiComponent> children) {}

	@Override
	public void start() {}

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
