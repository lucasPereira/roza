package br.ufsc.ine.leb.roza.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.AssignExpr.Operator;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;

public class AssignStatementTest {

	private AssignExpr parserExpression;
	private ExpressionStmt parserStatement;

	@BeforeEach
	void setup() {
		parserExpression = new AssignExpr(new NameExpr("fixture"), new IntegerLiteralExpr("10"), Operator.ASSIGN);
		parserStatement = new ExpressionStmt(parserExpression);
	}

	@Test
	void create() throws Exception {
		AssignStatement statement = new AssignStatement(parserStatement, parserExpression);
		assertEquals("fixture = 10;", statement.getCode());
	}

	@Test
	void equals() throws Exception {
		assertNotEquals(new AssignStatement(parserStatement, parserExpression), new AssignStatement(parserStatement, parserExpression));
	}

	@Test
	void string() throws Exception {
		assertEquals("fixture = 10;", new AssignStatement(parserStatement, parserExpression).toString());
	}

}
