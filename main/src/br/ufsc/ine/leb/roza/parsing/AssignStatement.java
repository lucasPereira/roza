package br.ufsc.ine.leb.roza.parsing;

import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;

public class AssignStatement extends GenericStatement {

	public AssignStatement(ExpressionStmt statement, AssignExpr expression) {
		super(statement);
	}

	@Override
	public void visit(StatementVisitor visitor) {
		visitor.visit(this);
	}

}
