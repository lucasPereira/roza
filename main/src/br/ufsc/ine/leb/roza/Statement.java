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

	@Override
	public boolean equals(Object object) {
		if (object instanceof Statement) {
			Statement other = (Statement) object;
			return getText().equals(other.getText());
		}
		return super.equals(object);
	}

}
