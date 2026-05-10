package br.ufsc.ine.leb.roza.core.legacy.selection;

import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.core.legacy.TextFile;

public class JavaExtensionTextFileSelector implements TextFileSelector {

	@Override
	public List<TextFile> select(List<TextFile> textFiles) {
		List<TextFile> selected = new LinkedList<>();
		textFiles.forEach((textFile) -> {
			if (textFile.getName().endsWith(".java")) {
				selected.add(textFile);
			}
		});
		return selected;
	}

}
