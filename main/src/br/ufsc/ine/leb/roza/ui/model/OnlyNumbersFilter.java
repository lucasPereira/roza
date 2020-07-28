package br.ufsc.ine.leb.roza.ui.model;

public class OnlyNumbersFilter extends RegexFilter {

	@Override
	String getRegex() {
		return "^[0-9]+$";
	}

}
