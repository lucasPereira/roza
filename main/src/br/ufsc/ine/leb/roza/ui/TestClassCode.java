package br.ufsc.ine.leb.roza.ui;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

public class TestClassCode implements UiComponent {

	private TestClassesTab testClassesTab;

	public TestClassCode(TestClassesTab testClassesTab) {
		this.testClassesTab = testClassesTab;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JTextPane code = new JTextPane();
		try {
			code.getStyledDocument().insertString(0, "lucas", null);
		} catch (BadLocationException exception) {
			exception.printStackTrace();
		}
		testClassesTab.addBottomComponent(code);
	}

	@Override
	public void createChilds() {}

}
