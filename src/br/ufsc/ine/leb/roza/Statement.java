package br.ufsc.ine.leb.roza;

public class Statement {

	private String text;

	public Statement(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return getText();
	}

}
