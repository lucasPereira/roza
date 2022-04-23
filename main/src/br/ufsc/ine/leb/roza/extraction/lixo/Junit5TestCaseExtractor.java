package br.ufsc.ine.leb.roza.extraction.lixo;

import java.util.Arrays;

import br.ufsc.ine.leb.roza.extraction.TestCaseExtractor;

public class Junit5TestCaseExtractor extends JunitTestCaseExtractor implements TestCaseExtractor {

	public Junit5TestCaseExtractor() {
		super(Arrays.asList("assertEquals", "assertNotEquals", "assertTrue", "assertFalse", "assertNull", "assertNotNull", "assertSame", "assertNotSame", "assertArrayEquals", "assertIterableEquals", "assertLinesMatch", "assertAll", "assertTimeout", "assertTimeoutPreemptively", "assertThrows", "assertDoesNotThrow"));
	}

}
