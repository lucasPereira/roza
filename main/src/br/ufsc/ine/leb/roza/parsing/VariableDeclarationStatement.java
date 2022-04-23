package br.ufsc.ine.leb.roza.parsing;

import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.Statement;

public class VariableDeclarationStatement extends GenericStatement {

	public VariableDeclarationStatement(VariableDeclarationExpr expression, Statement statement) {
		super(statement);
	}

	@Override
	public void visit(StatementVisitor visitor) {
		visitor.visit(this);
	}

}
