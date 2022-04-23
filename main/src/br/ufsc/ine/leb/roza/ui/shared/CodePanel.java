package br.ufsc.ine.leb.roza.ui.shared;

import javax.swing.JEditorPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import br.ufsc.ine.leb.roza.extraction.TestCase;
import br.ufsc.ine.leb.roza.parsing.RozaStatement;

public class CodePanel extends JScrollPane {

	private static final long serialVersionUID = 1L;

	private JEditorPane pane;

	public CodePanel() {
		super(new JEditorPane());
		pane = (JEditorPane) getViewport().getView();
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
		addStyle(sheet, "li.odd-fixture", "sbackground-color", "#FFFFFF");
		addStyle(sheet, "li.odd-assert", "background-color", "#FFFFFF");
		addStyle(sheet, "li.even-assert", "color", "#999999");
		addStyle(sheet, "li.odd-assert", "color", "999999");
	}

	public void clearTestCase() {
		pane.setText("");
	}

	public void setTestCase(TestCase testCase) {
		Integer line = 1;
		StringBuilder code = new StringBuilder();
		code.append("<html>");
		code.append("<body>");
		code.append("<ul>");
		for (RozaStatement fixture : testCase.getFixtures()) {
			appendCode(line++, code, fixture, "fixture");
		}
		for (RozaStatement assertion : testCase.getAsserts()) {
			appendCode(line++, code, assertion, "assert");
		}
		code.append("</ul>");
		code.append("</body>");
		code.append("</html>");
		pane.setText(code.toString());
		updateScroll();
	}

	private void addStyle(StyleSheet sheet, String selector, String property, String value) {
		String rule = String.format("%s { %s: %s; }", selector, property, value);
		sheet.addRule(rule);
	}

	private void appendCode(Integer line, StringBuilder code, RozaStatement statement, String type) {
		String color = line % 2 == 0 ? "even" : "odd";
		code.append(String.format("<li class=\"%s-%s\">%02d. %s</li>", color, type, line, statement.toCode()));
	}

	private void updateScroll() {
		CodePanel scroller = this;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JScrollBar vertical = scroller.getVerticalScrollBar();
				JScrollBar horizontal = scroller.getHorizontalScrollBar();
				vertical.setValue(vertical.getMinimum());
				horizontal.setValue(horizontal.getMinimum());
				scroller.revalidate();
				scroller.repaint();
			}

		});
	}

}
