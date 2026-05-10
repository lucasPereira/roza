package br.ufsc.ine.leb.roza.core.selection;

import java.util.List;

import br.ufsc.ine.leb.roza.core.TextFile;

public interface TextFileSelector {

	List<TextFile> select(List<TextFile> files);

}
