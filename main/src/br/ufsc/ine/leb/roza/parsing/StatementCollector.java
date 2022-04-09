package br.ufsc.ine.leb.roza.parsing;

import java.util.List;

public class StatementCollector {

	private StatementCollectorVisitor visitor;

	public StatementCollector(List<RozaStatement> statements) {
		visitor = new StatementCollectorVisitor();
		statements.forEach(statement -> statement.visit(visitor));
	}

	public List<AssignStatement> getAssignStatements() {
		return visitor.getAssignStatements();
	}

	public List<GenericStatement> getGenericStatements() {
		return visitor.getGenericStatements();
	}

	public List<MethodCallStatement> getMethodCallStatements() {
		return visitor.getMethodCallStatements();
	}

	public List<VariableDeclarationStatement> getVariableDeclarationStatements() {
		return visitor.getVariableDeclarationStatements();
	}

}
