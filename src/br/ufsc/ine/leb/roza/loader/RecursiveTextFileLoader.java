package br.ufsc.ine.leb.roza.loader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.TextFile;

public class RecursiveTextFileLoader implements TextFileLoader {

	private String baseFolder;

	public RecursiveTextFileLoader(String baseFolder) {
		this.baseFolder = baseFolder;
	}

	@Override
	public List<TextFile> load() {
		List<TextFile> files = new LinkedList<>();
		File base = new File(baseFolder);
		load(base, files);
		Collections.sort(files, new TextFileComparator());
		return files;
	}

	private void load(File base, List<TextFile> files) {
		for (File child : base.listFiles()) {
			if (child.isDirectory()) {
				load(child, files);
			} else if (child.isFile()) {
				String content = read(child);
				files.add(new TextFile(child.getName(), content));
			}
		}
	}

	private String read(File child) {
		try {
			byte[] bytes = Files.readAllBytes(child.toPath());
			return new String(bytes);
		} catch (IOException excecao) {
			throw new RuntimeException(excecao);
		}
	}

	private class TextFileComparator implements Comparator<TextFile> {

		@Override
		public int compare(TextFile file1, TextFile file2) {
			return file1.getName().compareTo(file2.getName());
		}

	}

}
