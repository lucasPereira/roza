package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class TestMethodTest {

	@Test
	void create() throws Exception {
		TestMethod exampleTestMethod = new TestMethod("example", Arrays.asList(new Statement("assertEquals(0, 0);")));
		assertEquals("example", exampleTestMethod.getName());
		assertEquals(1, exampleTestMethod.getStatements().size());
		assertEquals("assertEquals(0, 0);", exampleTestMethod.getStatements().get(0).getText());
	}

}
