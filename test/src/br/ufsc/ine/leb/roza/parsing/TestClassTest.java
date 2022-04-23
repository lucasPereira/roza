package br.ufsc.ine.leb.roza.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.fixture.ParsingFixtures;

class TestClassTest {

	@Test
	void test() throws Exception {
		TestClass testClass = ParsingFixtures.testClass("ExampleTest");

		assertEquals("ExampleTest", testClass.getName());
		assertEquals(0, testClass.getFields().size());
		assertEquals(0, testClass.getSetupMethods().size());
		assertEquals(0, testClass.getTestMethods().size());
	}

}
