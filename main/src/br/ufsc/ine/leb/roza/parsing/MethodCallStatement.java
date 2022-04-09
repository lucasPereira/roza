package br.ufsc.ine.leb.roza.parsing;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;

public class MethodCallStatement extends GenericStatement {

	public MethodCallStatement(ExpressionStmt statement, MethodCallExpr expression) {
		super(statement);
	}

	@Override
	public void visit(StatementVisitor visitor) {
		visitor.visit(this);
	}

}
