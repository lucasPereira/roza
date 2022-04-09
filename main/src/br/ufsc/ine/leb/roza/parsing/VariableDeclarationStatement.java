package br.ufsc.ine.leb.roza.parsing;

public class VariableDeclarationStatement extends GenericStatement {

	private String variable;

	public VariableDeclarationStatement(String code, String variable) {
		super(code);
		this.variable = variable;
	}

	public String getVariableName() {
		return variable;
	}

	@Override
	public void visit(StatementVisitor visitor) {
		visitor.visit(this);
	}

}
