package br.ufsc.ine.leb.roza.utils.comparator;

import java.io.File;
import java.util.Comparator;

public class FileComparatorByPath implements Comparator<File> {

	@Override
	public int compare(File first, File second) {
		return first.getAbsolutePath().compareTo(second.getAbsolutePath());
	}

}
