package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.Set;

final class JunitAssertionMethods {

	private static final Set<String> NAMES = Set.of(
			"assertAll",
			"assertArrayEquals",
			"assertDoesNotThrow",
			"assertEquals",
			"assertFalse",
			"assertInstanceOf",
			"assertIterableEquals",
			"assertLinesMatch",
			"assertNotEquals",
			"assertNotNull",
			"assertNotSame",
			"assertNull",
			"assertSame",
			"assertThat",
			"assertThrows",
			"assertThrowsExactly",
			"assertTimeout",
			"assertTimeoutPreemptively",
			"assertTrue",
			"fail");

	private JunitAssertionMethods() {
	}

	static boolean contains(String name) {
		return NAMES.contains(name);
	}
}
