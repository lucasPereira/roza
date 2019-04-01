package br.ufsc.ine.leb.roza;

import java.io.File;

public class TestCaseMaterialization {

	private File file;
	private TestCase testCase;

	public TestCaseMaterialization(File file, TestCase testCase) {
		this.file = file;
		this.testCase = testCase;
	}

	public String getFileName() {
		return file.getName();
	}

	public String getFilePath() {
		return file.getPath();
	}

	public String getFolder() {
		return file.getParent();
	}

	public TestCase getTestCase() {
		return testCase;
	}

}
