package br.ufsc.ine.leb.roza.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.fixture.ParsingFixtures;

public class VariableDeclarationStatementTest {

	@Test
	void notInitialized() throws Exception {
		VariableDeclarationStatement variableDeclarationStatement = ParsingFixtures.variableDeclarationInteger("fixture");
		assertEquals("Integer fixture;", variableDeclarationStatement.toCode());
	}

	@Test
	void initialized() throws Exception {
		VariableDeclarationStatement variableDeclarationStatement = ParsingFixtures.variableDeclarationInteger("fixture", 10);
		assertEquals("Integer fixture = 10;", variableDeclarationStatement.toCode());
	}

}
