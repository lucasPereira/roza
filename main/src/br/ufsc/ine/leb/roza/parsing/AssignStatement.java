package br.ufsc.ine.leb.roza.parsing;

import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.stmt.Statement;

public class AssignStatement extends GenericStatement {

	public AssignStatement(AssignExpr expression, Statement expressionStatement) {
		super(expressionStatement);
	}

	@Override
	public void visit(StatementVisitor visitor) {
		visitor.visit(this);
	}

}
