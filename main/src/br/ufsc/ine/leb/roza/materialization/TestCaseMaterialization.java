package br.ufsc.ine.leb.roza.materialization;

import java.io.File;

import br.ufsc.ine.leb.roza.extraction.TestCase;

public class TestCaseMaterialization {

	private File file;
	private TestCase testCase;
	private Integer lenght;

	public TestCaseMaterialization(File file, Integer lenght, TestCase testCase) {
		this.file = file;
		this.lenght = lenght;
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
		return lenght;
	}

	@Override
	public String toString() {
		return String.format("%s -> %s", testCase.getName(), file.getName());
	}

}
