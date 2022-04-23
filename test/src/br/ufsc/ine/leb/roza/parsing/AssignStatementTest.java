package br.ufsc.ine.leb.roza.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.fixture.ParsingFixtures;

public class AssignStatementTest {

	@Test
	void test() throws Exception {
		AssignStatement assignStatement = ParsingFixtures.assignInteger("fixture", 0);
		assertEquals("fixture = 0;", assignStatement.toCode());
	}

}
