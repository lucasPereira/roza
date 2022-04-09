package br.ufsc.ine.leb.roza.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.fixtures.ParsingFixtures;

class TestMethodTest {

	@Test
	void create() throws Exception {
		RozaStatement statement = ParsingFixtures.buildAssertStatement();
		TestMethod method = new TestMethod("test", Arrays.asList(statement));
		assertEquals("test", method.getName());
		assertEquals(1, method.getStatements().size());
		assertEquals(statement, method.getStatements().get(0));
	}

	@Test
	void equals() throws Exception {
		assertNotEquals(new TestMethod("test", Arrays.asList()), new TestMethod("test", Arrays.asList()));
	}

	@Test
	void string() throws Exception {
		assertEquals("@Test test()", new TestMethod("test", Arrays.asList()).toString());
	}

}
