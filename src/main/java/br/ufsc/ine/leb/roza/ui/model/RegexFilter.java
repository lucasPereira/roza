package br.ufsc.ine.leb.roza.ui.model;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public abstract class RegexFilter extends DocumentFilter {

	@Override
	public final void remove(FilterBypass filter, int offset, int length) throws BadLocationException {
		Document document = filter.getDocument();
		String current = document.getText(0, document.getLength());
		if (current.length() - length > 0) {
			super.remove(filter, offset, length);
		}
	}

	@Override
	public final void replace(FilterBypass filter, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		Document document = filter.getDocument();
		String current = document.getText(0, document.getLength());
		String next = current.substring(0, offset) + text + current.substring(offset);
		String regex = getRegex();
		if (next.matches(regex)) {
			super.replace(filter, offset, length, text, attrs);
		}
	}

	abstract String getRegex();

}
