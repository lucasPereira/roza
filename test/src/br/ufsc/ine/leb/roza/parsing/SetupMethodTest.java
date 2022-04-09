package br.ufsc.ine.leb.roza.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.fixtures.ParsingFixtures;

class SetupMethodTest {

	@Test
	void create() throws Exception {
		RozaStatement statement = ParsingFixtures.buildFixtureStatement();
		SetupMethod method = new SetupMethod("setup", Arrays.asList(statement));
		assertEquals("setup", method.getName());
		assertEquals(1, method.getStatements().size());
		assertEquals(statement, method.getStatements().get(0));
	}

	@Test
	void equals() throws Exception {
		assertNotEquals(new SetupMethod("setup", Arrays.asList()), new SetupMethod("setup", Arrays.asList()));
	}

	@Test
	void string() throws Exception {
		assertEquals("@Before setup()", new SetupMethod("setup", Arrays.asList()).toString());
	}

}
