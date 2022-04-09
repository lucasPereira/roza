package br.ufsc.ine.leb.roza.loading;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.utils.FileUtils;
import br.ufsc.ine.leb.roza.utils.comparator.TextFileComparatorByFileName;

public class ProgramaticTextFileLoader implements TextFileLoader {

	private List<File> files;
	private FileUtils fileUtils;

	public ProgramaticTextFileLoader(List<File> files) {
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
		Collections.sort(textFiles, new TextFileComparatorByFileName());
		return textFiles;
	}

}
