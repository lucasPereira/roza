package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.util.Objects;

public final class CloneFragment {

	private final String sourceFile;
	private final String targetFile;
	private final int startLine;
	private final int endLine;

	public CloneFragment(String sourceFile, String targetFile, int startLine, int endLine) {
		if (startLine <= 0) {
			throw new IllegalArgumentException("Start line must be greater than zero.");
		}
		if (endLine < startLine) {
			throw new IllegalArgumentException("End line must not be smaller than start line.");
		}
		this.sourceFile = Objects.requireNonNull(sourceFile);
		this.targetFile = Objects.requireNonNull(targetFile);
		this.startLine = startLine;
		this.endLine = endLine;
	}

	public String sourceFile() {
		return sourceFile;
	}

	public String targetFile() {
		return targetFile;
	}

	public int startLine() {
		return startLine;
	}

	public int endLine() {
		return endLine;
	}
}
