package br.ufsc.ine.leb.roza.parsing;

public interface StatementVisitor {

	void visit(AssignStatement statement);

	void visit(GenericStatement statement);

	void visit(MethodCallStatement statement);

	void visit(VariableDeclarationStatement statement);

}
