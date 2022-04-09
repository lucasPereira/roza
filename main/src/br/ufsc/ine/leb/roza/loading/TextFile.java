package br.ufsc.ine.leb.roza.loading;

public class TextFile {

	private String name;
	private String content;

	public TextFile(String name, String content) {
		this.name = name;
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public String getContent() {
		return content;
	}

}
