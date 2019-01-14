package br.ufsc.ine.leb.roza;

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

	public Boolean hasExtension(String extension) {
		return name.toLowerCase().endsWith(String.format(".%s", extension.toLowerCase()));
	}

}
