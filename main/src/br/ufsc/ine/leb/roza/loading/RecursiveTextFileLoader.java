package br.ufsc.ine.leb.roza.loading;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.utils.FileUtils;
import br.ufsc.ine.leb.roza.utils.FolderUtils;
import br.ufsc.ine.leb.roza.utils.comparator.TextFileComparatorByFileName;

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
		Collections.sort(textFiles, new TextFileComparatorByFileName());
		return textFiles;
	}

}
