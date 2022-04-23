package br.ufsc.ine.leb.roza.parsing;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.Statement;

public class MethodCallStatement extends GenericStatement {

	public MethodCallStatement(MethodCallExpr expression, Statement statement) {
		super(statement);
	}

	@Override
	public void visit(StatementVisitor visitor) {
		visitor.visit(this);
	}

}
