package br.ufsc.ine.leb.roza.parsing;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.fixture.ParsingFixtures;

class GenericStatementTest {

	@Test
	void test() throws Exception {
		GenericStatement genericStatement = ParsingFixtures.whileTrueBreak();
		assertEquals("while (true) break;", genericStatement.toCode());
	}

}
