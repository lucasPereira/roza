package br.ufsc.ine.leb.roza.loading;

import java.io.File;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.TextFile;
import br.ufsc.ine.leb.roza.utils.FileUtils;

public class ProgrammaticTextFileLoader implements TextFileLoader {

	private final List<File> files;
	private final FileUtils fileUtils;

	public ProgrammaticTextFileLoader(List<File> files) {
		this.files = files;
		this.fileUtils = new FileUtils();
	}

	@Override
	public List<TextFile> load() {
		List<TextFile> textFiles = new LinkedList<>();
		for (File file : files) {
			String name = file.getName();
			String content = fileUtils.readContetAsString(file);
			TextFile textFile = new TextFile(name, content);
			textFiles.add(textFile);
		}
		textFiles.sort(new TextFileComparator());
		return textFiles;
	}

	private static class TextFileComparator implements Comparator<TextFile> {

		@Override
		public int compare(TextFile file1, TextFile file2) {
			return file1.getName().compareTo(file2.getName());
		}

	}

}
