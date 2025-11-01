package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class TestMethodTest {

	@Test
	void create() {
		TestMethod exampleTestMethod = new TestMethod("example", List.of(new Statement("assertEquals(0, 0);")));
		assertEquals("example", exampleTestMethod.getName());
		assertEquals(1, exampleTestMethod.getStatements().size());
		assertEquals("assertEquals(0, 0);", exampleTestMethod.getStatements().get(0).getText());
	}

}
