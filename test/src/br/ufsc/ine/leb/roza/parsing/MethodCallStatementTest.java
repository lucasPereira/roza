package br.ufsc.ine.leb.roza.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.fixture.ParsingFixtures;

public class MethodCallStatementTest {

	@Test
	void test() throws Exception {
		MethodCallStatement methodCallStatement = ParsingFixtures.methodCall("operation");
		assertEquals("operation();", methodCallStatement.toCode());
	}

}
