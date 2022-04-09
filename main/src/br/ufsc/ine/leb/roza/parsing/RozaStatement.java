package br.ufsc.ine.leb.roza.parsing;

public interface RozaStatement {

	public String getCode();

	public void visit(StatementVisitor visitor);

}
