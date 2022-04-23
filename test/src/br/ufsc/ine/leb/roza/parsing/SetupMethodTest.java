package br.ufsc.ine.leb.roza.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.fixture.ParsingFixtures;

class SetupMethodTest {

	@Test
	void create() throws Exception {
		SetupMethod method = ParsingFixtures.setupMethod("setup");
		assertEquals("setup", method.getName());
		assertEquals(0, method.getStatements().size());
	}

}
