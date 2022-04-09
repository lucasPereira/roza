package br.ufsc.ine.leb.roza.utils.comparator;

import java.util.Comparator;

import br.ufsc.ine.leb.roza.loading.TextFile;

public class TextFileComparatorByFileName implements Comparator<TextFile> {

	@Override
	public int compare(TextFile file1, TextFile file2) {
		return file1.getName().compareTo(file2.getName());
	}

}
