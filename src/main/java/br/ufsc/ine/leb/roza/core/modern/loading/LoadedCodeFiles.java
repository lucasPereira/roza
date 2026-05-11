package br.ufsc.ine.leb.roza.core.modern.loading;

import java.util.List;
import java.util.Objects;

public final class LoadedCodeFiles {

	private final List<CodeFile> codeFiles;

	public LoadedCodeFiles(List<CodeFile> codeFiles) {
		this.codeFiles = List.copyOf(Objects.requireNonNull(codeFiles));
	}

	public List<CodeFile> codeFiles() {
		return codeFiles;
	}
}
