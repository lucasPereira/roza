package br.ufsc.ine.leb.roza.ui.window.content.sidebar.tests;

import javax.swing.JEditorPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class TestCaseCode implements UiComponent {

	private Hub hub;
	private TestCasesTab testCasesTab;
	private Integer line;

	public TestCaseCode(Hub hub, TestCasesTab testCasesTab) {
		this.hub = hub;
		this.testCasesTab = testCasesTab;
		this.line = 0;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JEditorPane pane = new JEditorPane();
		JScrollPane scroller = new JScrollPane(pane);
		HTMLEditorKit editor = new HTMLEditorKit();
		StyleSheet sheet = editor.getStyleSheet();
		Document document = editor.createDefaultDocument();
		pane.setEditorKit(editor);
		pane.setDocument(document);
		pane.setContentType("text/html");
		addStyle(sheet, "ul", "margin", "0");
		addStyle(sheet, "ul", "padding", "0");
		addStyle(sheet, "ul", "list-style-type", "none");
		addStyle(sheet, "li", "margin", "0");
		addStyle(sheet, "li", "white-space", "nowrap");
		addStyle(sheet, "li", "padding", "4px 8px 4px 8px");
		addStyle(sheet, "li", "font-family", "monospace");
		addStyle(sheet, "li", "color", "#444444");
		addStyle(sheet, "li.even-fixture", "background-color", "#F0F0F0");
		addStyle(sheet, "li.even-assert", "background-color", "#F0F0F0");
		addStyle(sheet, "li.odd-fixture", "background-color", "#FFFFFF");
		addStyle(sheet, "li.odd-assert", "background-color", "#FFFFFF");
		 addStyle(sheet, "li.even-assert", "color", "#999999");
		 addStyle(sheet, "li.odd-assert", "color", "999999");
		testCasesTab.addBottomComponent(scroller);
		hub.selectTestCaseSubscribe(testCase -> {
			line = 1;
			StringBuilder code = new StringBuilder();
			code.append("<html>");
			code.append("<body>");
			code.append("<ul>");
			testCase.getFixtures().forEach(fixture -> appendCode(code, fixture, "fixture"));
			testCase.getAsserts().forEach(assertion -> appendCode(code, assertion, "assert"));
			code.append("</ul>");
			code.append("</body>");
			code.append("</html>");
			pane.setText(code.toString());
			updateScroll(scroller);
		});
	}

	private void updateScroll(JScrollPane scroller) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JScrollBar vertical = scroller.getVerticalScrollBar();
				JScrollBar horizontal = scroller.getHorizontalScrollBar();
				vertical.setValue(vertical.getMinimum());
				horizontal.setValue(horizontal.getMinimum());
			}

		});
	}

	private void addStyle(StyleSheet sheet, String selector, String property, String value) {
		String rule = String.format("%s { %s: %s; }", selector, property, value);
		System.out.println(rule);
		sheet.addRule(rule);
	}

	private void appendCode(StringBuilder code, Statement statement, String type) {
		String color = line % 2 == 0 ? "even" : "odd";
		code.append(String.format("<li class=\"%s-%s\">", color, type));
		code.append(statement.getText());
		code.append("</li>");
		line++;
	}

	@Override
	public void createChilds() {}

}
