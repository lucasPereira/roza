package br.ufsc.ine.leb.roza.parsing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;

public class MethodCallStatementTest {

	private ExpressionStmt parserStatement;
	private MethodCallExpr parserExpression;

	@BeforeEach
	void setup() {
		parserExpression = new MethodCallExpr("operation");
		parserStatement = new ExpressionStmt(parserExpression);
	}

	@Test
	void create() throws Exception {
		MethodCallStatement statement = new MethodCallStatement(parserStatement, parserExpression);
		assertEquals("operation();", statement.getCode());
	}

	@Test
	void equals() throws Exception {
		assertNotEquals(new MethodCallStatement(parserStatement, parserExpression), new MethodCallStatement(parserStatement, parserExpression));
	}

	@Test
	void string() throws Exception {
		assertEquals("operation();", new MethodCallStatement(parserStatement, parserExpression).toString());
	}

}
