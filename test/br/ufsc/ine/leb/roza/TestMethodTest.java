package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestMethodTest {

	@Test
	void create() throws Exception {
		TestMethod exampleTestMethod = new TestMethod("example");
		assertEquals("example", exampleTestMethod.getName());
	}

}
