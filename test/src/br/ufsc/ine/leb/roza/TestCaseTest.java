package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

class TestCaseTest {

	@Test
	void create() {
		Statement fixtureStatement = new Statement("System.out.println(0);");
		Statement assertStatement = new Statement("assertEqulas(0, 0);");
		TestCase test = new TestCase("example", List.of(fixtureStatement), List.of(assertStatement));
		assertNotNull(test.getId());
		assertEquals("example", test.getName());
		assertEquals(1, test.getFixtures().size());
		assertEquals("System.out.println(0);", test.getFixtures().get(0).getText());
		assertEquals(1, test.getAsserts().size());
		assertEquals("assertEqulas(0, 0);", test.getAsserts().get(0).getText());
	}

}
