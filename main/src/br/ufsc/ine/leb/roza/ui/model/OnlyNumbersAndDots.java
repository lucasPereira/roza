package br.ufsc.ine.leb.roza.ui.model;

public class OnlyNumbersAndDots extends RegexFilter {

	@Override
	String getRegex() {
		return "^[0-9.]+$";
	}

}
