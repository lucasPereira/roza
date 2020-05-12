package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class TestCaseTest {

	@Test
	void create() throws Exception {
		Statement fixtureStatement = new Statement("System.out.println(0);");
		Statement assertStatement = new Statement("assertEqulas(0, 0);");
		TestCase test = new TestCase("example", Arrays.asList(fixtureStatement), Arrays.asList(assertStatement));
		assertNotNull(test.getId());
		assertEquals("example", test.getName());
		assertEquals(1, test.getFixtures().size());
		assertEquals("System.out.println(0);", test.getFixtures().get(0).getText());
		assertEquals(1, test.getAsserts().size());
		assertEquals("assertEqulas(0, 0);", test.getAsserts().get(0).getText());
	}

}
