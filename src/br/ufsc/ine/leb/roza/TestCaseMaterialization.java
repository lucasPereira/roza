package br.ufsc.ine.leb.roza;

import java.io.File;

public class TestCaseMaterialization<T extends TestBlock> {

	private File file;
	private T testCase;

	public TestCaseMaterialization(File file, T testCase) {
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

	public T getTestCase() {
		return testCase;
	}

}
