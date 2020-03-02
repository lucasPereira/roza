package br.ufsc.ine.leb.roza.extraction;

import java.util.Arrays;

public class Junit4TestCaseExtractor extends JunitTestCaseExtractor implements TestCaseExtractor {

	public Junit4TestCaseExtractor() {
		super(Arrays.asList("assertArrayEquals", "assertEquals", "assertFalse", "assertNotNull", "assertNotSame", "assertNull", "assertSame", "assertThat", "assertTrue"));
	}

}
