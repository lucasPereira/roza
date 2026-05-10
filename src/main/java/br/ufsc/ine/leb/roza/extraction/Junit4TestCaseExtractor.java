package br.ufsc.ine.leb.roza.extraction;


import java.util.List;

public class Junit4TestCaseExtractor extends JunitTestCaseExtractor implements TestCaseExtractor {

	public Junit4TestCaseExtractor() {
		super(List.of("assertArrayEquals", "assertEquals", "assertFalse", "assertNotNull", "assertNotSame", "assertNull", "assertSame", "assertThat", "assertTrue"));
	}

}
