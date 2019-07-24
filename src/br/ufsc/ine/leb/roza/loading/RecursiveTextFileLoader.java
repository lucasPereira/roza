package br.ufsc.ine.leb.roza.loading;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.TextFile;
import br.ufsc.ine.leb.roza.utils.FileUtils;
import br.ufsc.ine.leb.roza.utils.FolderUtils;

public class RecursiveTextFileLoader implements TextFileLoader {

	private FileUtils fileUtils;
	private FolderUtils folderUtils;

	public RecursiveTextFileLoader(String baseFolder) {
		fileUtils = new FileUtils();
		folderUtils = new FolderUtils(baseFolder);
	}

	@Override
	public List<TextFile> load() {
		List<TextFile> textFiles = new LinkedList<>();
		List<File> files = folderUtils.listFilesRecursively();
		for (File file : files) {
			String name = file.getName();
			String content = fileUtils.readContetAsString(file);
			TextFile textFile = new TextFile(name, content);
			textFiles.add(textFile);
		}
		Collections.sort(textFiles, new TextFileComparator());
		return textFiles;
	}

	private class TextFileComparator implements Comparator<TextFile> {

		@Override
		public int compare(TextFile file1, TextFile file2) {
			return file1.getName().compareTo(file2.getName());
		}

	}

}
