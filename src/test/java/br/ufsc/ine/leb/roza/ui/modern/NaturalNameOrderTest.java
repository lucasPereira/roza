package br.ufsc.ine.leb.roza.ui.modern;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class NaturalNameOrderTest {

	@Test
	void shouldOrderNumberedSuffixesNumerically() {
		List<String> names = new ArrayList<>(List.of("TestClass10", "TestClass1", "TestClass2"));
		names.sort(NaturalNameOrder.INSTANCE);
		assertEquals(List.of("TestClass1", "TestClass2", "TestClass10"), names);
	}

	@Test
	void shouldOrderHelperClassSuffixesNumerically() {
		List<String> names = new ArrayList<>(List.of("HelperClass10", "HelperClass2", "HelperClass1"));
		names.sort(NaturalNameOrder.INSTANCE);
		assertEquals(List.of("HelperClass1", "HelperClass2", "HelperClass10"), names);
	}

	@Test
	void shouldOrderPathsByDirectoryThenFileName() {
		List<String> paths = new ArrayList<>(List.of(
				"example/tests/TestClass10.java",
				"example/helpers/Helper.java",
				"example/tests/TestClass2.java",
				"example/tests/TestClass1.java"));
		paths.sort(NaturalNameOrder.PATH);
		assertEquals(List.of(
				"example/helpers/Helper.java",
				"example/tests/TestClass1.java",
				"example/tests/TestClass2.java",
				"example/tests/TestClass10.java"), paths);
	}
}
