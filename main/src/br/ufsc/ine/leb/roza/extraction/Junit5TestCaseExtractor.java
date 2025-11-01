package br.ufsc.ine.leb.roza.extraction;


import java.util.List;

public class Junit5TestCaseExtractor extends JunitTestCaseExtractor implements TestCaseExtractor {

	public Junit5TestCaseExtractor() {
		super(List.of("assertEquals", "assertNotEquals", "assertTrue", "assertFalse", "assertNull", "assertNotNull", "assertSame", "assertNotSame", "assertArrayEquals", "assertIterableEquals", "assertLinesMatch", "assertAll", "assertTimeout", "assertTimeoutPreemptively", "assertThrows", "assertDoesNotThrow"));
	}

}
