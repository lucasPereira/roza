package br.ufsc.ine.leb.roza.core.legacy.selection;

import java.util.List;

import br.ufsc.ine.leb.roza.core.legacy.TextFile;

public interface TextFileSelector {

	List<TextFile> select(List<TextFile> files);

}
