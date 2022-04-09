package br.ufsc.ine.leb.roza.selection;

import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.loading.TextFile;

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
