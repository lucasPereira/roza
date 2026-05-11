package br.ufsc.ine.leb.roza.ui.legacy.model;

public class OnlyNumbersAndDots extends RegexFilter {

	@Override
	String getRegex() {
		return "^[0-9.]+$";
	}

}
