package br.ufsc.ine.leb.roza.parsing;

public interface RozaStatement {

	public String toCode();

	public void visit(StatementVisitor visitor);

}
