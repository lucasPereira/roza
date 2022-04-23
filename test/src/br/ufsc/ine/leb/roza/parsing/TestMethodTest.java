package br.ufsc.ine.leb.roza.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.fixture.ParsingFixtures;

class TestMethodTest {

	@Test
	void test() throws Exception {
		TestMethod method = ParsingFixtures.testMethod("test");
		assertEquals("test", method.getName());
		assertEquals(0, method.getStatements().size());
	}

}
