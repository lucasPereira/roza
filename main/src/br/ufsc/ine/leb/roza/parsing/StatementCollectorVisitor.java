package br.ufsc.ine.leb.roza.parsing;

import java.util.LinkedList;
import java.util.List;

public class StatementCollectorVisitor implements StatementVisitor {

	private List<AssignStatement> assignStatements;
	private List<GenericStatement> genericStatements;
	private List<MethodCallStatement> methodCallStatements;
	private List<VariableDeclarationStatement> variableDeclarationStatements;

	public StatementCollectorVisitor() {
		assignStatements = new LinkedList<>();
		genericStatements = new LinkedList<>();
		methodCallStatements = new LinkedList<>();
		variableDeclarationStatements = new LinkedList<>();
	}

	@Override
	public void visit(AssignStatement statement) {
		assignStatements.add(statement);
	}

	@Override
	public void visit(GenericStatement statement) {
		genericStatements.add(statement);
	}

	@Override
	public void visit(MethodCallStatement statement) {
		methodCallStatements.add(statement);
	}

	@Override
	public void visit(VariableDeclarationStatement statement) {
		variableDeclarationStatements.add(statement);
	}

	public List<AssignStatement> getAssignStatements() {
		return assignStatements;
	}

	public List<GenericStatement> getGenericStatements() {
		return genericStatements;
	}

	public List<MethodCallStatement> getMethodCallStatements() {
		return methodCallStatements;
	}

	public List<VariableDeclarationStatement> getVariableDeclarationStatements() {
		return variableDeclarationStatements;
	}

}
