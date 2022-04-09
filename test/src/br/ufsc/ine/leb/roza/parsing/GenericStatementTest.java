package br.ufsc.ine.leb.roza.parsing;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.stmt.AssertStmt;

class GenericStatementTest {

	private AssertStmt parserStatement;

	@BeforeEach
	void setup() {
		parserStatement = new AssertStmt(new BooleanLiteralExpr(true));
	}

	@Test
	void create() throws Exception {
		GenericStatement statement = new GenericStatement(parserStatement);
		assertEquals("assert true;", statement.getCode());
	}

	@Test
	void equals() throws Exception {
		assertNotEquals(new GenericStatement(parserStatement), new GenericStatement(parserStatement));
	}

	@Test
	void string() throws Exception {
		assertEquals("assert true;", new GenericStatement(parserStatement).toString());
	}

}
