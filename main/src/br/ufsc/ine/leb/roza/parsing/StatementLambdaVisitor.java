package br.ufsc.ine.leb.roza.parsing;

import java.util.function.Consumer;

public class StatementLambdaVisitor implements StatementVisitor {

	private Consumer<AssignStatement> assignStatementConsumer;
	private Consumer<GenericStatement> genericStatementConsumer;
	private Consumer<MethodCallStatement> methodCallStatementConsumer;
	private Consumer<VariableDeclarationStatement> variableDeclarationStatementConsumer;

	public void withAssignStatementConsumer(Consumer<AssignStatement> assignStatementConsumer) {
		this.assignStatementConsumer = assignStatementConsumer;
	}

	public void withGenericStatementConsumer(Consumer<GenericStatement> genericStatementConsumer) {
		this.genericStatementConsumer = genericStatementConsumer;
	}

	public void withMethodCallStatement(Consumer<MethodCallStatement> methodCallStatementConsumer) {
		this.methodCallStatementConsumer = methodCallStatementConsumer;
	}

	public void withVariableDeclarationStatement(Consumer<VariableDeclarationStatement> variableDeclarationStatementConsumer) {
		this.variableDeclarationStatementConsumer = variableDeclarationStatementConsumer;
	}

	@Override
	public void visit(AssignStatement statement) {
		if (assignStatementConsumer != null) {
			assignStatementConsumer.accept(statement);
		}
	}

	@Override
	public void visit(GenericStatement statement) {
		if (genericStatementConsumer != null) {
			genericStatementConsumer.accept(statement);
		}
	}

	@Override
	public void visit(MethodCallStatement statement) {
		if (methodCallStatementConsumer != null) {
			methodCallStatementConsumer.accept(statement);
		}
	}

	@Override
	public void visit(VariableDeclarationStatement statement) {
		if (variableDeclarationStatementConsumer != null) {
			variableDeclarationStatementConsumer.accept(statement);
		}
	}

}
