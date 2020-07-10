package br.ufsc.ine.leb.roza.ui.window.content.sidebar;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class TestClassCode implements UiComponent {

	private Hub hub;
	private TestClassesTab testClassesTab;

	public TestClassCode(Hub hub, TestClassesTab testClassesTab) {
		this.hub = hub;
		this.testClassesTab = testClassesTab;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JTextPane code = new JTextPane();
		JScrollPane scroller = new JScrollPane(code);
		testClassesTab.addBottomComponent(scroller);
		hub.selectTestClassSubscribe((TestClass testClass) -> {
			showClass(code, testClass);
		});
	}

	@Override
	public void createChilds() {}

	private void showClass(JTextPane code, TestClass testClass) {
		try {
			String content = testClass.getName();
			StyledDocument document = code.getStyledDocument();
			document.remove(0, document.getLength());
			document.insertString(0, content, null);
		} catch (BadLocationException exception) {
			exception.printStackTrace();
		}
	}

}
