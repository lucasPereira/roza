package br.ufsc.ine.leb.roza;

import java.io.File;

public class TestCaseMaterialization {

	private final File file;
	private final TestCase testCase;
	private final Integer length;

	public TestCaseMaterialization(File file, Integer length, TestCase testCase) {
		this.file = file;
		this.length = length;
		this.testCase = testCase;
	}

	public String getFileName() {
		return file.getName();
	}

	public String getFilePath() {
		return file.getPath();
	}

	public String getAbsoluteFilePath() {
		return file.getAbsolutePath();
	}

	public String getFolder() {
		return file.getParent();
	}

	public TestCase getTestCase() {
		return testCase;
	}

	public Integer getLength() {
		return length;
	}

	@Override
	public String toString() {
		return String.format("%s -> %s", testCase.getName(), file.getName());
	}

}
