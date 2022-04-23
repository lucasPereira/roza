package br.ufsc.ine.leb.roza.extraction;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.fixture.ExtractionFixtures;
import br.ufsc.ine.leb.roza.parsing.RozaStatement;

class TestCaseTest {

	@Test
	void create() throws Exception {
		RozaStatement fixtureStatement = ExtractionFixtures.sutCallWithZeroParameter();
		RozaStatement assertStatement = ExtractionFixtures.assertEqualsWithZeroAndZero();
		TestCase test = new TestCase("example", Arrays.asList(fixtureStatement), Arrays.asList(assertStatement));
		assertNotNull(test.getId());
		assertEquals("example", test.getName());
		assertEquals(1, test.getFixtures().size());
		assertEquals(fixtureStatement, test.getFixtures().get(0));
		assertEquals(1, test.getAsserts().size());
		assertEquals(assertStatement, test.getAsserts().get(0));
	}

	@Test
	void equals() throws Exception {
		assertNotEquals(new TestCase("example", Arrays.asList(), Arrays.asList()), new TestCase("example", Arrays.asList(), Arrays.asList()));
	}

	@Test
	void string() throws Exception {
		TestCase testCase = new TestCase("example", Arrays.asList(), Arrays.asList());
		assertAll(() -> assertTrue(testCase.toString().startsWith("example (")), () -> assertTrue(testCase.toString().endsWith(")")));
	}

}
